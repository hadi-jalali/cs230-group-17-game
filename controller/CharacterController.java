package com.group17.game.controller;

import com.group17.game.core.LevelRenderer;
import com.group17.game.core.ProfileManager;
import com.group17.game.model.entity.Player;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Controller for Character Selection scene.
 *
 * @author Sam Murphy
 * @version 1.0
 */
public class CharacterController implements Controller {
    @FXML
    private ComboBox<String> cmb_language;

    @FXML
    private Label lbl_currentProfile;

    @FXML
    private Label lbl_profile;

    @FXML
    private ImageView img_man;

    @FXML
    private ImageView img_woman;

    @FXML
    private Label btn_back;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_subtitle;

    @FXML
    void onClickBtnBack(MouseEvent event) {
        back();
    }

    @FXML
    void onClickImgMan(MouseEvent event) {
        ProfileManager.setCharacter(Player.Skin.man);
        back();
    }

    @FXML
    void onClickImgWoman(MouseEvent event) {
        ProfileManager.setCharacter(Player.Skin.woman);
        back();
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
                .getString("character_title"));
        lbl_subtitle.setText(SceneController.getLanguageBundle()
                .getString("character_subtitle"));
        lbl_currentProfile.setText(SceneController.getLanguageBundle()
                .getString("scene_currentProfile"));
        btn_back.setText(
                SceneController.getLanguageBundle().getString("scene_back"));

        Image man = LevelRenderer.getSprite("player");
        Image woman = LevelRenderer.getSprite("player2");

        img_man.setImage(man);
        img_woman.setImage(woman);


        if (ProfileManager.getActiveProfile() != null) {
            lbl_profile.setText(ProfileManager.getActiveProfile().toString());
        } else {
            lbl_profile.setText("");
        }
    }

    private void back() {
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
}
