package com.group17.game;

import com.group17.game.controller.ProfilesController;
import com.group17.game.controller.SceneController;
import com.group17.game.core.ProfileManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * Launches the game javafx window. Entry point for the application.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public class Main extends Application {
    /**
     * Auto generated method to launch javafx application.
     *
     * @param args launch arguments
     */
    public static void main(String[] args) {
        File profilesDirectory = new File(ProfileManager.getProfileDir());
        if (!profilesDirectory.exists()) {
            profilesDirectory.mkdir();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneController.setMain(primaryStage);

        primaryStage
                .setTitle("Indiana Jones and the Last Assignment - Group 17");

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("view/profiles.fxml"));
        Parent root = loader.load();
        ProfilesController profiles = loader.getController();
        profiles.onLoad();

        SceneController.activate(new Scene(root, 800, 800));
        primaryStage.show();
    }

    /**
     * Overridden stop method to save active profile if game window closed.
     */
    @Override
    public void stop() {
        if (ProfileManager.getActiveProfile() != null) {
            ProfileManager.save(ProfileManager.getActiveProfile());
        }
    }
}
