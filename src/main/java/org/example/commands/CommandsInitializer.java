package org.example.commands;

import org.example.car.Car;
import org.example.config.AppConfig;

import java.util.Map;

@FunctionalInterface
public interface CommandsInitializer {
    Map<String, Command> init(Car car, AppConfig appConfig);
}
