package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;

public class MemoryHelper {

    private final static Preferences prefs = Gdx.app.getPreferences("User saves");

    public static void saveMusicVolume(float volume) {
        prefs.putFloat("MusicVolume", volume).flush();
    }

    public static float loadMusicVolume() {
        if (prefs.contains("MusicVolume")) {
            return prefs.getFloat("MusicVolume");
        }
        saveMusicVolume(1);
        return 1;
    }
}
