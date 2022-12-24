package com.group17.game.model.world;

import com.group17.game.model.entity.item.Key;

/**
 * Models the red door cell in the game.
 *
 * @author Oscar Howard
 * @version 1.0
 */
public class RedDoor extends KeyDoor {
    /**
     * Constructor to create a new instance.
     */
    public RedDoor() {
        super(Key.KeyType.red, "door_red");
    }
}
