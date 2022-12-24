package com.group17.game.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a profile in the game. Stores all data for the user.
 *
 * @author Hadi Jalali
 * @version 2.0
 */
public class Profile implements Serializable, Comparable<Profile> {
    private final String name;
    private final Map levelTimes;
    private Game game;
    private int highestLevel;

    /**
     * Create a new profile with the specified name.
     *
     * @param name name of the profile.
     */
    public Profile(String name) {
        this.name = name;
        game = new Game();
        levelTimes = new HashMap();
        highestLevel = -1;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public void setHighestLevel(int highestLevel) {
        this.highestLevel = highestLevel;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Profile profile) {
        String compareLevel = Leaderboard.getCompareLevel();

        if (compareLevel == null) {
            return 0;
        }

        return Integer.compare(getLevelTime(compareLevel),
                profile.getLevelTime(compareLevel));
    }

    /**
     * Get the time that it takes for a user to complete the specified level.
     *
     * @param levelName level name of the level.
     * @return the level time, return infinity if the level hasn't been completed.
     */
    public int getLevelTime(String levelName) {
        if (levelTimes.containsKey(levelName)) {
            return (int) levelTimes.get(levelName);
        } else {
            return (int) Double.POSITIVE_INFINITY;
        }
    }

    /**
     * Check if the profile has completed a level.
     *
     * @param levelName the level name of the level to check.
     * @return true or false if complete or not.
     */
    public boolean levelCompleted(String levelName) {
        return levelTimes.containsKey(levelName);
    }

    /**
     * Set the record time for the specified level.
     *
     * @param levelName level to set the time for.
     * @param time      the time as an integer in seconds.
     */
    public void setLevelTime(String levelName, int time) {
        levelTimes.put(levelName, time);
    }

    /**
     * Create a new game fro the profile.
     */
    public void newGame() {
        game = new Game();
    }

    /**
     * Create a new game starting at the specified level index.
     *
     * @param levelIndex level index to start at.
     */

    public void newGame(int levelIndex) {
        game = new Game(levelIndex);
    }
}
