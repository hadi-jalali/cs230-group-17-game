package com.group17.game.controller;

/**
 * Template for FXML controller classes.
 *
 * @author Laurence Rawlings
 * @version 1.0
 */
public interface Controller {
    /**
     * Sets the language for the application. Loads a new Locale.
     */
    void setLanguage();

    /**
     * Called after a scene is initialized. Updates scene elements that need to be set at the scene start.
     */
    void onLoad();
}
