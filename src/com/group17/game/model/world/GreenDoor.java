package com.group17.game.model.world;

import com.group17.game.model.entity.item.Key;

/**
 * Models the green door cell in the game.
 *
 * @author Sam Murphey
 * @version 1.0
 */
public class GreenDoor extends KeyDoor {
    /**
     * Constructor to create a new instance.
     */
    public GreenDoor() {
        super(Key.KeyType.green, "door_green");
    }
}
