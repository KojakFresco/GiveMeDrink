package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundHelper {

    static float soundVolume = MemoryHelper.loadSoundsVolume();

    static Music[] musicList = {
            Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3")),
            Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"))
    };

    static Sound[] soundList = {
            Gdx.audio.newSound(Gdx.files.internal("sounds/mistakeSound.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/buttonSound.mp3"))
    };
    public static void playMusic(int index) {
        musicList[index].play();
        musicList[index].setLooping(true);
    }

    public static void stopMusic(int index) {
        musicList[index].stop();
    }

    public static void playMistakeSound() {
        soundList[0].play(soundVolume);
    }

    public static void playButtonSound() {
        soundList[1].play(soundVolume * 0.5f);
    }
    public static void playSound(Sound sound) {
        sound.play(soundVolume);
    }

    public static void setMusicVolume(float vol) {
        for (Music music : musicList) music.setVolume(vol);
    }

    public static void setSoundsVolume(float vol) {
        soundVolume = vol;
    }
}
