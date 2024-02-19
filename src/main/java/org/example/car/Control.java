package org.example.car;

import org.example.commands.Command;
import org.example.commands.CommandsInitializer;
import org.example.config.AppConfig;

import java.util.Map;

public class Control {
    private final AppConfig appConfig;
    private final Car car;
    private Map<String, Command> commands;

    public Control(AppConfig appConfig, Car car, CommandsInitializer commandsInitializer) {
        this.appConfig = appConfig;
        this.car = car;
        commands = commandsInitializer.init(car, appConfig);
    }

    public void acceptCommand(String textCommand) {
        Command command = commands.get(textCommand);
        if (command != null) {
            command.execute();
        }
    }

    public void rewriteCommands(CommandsInitializer commandsInitializer) {
        commands = commandsInitializer.init(car, appConfig);
    }
}
