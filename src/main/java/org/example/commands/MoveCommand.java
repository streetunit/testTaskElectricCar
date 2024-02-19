package org.example.commands;

import lombok.extern.slf4j.Slf4j;
import org.example.car.Car;

@Slf4j
public class MoveCommand extends Command {
    public MoveCommand(Car car) {
        super(car);
    }

    @Override
    public void execute() {
            car.move();
    }
}
