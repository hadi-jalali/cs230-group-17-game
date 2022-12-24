package com.group17.game.model.entity.enemy;

import com.group17.game.core.Graph;
import com.group17.game.core.Node;
import com.group17.game.core.Position;
import com.group17.game.model.entity.Direction;
import com.group17.game.model.entity.Player;
import com.group17.game.model.world.Ground;
import com.group17.game.model.world.Level;

import java.util.LinkedList;

/**
 * Models the smart following enemy in the game.
 *
 * @author Vlad Kashtelyanov
 * @version 4.0
 */
public class SmartFollowingEnemy extends Enemy {
    private Position nextPosition;
    private Position nextDumbPosition;
    private boolean smartFail;

    /**
     * Create a new smart enemy.
     *
     * @param position      start position.
     * @param direction     starting direction.
     * @param level         reference to the parent level.
     * @param initialTarget initial target i.e. the spawn of the player.
     */
    public SmartFollowingEnemy(Position position, Direction direction,
                               Level level, Position initialTarget) {
        super(EnemyType.smart, position, direction, level);
        calculatePath(new Player(initialTarget));
        moveDumb(new Player(initialTarget));
    }

    @Override
    public void move(Player player) {
        if (smartFail) {
            if (!(nextDumbPosition.x() == player.getPosition().x() &&
                    nextDumbPosition.y() == player.getPosition().y())) {
                move(nextDumbPosition);
                smartFail = false;
            }
        } else {
            if (!(nextPosition.x() == player.getPosition().x() &&
                    nextPosition.y() == player.getPosition().y())) {
                move(nextPosition);
            }
        }
        calculatePath(player);
        moveDumb(player);
    }

    private void calculatePath(Player player) {
        Node[][] nodeMap =
                new Node[level.getWidth() - 1][level.getHeight() - 1];

        Graph graph = new Graph();
        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                if (level.getCell(new Position(i, j)) instanceof Ground) {
                    nodeMap[i][j] = new Node(new Position(i, j));
                }
            }
        }

        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                if (level.getCell(new Position(i, j)) != null &&
                        level.getCell(new Position(i, j)) instanceof Ground) {
                    if (level.getCell(new Position(i + 1, j)) != null &&
                            level.getCell(
                                    new Position(i + 1, j)) instanceof Ground) {
                        graph.addEdge(nodeMap[i][j], nodeMap[i + 1][j]);
                    }
                }
            }
        }
        for (int j = 0; j < level.getHeight(); j++) {
            for (int i = 0; i < level.getWidth(); i++) {
                if (level.getCell(new Position(i, j)) != null &&
                        level.getCell(new Position(i, j)) instanceof Ground) {
                    if (level.getCell(new Position(i, j + 1)) != null &&
                            level.getCell(
                                    new Position(i, j + 1)) instanceof Ground) {
                        graph.addEdge(nodeMap[i][j], nodeMap[i][j + 1]);
                    }
                }
            }
        }

        graph.breadthFirstSearch(
                nodeMap[player.getPosition().x()][player.getPosition().y()]);
        LinkedList<Node> shortestPath = graph.findShortestPath(
                nodeMap[player.getPosition().x()][player.getPosition().y()],
                nodeMap[position.x()][position.y()]);

        if (shortestPath != null && shortestPath.size() > 1) {
            nextPosition = shortestPath.get(1).getPos();
            smartFail = false;
        } else {
            smartFail = true;
        }
    }

    private void moveDumb(Player player) {
        int xDif = (position.x() - player.getPosition().x());
        int yDif = (position.y() - player.getPosition().y());

        if (Math.abs(xDif) >= Math.abs(yDif)) {
            if (!moveX(xDif)) {
                moveY(yDif);
            }
        } else {
            if (!moveY(yDif)) {
                moveX(xDif);
            }
        }
    }

    private boolean moveX(int xDif) {
        if (xDif > 0) {
            if (super
                    .canMove(Position.nextPosition(position, Direction.left))) {
                nextDumbPosition =
                        Position.nextPosition(position, Direction.left);
                return true;
            }
        } else if (xDif < 0) {
            if (super.canMove(
                    Position.nextPosition(position, Direction.right))) {
                nextDumbPosition =
                        Position.nextPosition(position, Direction.right);
                return true;
            }
        }
        return false;
    }

    private boolean moveY(int yDif) {
        if (yDif > 0) {
            if (super.canMove(Position.nextPosition(position, Direction.up))) {
                nextDumbPosition =
                        Position.nextPosition(position, Direction.up);
                return true;
            }
        } else if (yDif < 0) {
            if (super
                    .canMove(Position.nextPosition(position, Direction.down))) {
                nextDumbPosition =
                        Position.nextPosition(position, Direction.down);
                return true;
            }
        }
        return false;
    }
}

