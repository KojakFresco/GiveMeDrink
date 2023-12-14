package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Preferences;

public class MemoryHelper {

    private final static Preferences prefs = Gdx.app.getPreferences("User saves");

    public static void saveMusicVolume(float volume) {
        prefs.putFloat("MusicVolume", volume).flush();
    }

    public static void saveSoundsVolume(float volume) {
        prefs.putFloat("SoundsVolume", volume).flush();
    }

    public static float loadMusicVolume() {
        if (prefs.contains("MusicVolume")) {
            return prefs.getFloat("MusicVolume");
        }
        saveMusicVolume(1);
        return 1;
    }

    public static float loadSoundsVolume() {
        if (prefs.contains("SoundsVolume")) {
            return prefs.getFloat("SoundsVolume");
        }
        saveMusicVolume(0.5f);
        return 0.5f;
    }
}
