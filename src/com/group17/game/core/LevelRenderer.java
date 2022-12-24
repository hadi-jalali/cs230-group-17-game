package com.group17.game.core;

import com.group17.game.model.entity.Player;
import com.group17.game.model.world.Level;
import com.group17.game.model.world.TokenDoor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The purpose of this class is to draw out the game itself. Defines the size of sprite's
 * and of the canvas.
 *
 * @author Laurence Rawlings
 * @version 2.0
 */
public class LevelRenderer {
    private static final String SPRITE_DIR =
            "com/group17/game/resources/sprites";
    private static final String SPRITE_FILE_EXTENSION = "png";
    private static int spriteWidth = 100;
    private static int spriteHeight = 100;

    private static void setSpriteWidth(int spriteWidth) {
        LevelRenderer.spriteWidth = spriteWidth;
    }

    private static void setSpriteHeight(int spriteHeight) {
        LevelRenderer.spriteHeight = spriteHeight;
    }

    /**
     * Get a sprite's image according to the sprite's filename
     *
     * @param spriteName the sprites filename.
     * @return image object loaded from the sprite file.
     */
    public static Image getSprite(String spriteName) {
        try {
            return new Image(
                    SPRITE_DIR + "/" + spriteName + "." + SPRITE_FILE_EXTENSION,
                    spriteWidth, spriteHeight, false, true);
        } catch (IllegalArgumentException e) {
            try {
                return new Image(
                        SPRITE_DIR + "/missing." + SPRITE_FILE_EXTENSION,
                        spriteWidth, spriteHeight, false, true);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get a sprite's image according to the sprite's filename and set its width and height.
     *
     * @param spriteName the sprites filename.
     * @param height     height to load the image.
     * @param width      width to load the image.
     * @return image object loaded from the sprite file.
     */
    public static Image getSprite(String spriteName, int width, int height) {
        try {
            return new Image(
                    SPRITE_DIR + "/" + spriteName + "." + SPRITE_FILE_EXTENSION,
                    width, height, false, true);
        } catch (IllegalArgumentException e) {
            try {
                return new Image(
                        SPRITE_DIR + "/missing." + SPRITE_FILE_EXTENSION,
                        spriteWidth, spriteHeight, false, true);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Render the game canvas by drawing on all the sprites and writing required text.
     *
     * @param game   the game to render the state of.
     * @param canvas the canvas to draw the frame to.
     */
    public static void render(Game game, Canvas canvas) {
        LevelRenderer.setSpriteHeight(
                (int) canvas.getHeight() / (game.getFov() * 2 + 1));
        LevelRenderer.setSpriteWidth(
                (int) canvas.getWidth() / (game.getFov() * 2 + 1));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int fov = game.getFov();
        Level level = game.getCurrentLevel();
        Player player = game.getPlayer();
        Position playerPosition = player.getPosition();

        int drawX = 0;
        int drawY = 0;

        for (int y = playerPosition.y() - fov;
             y <= playerPosition.y() + fov; y++) {
            for (int x = playerPosition.x() - fov;
                 x <= playerPosition.x() + fov; x++) {
                if (y < 0 || x < 0 || y > level.getHeight() - 1 ||
                        x > level.getWidth() - 1) {
                    gc.drawImage(getSprite("null"), drawX * spriteWidth,
                            drawY * spriteHeight);
                } else {
                    if (level.getCell(new Position(x, y)) != null) {
                        gc.drawImage(getSprite(level.getCell(new Position(x, y))
                                        .getSpriteName()), drawX * spriteWidth,
                                drawY * spriteHeight);
                        if (level.getCell(
                                new Position(x, y)) instanceof TokenDoor) {
                            gc.setFill(Color.web("#ffffff"));
                            gc.setFont(Font.font("Segoe UI", 18));
                            gc.fillText(Integer.toString(((TokenDoor) level
                                            .getCell(new Position(x, y)))
                                            .getTokenCost()), drawX *
                                            spriteWidth +
                                            (float) spriteWidth / 2,
                                    drawY * spriteHeight +
                                            (float) spriteHeight / 2);
                        }
                    } else {
                        gc.drawImage(getSprite("null"), drawX * spriteWidth,
                                drawY * spriteHeight);
                    }
                    if (level.getItem(new Position(x, y)) != null) {
                        gc.drawImage(getSprite(level.getItem(new Position(x, y))
                                        .getSpriteName()), drawX * spriteWidth,
                                drawY * spriteHeight);
                    }
                    if (level.getEnemy(new Position(x, y)) != null) {
                        gc.drawImage(getSprite(
                                level.getEnemy(new Position(x, y))
                                        .getSpriteName()), drawX * spriteWidth,
                                drawY * spriteHeight);
                    }
                }
                drawX++;
            }
            drawX = 0;
            drawY++;
        }
        gc.drawImage(getSprite(player.getSpriteName()), fov * spriteWidth,
                fov * spriteHeight);
        gc.drawImage(getSprite("overlay", (int) canvas.getWidth(),
                (int) canvas.getHeight()), 0, 0);
    }
}
