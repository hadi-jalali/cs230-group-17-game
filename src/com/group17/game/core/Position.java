package com.group17.game.core;

import com.group17.game.model.entity.Direction;

import java.io.Serializable;
import java.util.Objects;

/**
 * Models a position on the game grid. Assigned to objects in the game to get and set their x and y coordinates.
 *
 * @author Oscar Howard
 * @version 1.0
 */
public class Position implements Serializable {
    private final int x;
    private final int y;

    /**
     * Create a position.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Given a position and direction to move, determine the next position.
     *
     * @param currentPosition position to move from.
     * @param direction       direction in which to move.
     * @return the next position.
     */
    public static Position nextPosition(Position currentPosition,
                                        Direction direction) {
        Position next = null;
        switch (direction) {
            case right:
                next = new Position(currentPosition.x() + 1,
                        currentPosition.y());
                break;
            case left:
                next = new Position(currentPosition.x() - 1,
                        currentPosition.y());
                break;
            case up:
                next = new Position(currentPosition.x(),
                        currentPosition.y() - 1);
                break;
            case down:
                next = new Position(currentPosition.x(),
                        currentPosition.y() + 1);
                break;
        }

        return next;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
