package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundHelper {
    //TODO: add character phrases

    static Music[] musicList = {
            Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"))
    };

    public static void playMusic(int index) {
        musicList[index].play();
        musicList[index].setLooping(true);
    }

    public static void playSound(Sound sound) {
        sound.play();
    }

    public static void setVolume(float vol) {
        for (Music music : musicList) music.setVolume(vol);
    }
}
