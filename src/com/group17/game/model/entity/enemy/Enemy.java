package com.group17.game.model.entity.enemy;

import com.group17.game.core.Position;
import com.group17.game.model.entity.Direction;
import com.group17.game.model.entity.Player;
import com.group17.game.model.world.Ground;
import com.group17.game.model.world.Level;

import java.io.Serializable;

/**
 * Parent abstract class for all enemy entities. Includes methods for core functionality of enemies.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public abstract class Enemy implements Serializable {
    final Level level;
    private final EnemyType enemyType;
    protected Position position;
    protected Direction direction;

    /**
     * Abstract constructor to set the attributes of the enemy.
     *
     * @param enemyType type of enemy.
     * @param position  start position.
     * @param direction starting direction.
     * @param level     reference to the parent level.
     */
    Enemy(EnemyType enemyType, Position position, Direction direction,
          Level level) {
        this.position = position;
        this.direction = direction;
        this.enemyType = enemyType;
        this.level = level;
    }

    public abstract void move(Player player);

    public Position getPosition() {
        return position;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    /**
     * Updates the enemies' position.
     *
     * @param nextPosition the position to move to.
     * @return true iff the enemy moved successfully.
     */
    boolean move(Position nextPosition) {
        if (canMove(nextPosition)) {
            position = nextPosition;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the enemy can move to the specified position.
     *
     * @param nextPosition position to check.
     * @return true iff the enemy can move to the specified position.
     */
    boolean canMove(Position nextPosition) {
        return level.getCell(nextPosition) instanceof Ground;
    }

    /**
     * Enum for the type of enemy with the corresponding sprite file name attached.
     *
     * @author Laurence Rawlings
     * @version 1.0
     */
    public enum EnemyType {
        smart("enemy_smart"),
        dumb("enemy_dumb"),
        wall("enemy_wall"),
        line("enemy_line");

        private final String spriteName;

        EnemyType(String spriteName) {
            this.spriteName = spriteName;
        }

        public String getSpriteName() {
            return spriteName;
        }
    }
}
