package org.example.commands;

import org.example.car.Car;

public class TurnOn180Degrees extends Command{
    public TurnOn180Degrees(Car car) {
        super(car);
    }

    @Override
    public void execute() {
        car.turnRight();
        car.turnRight();
    }
}
