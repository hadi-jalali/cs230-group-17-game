package com.group17.game.model.world;

/**
 * Models the token door cell in the game.
 *
 * @author Hadi Jalali
 * @version 1.0
 */
public class TokenDoor extends Cell {
    private final int tokenCost;

    /**
     * Constructor to create a new instance.
     *
     * @param tokenCost cost in tokens to open the door.
     */
    public TokenDoor(int tokenCost) {
        super(false, "door_token");
        this.tokenCost = tokenCost;
    }

    public int getTokenCost() {
        return tokenCost;
    }
}
