package com.badlogic.drop.lwjgl3;

import com.badlogic.drop.RainkeeperGame;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Rainkeeper - Assignment 3");
        config.setWindowedMode(960, 600);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new RainkeeperGame(), config);
    }
}
