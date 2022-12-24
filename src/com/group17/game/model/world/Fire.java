package com.group17.game.model.world;

import com.group17.game.model.entity.item.FireBoots;

/**
 * Models the fire cell in the game.
 *
 * @author Sam Murphey
 * @version 1.0
 */
public class Fire extends Obstacle {
    /**
     * Constructor to create a new instance.
     */
    public Fire() {
        super(new FireBoots(), "obstacle_fire");
    }
}
