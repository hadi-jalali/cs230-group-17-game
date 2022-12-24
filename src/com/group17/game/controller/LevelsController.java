package com.group17.game.controller;

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
 * Controller for the Level Selection scene.
 *
 * @author Sam Murphy
 * @version 1.0
 */
public class LevelsController implements Controller {
    @FXML
    private ComboBox<String> cmb_language;

    @FXML
    private Label lbl_currentProfile;

    @FXML
    private Label lbl_profile;

    @FXML
    private ListView<?> lst_levels;

    @FXML
    private Label btn_back;

    @FXML
    private Label btn_play;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_subtitle;

    @FXML
    void onClickBtnBack(MouseEvent event) {
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
    void onClickBtnPlay(MouseEvent event) {
        if (lst_levels.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("../view/game.fxml"));
                Parent root = loader.load();
                GameController game = loader.getController();

                Scene scene = new Scene(root);
                scene.addEventFilter(KeyEvent.KEY_PRESSED, game::keyPressed);
                ProfileManager.getActiveProfile().newGame(
                        lst_levels.getSelectionModel().getSelectedIndex());

                game.onLoad();
                SceneController.activate(scene);
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
                SceneController.getLanguageBundle().getString("level_title"));
        lbl_subtitle.setText(SceneController.getLanguageBundle()
                .getString("level_subtitle"));
        lbl_currentProfile.setText(SceneController.getLanguageBundle()
                .getString("scene_currentProfile"));
        btn_back.setText(
                SceneController.getLanguageBundle().getString("scene_back"));
        btn_play.setText(
                SceneController.getLanguageBundle().getString("level_play"));

        List<String> availableLevels = new ArrayList<>();
        for (int i = 0; i <=
                ProfileManager.getActiveProfile().getHighestLevel() + 1; i++) {
            if (i < LevelReader.getLevelQueue().size()) {
                availableLevels
                        .add(LevelReader.getLevelQueue().get(i).toString());
            }
        }
        ObservableList levels =
                FXCollections.observableArrayList(availableLevels);
        lst_levels.setItems(levels);

        if (ProfileManager.getActiveProfile() != null) {
            lbl_profile.setText(ProfileManager.getActiveProfile().toString());
        } else {
            lbl_profile.setText("");
        }
    }
}
