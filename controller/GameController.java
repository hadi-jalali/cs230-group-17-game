package com.group17.game.controller;

import com.group17.game.core.Game;
import com.group17.game.core.Leaderboard;
import com.group17.game.core.LevelRenderer;
import com.group17.game.core.ProfileManager;
import com.group17.game.model.entity.Direction;
import com.group17.game.model.world.Level;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controller for the game scene. Handles user key input and checks if the game is won.
 *
 * @author Laurence Rawlings
 * @version 2.0
 */
public class GameController implements Controller {
    private Game game;
    private Timeline timer;

    @FXML
    private Label lbl_inventory;

    @FXML
    private ListView<String> lst_inventory;

    @FXML
    private Label lbl_fov;

    @FXML
    private Label btn_zoomIn;

    @FXML
    private Label btn_zoomOut;

    @FXML
    private Canvas canvas;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_timer;

    @FXML
    private Label btn_exit;

    @FXML
    private ComboBox<String> cmb_language;

    @FXML
    private Label lbl_currentProfile;

    @FXML
    private Label lbl_profile;

    @FXML
    void initialize() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            int time = game.getCurrentLevel().getTime();
            game.getCurrentLevel().setTime(++time);
            lbl_timer.setText(Leaderboard.formatTime(time));
            drawGame();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    @FXML
    void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                game.move(Direction.up);
                break;
            case DOWN:
                game.move(Direction.down);
                break;
            case LEFT:
                game.move(Direction.left);
                break;
            case RIGHT:
                game.move(Direction.right);
                break;
            default:
                break;
        }

        if (game.getPlayer().getPosition()
                .equals(game.getCurrentLevel().getFinish())) {
            int currentLevelIndex = game.getLevelIndex();
            Level currentLevel = game.getCurrentLevel();
            if (ProfileManager.getActiveProfile().getHighestLevel() <
                    game.getLevelIndex()) {
                ProfileManager.getActiveProfile()
                        .setHighestLevel(game.getLevelIndex());
            }
            if (currentLevel.getTime() < ProfileManager.getActiveProfile()
                    .getLevelTime(currentLevel.toString())) {
                ProfileManager.getActiveProfile()
                        .setLevelTime(currentLevel.toString(),
                                currentLevel.getTime());
            }
            if (!game.nextLevel()) {
                try {
                    timer.stop();
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("../view/win.fxml"));
                    Parent root = loader.load();
                    WinController win = loader.getController();
                    ProfileManager.save(ProfileManager.getActiveProfile());

                    win.onLoad();
                    SceneController.activate(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    timer.stop();
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("../view/continue.fxml"));
                    Parent root = loader.load();
                    ContinueController continueC = loader.getController();
                    ProfileManager.save(ProfileManager.getActiveProfile());
                    continueC.setNextLevelIndex(currentLevelIndex + 1);


                    continueC.onLoad();

                    SceneController.activate(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ObservableList items =
                FXCollections.observableArrayList(game.getPlayer().getItems());
        lst_inventory.setItems(items);

        drawGame();
        event.consume();
    }

    @FXML
    void onClickBtnSave(MouseEvent event) {
        try {
            timer.stop();
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("../view/menu.fxml"));
            Parent root = loader.load();
            MenuController menu = loader.getController();
            ProfileManager.save(ProfileManager.getActiveProfile());
            menu.onLoad();

            SceneController.activate(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnZoomIn(MouseEvent event) {
        if (game.getFov() > 1) {
            game.setFov(game.getFov() - 1);
            drawGame();
        }
    }

    @FXML
    void onClickBtnZoomOut(MouseEvent event) {
        if (game.getFov() < 4) {
            game.setFov(game.getFov() + 1);
            drawGame();
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

        ObservableList items =
                FXCollections.observableArrayList(game.getPlayer().getItems());
        lst_inventory.setItems(items);
    }

    @Override
    public void onLoad() {
        cmb_language.setItems(FXCollections
                .observableArrayList(SceneController.getLanguages()));

        lbl_currentProfile.setText(SceneController.getLanguageBundle()
                .getString("scene_currentProfile"));
        lbl_inventory.setText(SceneController.getLanguageBundle()
                .getString("game_inventory"));
        lbl_fov.setText(
                SceneController.getLanguageBundle().getString("game_fov"));
        btn_exit.setText(SceneController.getLanguageBundle()
                .getString("game_saveAndReturn"));
        btn_zoomIn.setText(
                SceneController.getLanguageBundle().getString("game_zoomIn"));
        btn_zoomOut.setText(
                SceneController.getLanguageBundle().getString("game_zoomOut"));


        game = ProfileManager.getActiveProfile().getGame();
        if (ProfileManager.getActiveProfile() != null) {
            lbl_profile.setText(ProfileManager.getActiveProfile().toString());
        } else {
            lbl_profile.setText("");
        }
        drawGame();
        lbl_timer.setText(
                Leaderboard.formatTime(game.getCurrentLevel().getTime()));
    }

    private void drawGame() {
        LevelRenderer.render(game, canvas);
        lbl_title.setText(game.getCurrentLevel().toString());
    }
}