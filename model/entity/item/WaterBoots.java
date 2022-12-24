package com.group17.game.model.entity.item;

import com.group17.game.controller.SceneController;

/**
 * Models waterboots item.
 *
 * @author Sam Murphy, Oscar Howard
 * @version 1.0
 */
public class WaterBoots extends Item {
    /**
     * Create new instance of waterboots.
     */
    public WaterBoots() {
        super(SceneController.getLanguageBundle().getString("item_waterBoots"),
                "item_waterboots");
    }

    @Override
    public String toString() {
        return SceneController.getLanguageBundle().getString("item_waterBoots");
    }
}
