package org.example.commands;

import org.example.car.Car;

public class TurnRightCommand extends Command{
    public TurnRightCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.turnRight();
    }
}
