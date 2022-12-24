package com.group17.game.model.entity;

import java.util.Objects;

/**
 * Enum for each moveable direction and helper methods for conversions.
 *
 * @author Laurence Rawlings
 * @version 2.0
 */
public enum Direction {
    up,
    down,
    left,
    right;

    /**
     * Flip the direction to the opposite direction.
     *
     * @return the flipped direction.
     */
    public Direction flip() {
        switch (this) {
            case up:
                return Direction.down;
            case down:
                return Direction.up;
            case left:
                return Direction.right;
            case right:
                return Direction.left;
        }

        return null;
    }

    /**
     * Convert the direction to the rotate right direction (Rotate Clockwise).
     *
     * @return the new direction.
     */
    public Direction rotateRight() {
        switch (this) {
            case up:
                return Direction.right;
            case down:
                return Direction.left;
            case left:
                return Direction.up;
            case right:
                return Direction.down;
        }

        return null;
    }

    /**
     * Gets the relative move direction when passed a facing direction
     *
     * @param facing direction the entity is facing.
     * @return the direction the entity should move.
     */
    public Direction relative(Direction facing) {
        switch (facing) {
            case up:
                return this;
            case down:
                return this.flip();
            case left:
                return Objects.requireNonNull(this.rotateRight()).flip();
            case right:
                return this.rotateRight();
        }

        return null;
    }
}
