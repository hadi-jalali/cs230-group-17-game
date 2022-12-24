package com.group17.game.core;

import com.group17.game.model.entity.Player;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Methods to manage profiles. Serialise profiles to save and load them.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public class ProfileManager {
    private static final String PROFILE_DIR =
            "./src/com/group17/game/resources/profiles";
    private static final String PROFILE_FILE_EXTENSION = "save";
    private static Profile activeProfile;
    private static Player.Skin character = Player.Skin.man;

    public static String getProfileDir() {
        return PROFILE_DIR;
    }

    public static Player.Skin getCharacter() {
        return character;
    }

    public static void setCharacter(Player.Skin character) {
        ProfileManager.character = character;
    }

    public static Profile getActiveProfile() {
        return activeProfile;
    }

    public static void setActiveProfile(Profile activeProfile) {
        ProfileManager.activeProfile = activeProfile;
    }

    /**
     * Loads a profile of the specified name from the file system.
     *
     * @param name the name of the profile to be loaded.
     * @return De-serialized profile object.
     */
    public static Profile load(String name) {
        if (exists(name)) {
            Profile profile = null;
            try {
                FileInputStream file = new FileInputStream(
                        PROFILE_DIR + "/" + name + "." +
                                PROFILE_FILE_EXTENSION);
                ObjectInputStream serial = new ObjectInputStream(file);

                profile = (Profile) serial.readObject();

                serial.close();
                file.close();
            } catch (IOException e) {
                System.out.println("Save file not Found!");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return profile;
        } else {
            return null;
        }
    }

    /**
     * Save a profile. Serialise the passed profile object and save it to the file system.
     *
     * @param profile the profile to be saved.
     */
    public static void save(Profile profile) {
        try {
            FileOutputStream file = new FileOutputStream(
                    PROFILE_DIR + "/" + profile.toString() + "." +
                            PROFILE_FILE_EXTENSION);
            ObjectOutputStream serial = new ObjectOutputStream(file);

            serial.writeObject(profile);

            serial.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if a profile exists.
     *
     * @param name name of the profile to check.
     * @return true iff the profile exists.
     */
    public static boolean exists(String name) {
        return getProfileNames().contains(name);
    }

    /**
     * Deletes the specified profile.
     *
     * @param name name of the profile to delete.
     */
    public static void delete(String name) {
        try {
            if (exists(name)) {
                try {
                    Files.deleteIfExists(Paths.get(
                            PROFILE_DIR + "/" + name + "." +
                                    PROFILE_FILE_EXTENSION));
                } catch (NoSuchFileException e) {
                    e.printStackTrace();
                    System.out.println("No such file/directory exists");
                } catch (DirectoryNotEmptyException e) {
                    e.printStackTrace();
                    System.out.println("Directory is not empty.");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Invalid permissions.");
                }
            }
        } catch (Exception e) {
            System.out.println("Profile not found!");
            e.printStackTrace();
        }

    }

    /**
     * Get a list of the profile names on the file system.
     *
     * @return list of profile names.
     */
    public static List<String> getProfileNames() {
        File profileDirectory = new File(PROFILE_DIR);
        File[] saveFiles = profileDirectory.listFiles();
        List<String> profiles = new ArrayList<>();

        try {
            if (saveFiles != null) {
                for (File save : saveFiles) {
                    String[] savePath = save.getAbsolutePath().split("\\.");
                    if (savePath[savePath.length - 1]
                            .equals(PROFILE_FILE_EXTENSION) && save.isFile()) {
                        String[] name = save.getName().split("\\.");
                        profiles.add(String.join(".",
                                Arrays.copyOf(name, name.length - 1)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profiles;
    }

    /**
     * Get list of rofile names that have completed the specified level.
     *
     * @param levelName level to have been completed.
     * @return the list of profiles names that have completed the level.
     */
    static List<Profile> getProfiles(String levelName) {
        List<Profile> profiles = getProfiles();
        List<Profile> returnProfiles = new ArrayList<>();

        for (Profile profile : profiles) {
            if (profile.levelCompleted(levelName)) {
                returnProfiles.add(profile);
            }
        }
        return returnProfiles;
    }

    private static List<Profile> getProfiles() {
        List<Profile> profiles = new ArrayList<>();
        for (String name : getProfileNames()) {
            profiles.add(load(name));
        }
        return profiles;
    }
}
