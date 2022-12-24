package com.group17.game.model.world;

import com.group17.game.model.GameObject;

/**
 * Parent abstract class for all cells in the game.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public abstract class Cell extends GameObject {
    private final boolean walkable;

    /**
     * Constructor to create a new instance of a cell.
     *
     * @param walkable   true iff the player can walk on this cell.
     * @param spriteName filename of the corresponding sprite for the cell.
     */
    Cell(boolean walkable, String spriteName) {
        super(spriteName);
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return this.walkable;
    }
}
