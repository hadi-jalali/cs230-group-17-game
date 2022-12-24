package com.group17.game.controller;

import com.group17.game.core.Leaderboard;
import com.group17.game.core.LevelReader;
import com.group17.game.core.ProfileManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Game Win scene.
 *
 * @author Tom Ling
 * @version 1.0
 */
public class WinController implements Controller {
    @FXML
    private ComboBox<String> cmb_language;

    @FXML
    private Label lbl_currentProfile;

    @FXML
    private Label lbl_profile;

    @FXML
    private Label lbl_time;

    @FXML
    private Label btn_menu;

    @FXML
    private Label btn_leaderboard;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_subtitle;

    @FXML
    private Label lbl_subtitle2;

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
                SceneController.getLanguageBundle().getString("win_title"));
        lbl_subtitle.setText(
                SceneController.getLanguageBundle().getString("win_subtitle"));
        lbl_subtitle2.setText(
                SceneController.getLanguageBundle().getString("win_subtitle2"));
        lbl_currentProfile.setText(SceneController.getLanguageBundle()
                .getString("scene_currentProfile"));
        btn_leaderboard.setText(SceneController.getLanguageBundle()
                .getString("win_leaderboard"));
        btn_menu.setText(
                SceneController.getLanguageBundle().getString("scene_menu"));


        int totalTime = 0;
        List<String> levels = LevelReader.getLevelNames();
        for (int i = 0;
             i <= ProfileManager.getActiveProfile().getHighestLevel(); i++) {
            if (!(i >= levels.size())) {
                totalTime += Leaderboard.getProfileTime(
                        ProfileManager.getActiveProfile().toString(),
                        levels.get(i));
            }
        }

        lbl_time.setText(Leaderboard.formatTime(totalTime));

        if (ProfileManager.getActiveProfile() != null) {
            lbl_profile.setText(ProfileManager.getActiveProfile().toString());
        } else {
            lbl_profile.setText("");
        }
    }
}
