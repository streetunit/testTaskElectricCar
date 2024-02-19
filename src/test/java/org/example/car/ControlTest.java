package org.example.car;

import org.example.commands.*;
import org.example.config.AppConfig;
import org.example.exception.CarException;
import org.example.message.ErrMessageForCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.example.car.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

class ControlTest {
    private static final int START_X = 0;
    private static final int START_Y = 0;
    private static final int FIELD_WIDTH = 5;
    private static final int FIELD_HEIGHT = 5;
    private AppConfig appConfig;
    private char[][] field;
    private Car car;
    private Control control;

    @BeforeEach
    void setUp() {
        field = new char[FIELD_WIDTH][FIELD_HEIGHT];
        appConfig = new AppConfig();
        car = new Car(START_X, START_Y, NORTH, field);
        control = new Control(appConfig, car,
                (curCar, appCfg) -> {
                    HashMap<String, Command> commands = new HashMap<>();
                    commands.put(appCfg.patternCommandTurnLeft(), new TurnLeftCommand(curCar));
                    commands.put(appCfg.patternCommandTurnRight(), new TurnRightCommand(curCar));
                    commands.put(appCfg.patternCommandMove(), new MoveCommand(curCar));
                    commands.put(appCfg.patternCommandTurn180(), new TurnOn180Degrees(curCar));
                    return commands;
                });
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void testAcceptCommand(String[] commands, int expectedX, int expectedY) {
        for (var c : commands) {
            control.acceptCommand(c);
        }

        assertEquals(expectedX, car.getPositionX());
        assertEquals(expectedY, car.getPositionY());
    }


    @ParameterizedTest
    @MethodSource("provideNegativeTestData")
    void testAcceptCommand_WhenCarCanBeOutOfBounds_ShouldThrowException(String[] commands, int expectedX, int expectedY, Direction expectedDirection) {
        String expectedErrMessage = MessageFormat.format(ErrMessageForCar.MOVE_ERROR_OUT_OF_BOUNDS, expectedX, expectedY, expectedDirection);

        Exception exception = assertThrows(CarException.class, () -> {
            for (var c : commands) {
                control.acceptCommand(c);
            }
        });

        assertEquals(expectedErrMessage, exception.getMessage());
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of(
                        new String[]{"move", "move"},
                        0, 2
                ),
                Arguments.of(
                        new String[]{"move", "move", "move", "move",
                                "turn 180",
                                "move", "move", "move"},
                       0, 1
                ),
                Arguments.of(
                        new String[]{"turn right", "move", "move", "move"},
                        3, 0
                ),
                Arguments.of(
                        new String[]{"turn right",
                                "move", "move", "move",
                                "turn right", "turn right",
                                "move"},
                        2, 0
                )
        );
    }

    private static Stream<Arguments> provideNegativeTestData() {
        return Stream.of(
                Arguments.of(
                        new String[]{"move", "move", "move", "move", "move"},  0, 4, NORTH
                ),
                Arguments.of(
                        new String[]{"turn right", "move", "move", "move", "move", "move"}, 4, 0, EAST
                ),
                Arguments.of(
                        new String[]{"turn left", "move"}, 0, 0, WEST
                ),
                Arguments.of(
                        new String[]{"turn 180", "move"}, 0, 0, SOUTH
                )
        );
    }
}