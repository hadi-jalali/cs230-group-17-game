package com.group17.game.model.world;

import com.group17.game.model.entity.item.Item;

/**
 * Public abstract class for all obstacle cells in the game.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public abstract class Obstacle extends Cell {
    private final Item counterItem;

    /**
     * Constructor to create a new instance of an obstacle.
     *
     * @param counterItem item that counters the obstacle. If the player has this item it can pass the obstacle.
     * @param spriteName  corresponding sprite filename of the obstacle.
     */
    Obstacle(Item counterItem, String spriteName) {
        super(false, spriteName);
        this.counterItem = counterItem;
    }

    public Item getCounterItem() {
        return counterItem;
    }
}
