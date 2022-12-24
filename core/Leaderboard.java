package com.group17.game.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Helper methods to calculate the leaderboards for levels using the profiles completion times.
 *
 * @author Hadi Jalali
 * @version 1.0
 */
public class Leaderboard {
    private static String compareLevel;

    static String getCompareLevel() {
        return compareLevel;
    }

    /**
     * Get top x times for the specified level, where x is the amount to be returned.
     *
     * @param levelName level to check.
     * @param amount    amount of top times to return.
     * @return list of profile names in order of best time.
     */
    public static List<String> getTopTimes(String levelName, int amount) {
        compareLevel = levelName;
        List<Profile> profiles = ProfileManager.getProfiles(levelName);
        Collections.sort(profiles);
        List<String> topProfiles = new ArrayList<>();
        int i = 0;
        for (Profile profile : profiles) {
            if (i >= amount) {
                return topProfiles;
            }
            topProfiles.add(profile.toString());
            i++;
        }
        return topProfiles;
    }

    /**
     * Formats and returns a string when given a time integer in seconds.
     *
     * @param seconds integer to be formatted.
     * @return days, hours, minutes, seconds in string format.
     */
    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int hours = minutes / 60;
        minutes = minutes % 60;
        int days = hours / 24;
        hours = hours % 24;

        if (days > 0) {
            return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else if (hours > 0) {
            return hours + "h " + minutes + "m " + seconds + "s";
        } else if (minutes > 0) {
            return minutes + "m " + seconds + "s";
        }
        return seconds + "s";
    }

    /**
     * Gets the time taken by a specified profile for the specified level.
     *
     * @param profileName profile to read.
     * @param levelName   level to check.
     * @return integer time taken by the profile to complete the level in seconds.
     */
    public static int getProfileTime(String profileName, String levelName) {
        return Objects.requireNonNull(ProfileManager.load(profileName))
                .getLevelTime(levelName);
    }
}
