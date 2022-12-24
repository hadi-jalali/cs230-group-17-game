package com.group17.game.model.world;

import com.group17.game.model.entity.item.WaterBoots;

/**
 * Models the fire cell in the game.
 *
 * @author Sam Murphey
 * @version 1.0
 */
public class Water extends Obstacle {
    /**
     * Constructor to create a new instance.
     */
    public Water() {
        super(new WaterBoots(), "obstacle_water");
    }
}
