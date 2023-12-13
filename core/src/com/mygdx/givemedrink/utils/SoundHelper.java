package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundHelper {

    static Music[] musicList = {
            Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"))
    };


    public static void playBackSound(int index) {
        musicList[index].play();
        musicList[index].setLooping(true);
    }


    public static void setVolume(float vol) {
        for (Music music : musicList) music.setVolume(vol);
    }
}
