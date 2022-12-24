package com.group17.game.model.entity.enemy;

import com.group17.game.core.Position;
import com.group17.game.model.entity.Direction;
import com.group17.game.model.entity.Player;
import com.group17.game.model.world.Level;

/**
 * Models the dumb following enemy in the game.
 *
 * @author Vlad Kashtelynov
 * @version 2.0
 */
public class DumbFollowingEnemy extends Enemy {
    private Position nextDumbPosition;

    /**
     * Create a new dumb enemy.
     *
     * @param position      start position.
     * @param direction     starting direction.
     * @param level         reference to the parent level.
     * @param initialTarget initial target i.e. the spawn of the player.
     */
    public DumbFollowingEnemy(Position position, Direction direction,
                              Level level, Position initialTarget) {
        super(EnemyType.dumb, position, direction, level);
        moveDumb(new Player(initialTarget));
    }

    @Override
    public void move(Player player) {
        move(nextDumbPosition);
        moveDumb(player);
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
