package com.group17.game.model.entity.enemy;

import com.group17.game.core.Position;
import com.group17.game.model.entity.Direction;
import com.group17.game.model.entity.Player;
import com.group17.game.model.world.Level;

import java.util.Objects;

/**
 * Models the line following enemy in the game.
 *
 * @author Vlad Kashtelyanov
 * @version 1.0
 */
public class LineFollowingEnemy extends Enemy {
    /**
     * Create a new line enemy.
     *
     * @param position  start position.
     * @param direction starting direction.
     * @param level     reference to the parent level.
     */
    public LineFollowingEnemy(Position position, Direction direction,
                              Level level) {
        super(EnemyType.line, position, direction, level);
    }

    @Override
    public void move(Player player) {
        if (!move(Position.nextPosition(position, direction))) {
            direction = direction.flip();
            move(Position
                    .nextPosition(position, Objects.requireNonNull(direction)));
        }
    }
}
