package com.group17.game.model.entity.item;

import com.group17.game.controller.SceneController;

/**
 * Models the token item in the game.
 *
 * @author Tom Ling
 * @version 1.0
 */
public class Token extends Item {
    /**
     * Create a new instance of a token item.
     */
    public Token() {
        super(SceneController.getLanguageBundle().getString("item_token"),
                "token");
    }

    @Override
    public String toString() {
        return SceneController.getLanguageBundle().getString("item_token");
    }
}
