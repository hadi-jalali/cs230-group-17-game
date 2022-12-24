package com.group17.game.core;

import java.io.Serializable;

/**
 * Models a node on a graph. Used by the Graph class.
 *
 * @author Vlad Kashtelyanov
 * @version 1.0
 */
public class Node extends Graph implements Serializable {
    final Position n;
    Node prev;
    boolean prevVisited;
    private boolean visited;

    /**
     * Node constructor to create a node at the specified position.
     *
     * @param n position of the node.
     */
    public Node(Position n) {
        this.n = n;
        visited = false;
        prevVisited = false;
    }

    public Position getPos() {
        return this.n;
    }

    void visit() {
        visited = true;
    }

    void setPrev(Node prev) {
        this.prev = prev;
    }

    boolean isVisited() {
        return this.visited;
    }

    @Override
    public String toString() {
        return "Node (" + n.x() + " " + n.y() + ")";
    }
}
