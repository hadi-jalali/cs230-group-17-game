package com.group17.game.model.entity;

import com.group17.game.core.Position;
import com.group17.game.core.ProfileManager;
import com.group17.game.model.GameObject;
import com.group17.game.model.entity.item.Item;

import java.util.*;

/**
 * Models the player in the game. Handles the players inventory.
 *
 * @author Laurence Rawlings, Hadi Jalali
 */
public class Player extends GameObject {
    private final Map inventory;
    private Position position;

    /**
     * Constructor creates a new instance of player entity at the given position.
     *
     * @param position spawn position of the player.
     */
    public Player(Position position) {
        super(ProfileManager.getCharacter().getSkinSprite());
        inventory = new HashMap();
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSprite(Skin character) {
        setSpriteName(character.getSkinSprite());
    }

    /**
     * Check if the player has the given item in it's inventory.
     *
     * @param item item to check for.
     * @return true iff the player has the specified item in it's inventory.
     */
    public boolean hasItem(Item item) {
        if (inventory.containsKey(item)) {
            return (int) inventory.get(item) > 0;
        }
        return false;
    }

    /**
     * Checks if the player has an equal or greater amount of the given item in it's inventory.
     *
     * @param item   item to check for.
     * @param amount amount to check for.
     * @return true iff the player has equal or greater of the specified item in it's inventory.
     */
    public boolean hasItem(Item item, int amount) {
        if (inventory.containsKey(item)) {
            return (int) inventory.get(item) >= amount;
        }
        return false;
    }

    /**
     * Adds the given item to the player's inventory.
     *
     * @param item item to add to the inventory.
     */
    public void pickUp(Item item) {
        if (!inventory.containsKey(item)) {
            inventory.put(item, 1);
        } else {
            int current = (int) inventory.get(item);
            inventory.put(item, ++current);
        }
    }

    /**
     * Remove the specified item from the player's inventory.
     *
     * @param item item to remove.
     */
    public void useItem(Item item) {
        if (hasItem(item)) {
            int current = (int) inventory.get(item);
            inventory.put(item, --current);
        }
    }

    /**
     * Gets a list of item names in the player's inventory.
     *
     * @return list of item names.
     */
    public List<String> getItems() {
        List<String> items = new ArrayList<>();
        for (Object item : inventory.keySet()) {
            int amount = (int) inventory.get(item);
            if (amount > 0) {
                items.add(item.toString() + " x" + amount);
            }
        }
        Collections.sort(items);
        return items;
    }

    /**
     * Enum for the various player skins.
     */
    public enum Skin {
        man("player"),
        woman("player2");

        private final String skinSprite;

        /**
         * Enum constructor sets the skin sprite filename for the type.
         *
         * @param skinSprite sprite filename for the skin.
         */
        Skin(String skinSprite) {
            this.skinSprite = skinSprite;
        }

        String getSkinSprite() {
            return skinSprite;
        }
    }
}
