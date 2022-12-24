package com.group17.game.model;

import java.io.Serializable;

/**
 * Parent abstract class for all level objects. Stores the sprite filename so the game object can be rendered.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public abstract class GameObject implements Serializable {
    private String spriteName;

    /**
     * Create an instance of a game object.
     *
     * @param spriteName sprite filename on the filesystem.
     */
    protected GameObject(String spriteName) {
        setSpriteName(spriteName);
    }

    public String getSpriteName() {
        return spriteName;
    }

    protected void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }
}
