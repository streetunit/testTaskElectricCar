package org.example.commands;

import org.example.car.Car;

public class MockCommand extends Command{
    public MockCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {

    }
}
