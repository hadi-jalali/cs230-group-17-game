package com.group17.game.model.world;

import com.group17.game.model.entity.item.Key;

/**
 * Models the blue door cell in the game.
 *
 * @author Oscar Howard
 * @version 1.0
 */
public class BlueDoor extends KeyDoor {
    /**
     * Constructor to create a new instance.
     */
    public BlueDoor() {
        super(Key.KeyType.blue, "door_blue");
    }
}
