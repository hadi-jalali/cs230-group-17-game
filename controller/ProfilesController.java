package com.group17.game.controller;

import com.group17.game.core.Profile;
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
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Controller for the Profile Selection scene.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public class ProfilesController implements Controller {
    @FXML
    private ComboBox<String> cmb_language;

    @FXML
    private Label lbl_currentProfile;

    @FXML
    private Label lbl_profile;

    @FXML
    private ListView<String> lst_profiles;

    @FXML
    private Label btn_create;

    @FXML
    private Label btn_delete;

    @FXML
    private Label btn_enter;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_subtitle;

    @FXML
    void initialize() {
        ObservableList<String> profiles = FXCollections
                .observableArrayList(ProfileManager.getProfileNames());
        lst_profiles.setItems(profiles);
    }

    @FXML
    void onClickBtnEnter(MouseEvent event) {
        String selectedProfile =
                lst_profiles.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            ProfileManager
                    .setActiveProfile(ProfileManager.load(selectedProfile));
            enter();
        } else if (ProfileManager.getActiveProfile() != null) {
            enter();
        } else {
            MessageController.showMessage(SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_title"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_head"),
                    SceneController.getLanguageBundle()
                            .getString("msg_selectProfile_body"));
        }
    }

    @FXML
    void onClickBtnCreate(MouseEvent event) {
        String name = MessageController.inputDialog(
                SceneController.getLanguageBundle()
                        .getString("msg_createProfile_title"),
                SceneController.getLanguageBundle()
                        .getString("msg_createProfile_head"),
                SceneController.getLanguageBundle()
                        .getString("msg_createProfile_body"),
                SceneController.getLanguageBundle()
                        .getString("msg_createProfile_default"));
        if (name != null) {
            if (!ProfileManager.exists(name)) {
                Profile profile = new Profile(name);
                ProfileManager.save(profile);
                ProfileManager.setActiveProfile(profile);
                initialize();
                onLoad();
            } else {
                MessageController.showMessage(
                        SceneController.getLanguageBundle()
                                .getString("msg_profileExists_title"),
                        SceneController.getLanguageBundle()
                                .getString("msg_profileExists_head"),
                        SceneController.getLanguageBundle()
                                .getString("msg_profileExists_body"));
            }
        }
    }

    @FXML
    void onClickBtnDelete(MouseEvent event) {
        try {
            String profile = lst_profiles.getSelectionModel().getSelectedItem();
            if (profile != null) {
                if (ProfileManager.getActiveProfile() != null) {
                    if (profile.equals(ProfileManager.getActiveProfile()
                            .toString())) {
                        ProfileManager.setActiveProfile(null);
                    }
                }
                ProfileManager.delete(profile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialize();
        onLoad();
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
                .getString("profiles_title"));
        lbl_subtitle.setText(SceneController.getLanguageBundle()
                .getString("profiles_subtitle"));
        lbl_currentProfile.setText(SceneController.getLanguageBundle()
                .getString("scene_currentProfile"));
        btn_create.setText(SceneController.getLanguageBundle()
                .getString("profiles_create"));
        btn_delete.setText(SceneController.getLanguageBundle()
                .getString("profiles_delete"));
        btn_enter.setText(SceneController.getLanguageBundle()
                .getString("profiles_enter"));


        if (ProfileManager.getActiveProfile() != null) {
            lbl_profile.setText(ProfileManager.getActiveProfile().toString());
        } else {
            lbl_profile.setText("");
        }
    }

    private void enter() {
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
