package org.example.car;

import org.example.exception.CarException;
import org.example.message.ErrMessageForCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private static final int FIELD_WIDTH = 5;
    private static final int FIELD_HEIGHT = 5;
    private char[][] field;
    private Car car;
    private Direction expectedDirection;

    @BeforeEach
    void setUp() {
        field = new char[FIELD_HEIGHT][FIELD_WIDTH];
        car = new Car(0, 0, Direction.SOUTH, field);
    }

    @Test
    void testTurnLeft() {
        expectedDirection = Direction.EAST;

        car.turnLeft();

        assertEquals(expectedDirection, car.getDirection());
    }

    @Test
    void testTurnRight() {
        expectedDirection = Direction.WEST;

        car.turnRight();

        assertEquals(expectedDirection, car.getDirection());
    }

    @Test
    void testMove() {
        int expectedX = 0;
        int expectedY = 1;

        car.turnRight();
        car.turnRight();
        car.move();

        assertEquals(expectedX, car.getPositionX());
        assertEquals(expectedY, car.getPositionY());
    }

    @ParameterizedTest
    @MethodSource("provideErrInitData")
    void testInitWhenOutsideShouldThrowException(int startX, int startY) {
        String expectedErrMsg = MessageFormat.format(ErrMessageForCar.INIT_ERROR_OUT_OF_BOUNDS, startX, startY, FIELD_HEIGHT, FIELD_WIDTH);
        Direction direction = Direction.EAST;

        Exception exception = assertThrows(CarException.class, () -> new Car(startX, startY, direction, field));

        assertEquals(expectedErrMsg, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideStartInfo")
    void testMove_WhenCarCanBeOutOfBounds_ShouldThrowException(int x, int y,
                                                               Direction direction,
                                                               int[] expectedCoord) {

        String expectedErrMessage = MessageFormat.format(ErrMessageForCar.MOVE_ERROR_OUT_OF_BOUNDS, expectedCoord[0], expectedCoord[1], direction);
        car = new Car(x, y, direction, field);

        Exception exception = assertThrows(CarException.class,
                () -> {
                    for (int i = 0; i < 5; i++) {
                        car.move();
                    }
                });

        assertEquals(expectedErrMessage, exception.getMessage());
    }

    private static Stream<Arguments> provideStartInfo() {
        return Stream.of(
                Arguments.of(0, 4, Direction.SOUTH, new int[]{0, 0}),
                Arguments.of(0, 0, Direction.NORTH, new int[]{0, 4}),
                Arguments.of(0, 0, Direction.EAST, new int[]{4, 0}),
                Arguments.of(4, 0, Direction.WEST, new int[]{0, 0})
        );
    }

    private static Stream<Arguments> provideErrInitData() {
        return Stream.of(
                Arguments.of(-1, -1),
                Arguments.of(-1, 0),
                Arguments.of(0, -1),
                Arguments.of(10, 10),
                Arguments.of(10, 0),
                Arguments.of(0, 10)
        );
    }
}