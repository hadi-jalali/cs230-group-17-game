package com.group17.game.model.world;

import com.group17.game.core.Position;
import com.group17.game.model.entity.Player;
import com.group17.game.model.entity.enemy.Enemy;
import com.group17.game.model.entity.item.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Models a level in the game. Track the positions of all cells, items and enemies.
 *
 * @author Laurence Rawlings
 * @version 3.0
 */
public class Level implements Comparable<Level>, Serializable, Cloneable {
    private final Cell[][] cells;
    private final Item[][] items;
    private final List<Enemy> enemies;
    private final int levelNumber;
    private final String levelName;
    private final Position start;
    private final Position finish;
    private final int width;
    private final int height;
    private Enemy.EnemyType[][] enemyPositions;
    private int time;

    /**
     * Create a new instance of a level with the specified parameters.
     *
     * @param start       player spawn point.
     * @param finish      level finish position.
     * @param levelNumber level number. Levels are player in order of level number.
     * @param levelName   name of the level.
     * @param width       width in cells of the level.
     * @param height      height in cells of the level.
     */
    public Level(Position start, Position finish, int levelNumber,
                 String levelName, int width, int height) {
        this.levelNumber = levelNumber;
        this.levelName = levelName;
        this.start = start;
        this.finish = finish;
        this.width = width;
        this.height = height;

        cells = new Cell[height][width];
        items = new Item[height][width];
        enemyPositions = new Enemy.EnemyType[height][width];
        enemies = new ArrayList<>();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Position getStart() {
        return start;
    }

    public Position getFinish() {
        return finish;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int getLevelNumber() {
        return levelNumber;
    }

    @Override
    public String toString() {
        return levelName;
    }

    @Override
    public int compareTo(Level level) {
        return Integer.compare(levelNumber, level.getLevelNumber());
    }

    /**
     * Get the cell at the specified position.
     *
     * @param position position of the cell.
     * @return cell at the position.
     */
    public Cell getCell(Position position) {
        return cells[position.y()][position.x()];
    }

    /**
     * Get the item at the specified position.
     *
     * @param position position of the item.
     * @return item at the position.
     */
    public Item getItem(Position position) {
        return items[position.y()][position.x()];
    }

    /**
     * Set the cell at the specified position.
     *
     * @param position position to set.
     * @param cell     the cell.
     */
    public void setCell(Position position, Cell cell) {
        cells[position.y()][position.x()] = cell;
    }

    /**
     * Set the item at the specified position.
     *
     * @param position position to set.
     * @param item     the item.
     */
    public void setItem(Position position, Item item) {
        items[position.y()][position.x()] = item;
    }

    /**
     * Adds an enemy to the level.
     *
     * @param enemy the enemy instance.
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    /**
     * Update the position of all enemies in the enemy list.
     */
    public void updateEnemyPositions() {
        enemyPositions = new Enemy.EnemyType[height][width];
        for (Enemy enemy : enemies) {
            enemyPositions[enemy.getPosition().y()][enemy.getPosition().x()] =
                    enemy.getEnemyType();
        }
    }

    /**
     * Calls the move method on each enemy in the enemy list.
     *
     * @param player reference to the player to act as target for the enemies.
     */
    public void moveEnemies(Player player) {
        for (Enemy enemy : enemies) {
            enemy.move(player);
            updateEnemyPositions();
        }
    }

    /**
     * MGet the type of enemy at the specified position.
     *
     * @param position the position to check
     * @return the enemy type of the enemy at the position. Return null if no enemy present at the position.
     */
    public Enemy.EnemyType getEnemy(Position position) {
        return enemyPositions[position.y()][position.x()];
    }
}
