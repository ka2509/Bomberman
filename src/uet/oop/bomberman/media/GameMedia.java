package uet.oop.bomberman.media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class GameMedia {
    /***** SOUND FILE NAME *****/
    private static  String placeBombSoundFileName = "res/Sound/place_bomb.wav";
    private static String bombExplosionSoundFileName = "res/Sound/bomb_explosion.wav";
    private static String getPowerUpSoundFileName = "res/Sound/power_up.wav";
    private static String winSoundFileName = "res/Sound/win.wav";
    private static String leftRightWalkSoundFileName = "res/Sound/left_right_walk.wav";
    private static String upDownWalkSoundFileName = "res/Sound/up_down_walk.wav";
    private static String deathSoundFileName = "res/Sound/death2.wav";
    private static String gameOverSoundFileName = "res/Sound/game_over.wav";
    private static String restartSoundFileName = "res/Sound/restart.mp3";
    private static String mainMenuSoundFileName = "res/Sound/main_menu.mp3";
    private static String themeSoundFileName = "res/Sound/theme.mp3";
    /***************************/

    private static MediaPlayer placeBombSound;

    public static MediaPlayer getPlaceBombSound() {
        return placeBombSound;
    }
    public static void setPlaceBombSound() {
        placeBombSound = null;
        placeBombSound = new MediaPlayer(new Media(new File(placeBombSoundFileName).toURI().toString()));
    }
    private static MediaPlayer bombExplosionSound;
    public static MediaPlayer getBombExplosionSound() {
        return bombExplosionSound;
    }
    public static void setBombExplosionSound() {
        bombExplosionSound = null;
        bombExplosionSound = new MediaPlayer(new Media(new File(bombExplosionSoundFileName).toURI().toString()));
    }
    private static MediaPlayer getPowerUpSound;
    public static MediaPlayer getGetPowerUpSound() {
        return getPowerUpSound;
    }
    public static void setGetPowerUpSound() {
        getPowerUpSound = null;
        getPowerUpSound = new MediaPlayer(new Media(new File(getPowerUpSoundFileName).toURI().toString()));
    }
    private static MediaPlayer winSound;
    public static MediaPlayer getWinSound() {
        return winSound;
    }
    public static void setWinSound() {
        winSound = null;
        winSound = new MediaPlayer(new Media(new File(winSoundFileName).toURI().toString()));
    }
    private static MediaPlayer leftRightWalkSound;
    public static MediaPlayer getLeftRightWalkSound() {
        return leftRightWalkSound;
    }
    public static void setLeftRightWalkSound() {
        leftRightWalkSound = null;
        leftRightWalkSound = new MediaPlayer(new Media(new File(leftRightWalkSoundFileName).toURI().toString()));
    }
    private static MediaPlayer upDownWalkSound;
    public static MediaPlayer getUpDownWalkSound() {
        return upDownWalkSound;
    }
    public static void setUpDownWalkSound() {
        upDownWalkSound = null;
        upDownWalkSound = new MediaPlayer(new Media(new File(upDownWalkSoundFileName).toURI().toString()));
        upDownWalkSound.setVolume(upDownWalkSound.getVolume() + 1);
    }
    private static MediaPlayer deathSound;
    public static MediaPlayer getDeathSound() {
        return deathSound;
    }
    public static void setDeathSound() {
        deathSound = null;
        deathSound = new MediaPlayer(new Media(new File(deathSoundFileName).toURI().toString()));
    }
    private static MediaPlayer gameOverSound;
    public static MediaPlayer getGameOverSound() {
        return gameOverSound;
    }
    public static void setGameOverSound() {
        gameOverSound = null;
        gameOverSound = new MediaPlayer(new Media(new File(gameOverSoundFileName).toURI().toString()));
    }
    private static MediaPlayer restartSound;
    public static MediaPlayer getRestartSound() {
        return restartSound;
    }
    public static void setRestartSound() {
        restartSound = null;
        restartSound = new MediaPlayer(new Media(new File(restartSoundFileName).toURI().toString()));
    }
    private static MediaPlayer mainMenuSound;
    public static MediaPlayer getMainMenuSound() {
        return mainMenuSound;
    }
    public static void setMainMenuSound() {
        mainMenuSound = null;
        mainMenuSound = new MediaPlayer(new Media(new File(mainMenuSoundFileName).toURI().toString()));
    }
    public static void setMainMenuSoundToNull() {
        mainMenuSound = null;
    }
    private static MediaPlayer themeSound;
    public static MediaPlayer getThemeSound() {
        return themeSound;
    }
    public static void setThemeSound() {
        themeSound = null;
        themeSound = new MediaPlayer(new Media(new File(themeSoundFileName).toURI().toString()));
        themeSound.setVolume(themeSound.getVolume() - 0.7);
    }
    public static void setThemeSoundToNull() {
        themeSound = null;
    }
}
