package com.group17.game.model.world;

import com.group17.game.model.entity.item.Key;

/**
 * Parent abstract class for all the key door cells.
 *
 * @author Laurence Rawlings
 * @version 2.0
 */
public abstract class KeyDoor extends Cell {
    private final Key.KeyType key;

    /**
     * Constructor to create a new instance.
     *
     * @param key        key that will open the door.
     * @param spriteName corresponding sprite name for the coloured door.
     */
    KeyDoor(Key.KeyType key, String spriteName) {
        super(false, spriteName);
        this.key = key;
    }

    public Key.KeyType getKey() {
        return key;
    }
}
