package com.group17.game.controller;

import com.group17.game.core.Leaderboard;
import com.group17.game.core.LevelReader;
import com.group17.game.core.ProfileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Continue scene after a level is completed.
 *
 * @author Tom Ling
 * @version 1.0
 */
public class ContinueController implements Controller {
    private int nextLevelIndex = 0;
    @FXML
    private ComboBox<String> cmb_language;
    @FXML
    private Label lbl_profile;
    @FXML
    private ListView<String> lst_times;
    @FXML
    private Label btn_menu;
    @FXML
    private Label btn_continue;
    @FXML
    private Label lbl_title;
    @FXML
    private Label lbl_subtitle;
    @FXML
    private Label lbl_currentProfile;

    void setNextLevelIndex(int nextLevelIndex) {
        this.nextLevelIndex = nextLevelIndex;
    }

    @FXML
    void onClickBtnMenu(MouseEvent event) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("../view/menu.fxml"));
            Parent root = loader.load();
            MenuController menu = loader.getController();
            menu.onLoad();

            SceneController.activate(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnContinue(MouseEvent event) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("../view/game.fxml"));
            Parent root = loader.load();
            GameController game = loader.getController();

            Scene scene = new Scene(root);
            scene.addEventFilter(KeyEvent.KEY_PRESSED, game::keyPressed);
            ProfileManager.getActiveProfile().newGame(nextLevelIndex);

            game.onLoad();
            SceneController.activate(scene);
        } catch (IOException e) {
            e.printStackTrace();
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

        lbl_title.setText(SceneController.getLanguageBundle()
                .getString("continue_title"));
        lbl_subtitle.setText(SceneController.getLanguageBundle()
                .getString("continue_subtitle"));
        lbl_currentProfile.setText(SceneController.getLanguageBundle()
                .getString("scene_currentProfile"));
        btn_menu.setText(
                SceneController.getLanguageBundle().getString("scene_menu"));
        btn_continue.setText(SceneController.getLanguageBundle()
                .getString("continue_continue"));

        List<String> topProfiles = Leaderboard.getTopTimes(
                LevelReader.getLevelQueue().get(nextLevelIndex - 1).toString(),
                ProfileManager.getProfileNames().size());
        List<String> topTimes = new ArrayList<>();

        for (String profile : topProfiles) {
            topTimes.add((topProfiles.indexOf(profile) + 1) + ". " + profile +
                    " - " + Leaderboard.formatTime(Leaderboard
                    .getProfileTime(profile,
                            LevelReader.getLevelQueue().get(nextLevelIndex - 1)
                                    .toString())));
        }

        ObservableList times = FXCollections.observableArrayList(topTimes);
        lst_times.setItems(times);

        if (ProfileManager.getActiveProfile() != null) {
            lbl_profile.setText(ProfileManager.getActiveProfile().toString());
        } else {
            lbl_profile.setText("");
        }
    }
}
