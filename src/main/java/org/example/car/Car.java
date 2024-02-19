package org.example.car;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.CarException;

import java.text.MessageFormat;

import static org.example.message.ErrMessageForCar.*;

@Slf4j
public class Car {
    private final int boundWidth;
    private final int boundHeight;
    @Getter
    private int positionX;
    @Getter
    private int positionY;
    @Getter
    private Direction direction;

    public Car(int x, int y, Direction direction, char[][] field) {
        boundHeight = field.length;
        boundWidth = field[0].length;
        initCarPosition(x, y);
        this.direction = direction;
    }

    public void turnLeft() {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case EAST -> direction = Direction.NORTH;
            case SOUTH -> direction = Direction.EAST;
            case WEST -> direction = Direction.SOUTH;
        }
    }

    public void turnRight() {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case EAST -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.WEST;
            case WEST -> direction = Direction.NORTH;
        }
    }

    public void move() {
        int newPositionX = positionX;
        int newPositionY = positionY;
        switch (direction) {
            case NORTH -> newPositionY++;
            case EAST -> newPositionX++;
            case SOUTH -> newPositionY--;
            case WEST -> newPositionX--;
        }

        if (checkPositionOutsideBounds(newPositionX, newPositionY)) {
            String errMessage = MessageFormat.format(MOVE_ERROR_OUT_OF_BOUNDS, positionX, positionY, direction);
            throw new CarException(errMessage);
        }
        positionX = newPositionX;
        positionY = newPositionY;
    }

    public void logCurrentPosition() {
        log.info("x: {}; y: {}", positionX, positionY);
    }


    private boolean checkPositionOutsideBounds(int x, int y) {
        return x >= boundWidth
                || x < 0
                || y >= boundHeight
                || y < 0;
    }

    private void initCarPosition(int startPosX, int startPosY) {
        if (checkPositionOutsideBounds(startPosX, startPosY)) {
            String errMessage = MessageFormat.format(INIT_ERROR_OUT_OF_BOUNDS, startPosX, startPosY, boundWidth, boundHeight);
            throw new CarException(errMessage);
        }

        positionX = startPosX;
        positionY = startPosY;
    }
}
