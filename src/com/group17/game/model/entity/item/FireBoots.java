package com.group17.game.model.entity.item;

import com.group17.game.controller.SceneController;

/**
 * Models fireboots item.
 *
 * @author Sam Murphy, Alex Hughes
 * @version 1.0
 */
public class FireBoots extends Item {
    /**
     * Create new instance of fireboots.
     */
    public FireBoots() {
        super(SceneController.getLanguageBundle().getString("item_fireBoots"),
                "item_fireboots");
    }

    @Override
    public String toString() {
        return SceneController.getLanguageBundle().getString("item_fireBoots");
    }
}
