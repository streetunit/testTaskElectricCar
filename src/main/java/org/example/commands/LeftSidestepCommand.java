package org.example.commands;

import org.example.car.Car;

public class LeftSidestepCommand extends Command{
    public LeftSidestepCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.turnLeft();
        car.move();
        car.turnRight();
    }
}
