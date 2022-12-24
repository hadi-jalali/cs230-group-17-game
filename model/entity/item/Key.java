package com.group17.game.model.entity.item;

import com.group17.game.controller.SceneController;

import java.util.Objects;

/**
 * Models key item.
 *
 * @author Vlad Kashtelyanov
 * @version 1.0
 */
public class Key extends Item {
    private final KeyType keyType;

    /**
     * This key method sets the sprite names according to their colour
     *
     * @param keyType colour of the key.
     */
    public Key(KeyType keyType) {
        super(keyType.toString() + " " +
                        SceneController.getLanguageBundle().getString("item_key"),
                "missing_item");
        this.keyType = keyType;
        switch (keyType) {
            case red:
                setSpriteName("key_red");
                break;
            case green:
                setSpriteName("key_green");
                break;
            case blue:
                setSpriteName("key_blue");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Key key = (Key) o;
        return keyType == key.keyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), keyType);
    }

    @Override
    public String toString() {
        return keyType.toString() + " " +
                SceneController.getLanguageBundle().getString("item_key");
    }

    /**
     * Enum for all the different key colours.
     */
    public enum KeyType {
        red,
        green,
        blue;

        /**
         * Gets the corresponding label for the key colour.
         *
         * @return the colour string.
         */
        @Override
        public String toString() {
            switch (this) {
                case red:
                    return SceneController.getLanguageBundle()
                            .getString("colour_red");
                case green:
                    return SceneController.getLanguageBundle()
                            .getString("colour_green");
                case blue:
                    return SceneController.getLanguageBundle()
                            .getString("colour_blue");
                default:
                    return SceneController.getLanguageBundle()
                            .getString("item_key");
            }
        }
    }
}
