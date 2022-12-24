package com.group17.game.model.entity.item;

import com.group17.game.model.GameObject;

import java.util.Objects;

/**
 * Parent abstract class for all game items.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public abstract class Item extends GameObject {
    private final String name;

    /**
     * Create a new instance of an item.
     *
     * @param name       the name of the item.
     * @param spriteName filename of the associated sprite.
     */
    Item(String name, String spriteName) {
        super(spriteName);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
