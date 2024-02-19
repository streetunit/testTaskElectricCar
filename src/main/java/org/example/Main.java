package org.example;

import org.example.car.Car;
import org.example.car.Control;
import org.example.car.Direction;
import org.example.commands.*;
import org.example.config.AppConfig;
import org.example.parser.FileParser;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        FileParser fileParser = new FileParser(appConfig);

        CommandsInitializer commandsInitializer = (curCar, appCfg) -> {
            HashMap<String, Command> commands = new HashMap<>();
            commands.put(appCfg.patternCommandTurnLeft(), new TurnLeftCommand(curCar));
            commands.put(appCfg.patternCommandTurnRight(), new TurnRightCommand(curCar));
            commands.put(appCfg.patternCommandMove(), new MoveCommand(curCar));
            commands.put(appCfg.patternCommandTurn180(), new TurnOn180Degrees(curCar));
            return commands;
        };

        char[][] field = new char[appConfig.fieldHeight()][appConfig.fieldWidth()];
        Car car = new Car(appConfig.startCarPosX(), appConfig.startCarPosY(), appConfig.startDirection(), field);
        Control control = new Control(appConfig, car, commandsInitializer);

        List<String> commands = fileParser.getCommandsFromFile();

        for (var command: commands) {
            control.acceptCommand(command);
        }
        car.logCurrentPosition();
    }
}