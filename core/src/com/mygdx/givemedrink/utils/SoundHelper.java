package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundHelper {

    static Music[] musicList = {
            Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3")),
            Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"))
    };

    static Sound mistakeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/mistakeSound.mp3"));
    public static void playMusic(int index) {
        stopMusic();
        musicList[index].play();
        musicList[index].setLooping(true);
    }

    public static void stopMusic() {
        for (Music music : musicList) music.stop();
    }

    public static void playMistakeSound() {
        mistakeSound.play(0.3f);
    }
    public static void playSound(Sound sound) {
        sound.play();
    }

    public static void setVolume(float vol) {
        for (Music music : musicList) music.setVolume(vol);
    }
}
