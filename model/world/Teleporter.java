package com.group17.game.model.world;

import com.group17.game.core.Position;

/**
 * Models the teleporter cell in the game.
 *
 * @author Laurence Rawlings
 * @version 2.0
 */
public class Teleporter extends Cell {
    private final Teleporter destination;
    private final Position position;

    /**
     * Creates a teleporter pair taking start and destination position.
     *
     * @param position            position of the teleporter.
     * @param destinationPosition destination position for the teleporter.
     */
    public Teleporter(Position position, Position destinationPosition) {
        super(true, "Teleporter");
        this.position = position;
        destination = new Teleporter(destinationPosition, this);
    }

    /**
     * Create a single teleporter taking an existing teleporter as it's destination.
     *
     * @param position    position of the teleporter.
     * @param destination destination teleporter.
     */
    private Teleporter(Position position, Teleporter destination) {
        super(true, "Teleporter");
        this.position = position;
        this.destination = destination;
    }

    public Position getPosition() {
        return position;
    }

    public Teleporter getDestination() {
        return destination;
    }
}
