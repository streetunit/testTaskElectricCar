package org.example.commands;

import org.example.car.Car;

public class TurnLeftCommand extends Command{

    public TurnLeftCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.turnLeft();
    }
}
