package com.group17.game.controller;

import com.group17.game.core.MessageOfTheDay;
import com.group17.game.core.ProfileManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller for the Main Menu scene.
 *
 * @author Laurence Rawlings
 * @version 2.0
 */
public class MenuController implements Controller {
    @FXML
    private ComboBox<String> cmb_language;

    @FXML
    private Label lbl_currentProfile;

    @FXML
    private Label lbl_profile;

    @FXML
    private Label btn_continue;

    @FXML
    private Label btn_level;

    @FXML
    private Label btn_new;

    @FXML
    private Label btn_leaderboard;

    @FXML
    private Label btn_profile;

    @FXML
    private Label btn_quit;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_motd;

    @FXML
    private Label btn_character;


    @FXML
    void initialize() {
        lbl_motd.setText(MessageOfTheDay.get());
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(30),
                event -> lbl_motd.setText(MessageOfTheDay.get())));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    @FXML
    void onClickBtnContinue(MouseEvent event) {
        if (ProfileManager.getActiveProfile() == null) {
            MessageController.showMessage(SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_title"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_head"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_body"));
        } else {
            ProfileManager.getActiveProfile().getGame().getPlayer()
                    .setSprite(ProfileManager.getCharacter());
            startGame();
        }
    }

    @FXML
    void onClickBtnQuit(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onClickBtnNew(MouseEvent event) {
        if (ProfileManager.getActiveProfile() == null) {
            MessageController.showMessage(SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_title"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_head"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_body"));
        } else {
            ProfileManager.getActiveProfile().newGame();
            startGame();
        }
    }

    @FXML
    void onClickBtnProfile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../view/profiles.fxml"));
            Parent root = loader.load();
            ProfilesController profiles = loader.getController();
            profiles.onLoad();

            SceneController.activate(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnCharacter(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../view/character.fxml"));
            Parent root = loader.load();
            CharacterController character = loader.getController();
            character.onLoad();

            SceneController.activate(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnLeaderboard(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("../view/leaderboard.fxml"));
            Parent root = loader.load();
            LeaderboardController leaderboard = loader.getController();
            leaderboard.onLoad();

            SceneController.activate(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnLevel(MouseEvent event) {
        if (ProfileManager.getActiveProfile() == null) {
            MessageController.showMessage(SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_title"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_head"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_body"));
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("../view/levels.fxml"));
                Parent root = loader.load();
                LevelsController levels = loader.getController();
                levels.onLoad();

                SceneController.activate(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    @Override
    public void setLanguage() {
        String language = cmb_language.getSelectionModel().getSelectedItem();
        if (language != null) {
            SceneController.loadLanguage(language);
            onLoad();
        }
    }

    @Override
    public void onLoad() {
        cmb_language.setItems(FXCollections
                .observableArrayList(SceneController.getLanguages()));

        lbl_title.setText(
                SceneController.getLanguageBundle().getString("game_name"));
        lbl_currentProfile.setText(SceneController.getLanguageBundle()
                .getString("scene_currentProfile"));
        btn_continue.setText(SceneController.getLanguageBundle()
                .getString("menu_continueGame"));
        btn_level.setText(SceneController.getLanguageBundle()
                .getString("menu_selectProfile"));
        btn_new.setText(
                SceneController.getLanguageBundle().getString("menu_newGame"));
        btn_leaderboard.setText(SceneController.getLanguageBundle()
                .getString("menu_leaderboard"));
        btn_profile.setText(SceneController.getLanguageBundle()
                .getString("menu_switchProfile"));
        btn_quit.setText(
                SceneController.getLanguageBundle().getString("menu_exit"));
        btn_character.setText(SceneController.getLanguageBundle()
                .getString("menu_character"));


        if (ProfileManager.getActiveProfile() != null) {
            lbl_profile.setText(ProfileManager.getActiveProfile().toString());
        } else {
            lbl_profile.setText("");
        }
    }

    private void startGame() {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("../view/game.fxml"));
            Parent root = loader.load();
            GameController game = loader.getController();

            Scene scene = new Scene(root);
            scene.addEventFilter(KeyEvent.KEY_PRESSED, game::keyPressed);

            game.onLoad();
            SceneController.activate(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
