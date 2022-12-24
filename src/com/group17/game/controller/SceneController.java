package com.group17.game.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller to manage scene switching and language selection.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public class SceneController {
    private static final List<String> languages = new ArrayList<String>() {
        {
            add("EN");
            add("UK");
            add("RU");
            add("FA");
            add("TR");
        }
    };
    private static Stage main;
    private static ResourceBundle languageBundle = ResourceBundle
            .getBundle("com.group17.game.locale.lang", new Locale("en"));

    public static ResourceBundle getLanguageBundle() {
        return languageBundle;
    }

    static List<String> getLanguages() {
        return languages;
    }

    public static void setMain(Stage main) {
        SceneController.main = main;
    }

    /**
     * Switches the scene of the current stage.
     *
     * @param scene the scene to switch to.
     */
    public static void activate(Scene scene) {
        main.setScene(scene);
    }

    /**
     * Loads in the language bundle for the specified language.
     *
     * @param language the language code of the locale to be loaded.
     */
    static void loadLanguage(String language) {
        Locale locale = new Locale(language.toLowerCase());
        languageBundle = ResourceBundle
                .getBundle("com.group17.game.locale.lang", locale);
    }
}
