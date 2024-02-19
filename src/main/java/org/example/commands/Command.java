package org.example.commands;

import org.example.car.Car;

public abstract class Command {
    protected Car car;

    public Command(Car car) {
        this.car = car;
    }

    public abstract void execute();
}
