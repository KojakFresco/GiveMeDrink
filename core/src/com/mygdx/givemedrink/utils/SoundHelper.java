package com.mygdx.givemedrink.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundHelper {
    //TODO: (optionally) add good sound

    static float soundVolume = MemoryHelper.loadSoundsVolume();

    Music[] musicList = {
            Gdx.audio.newMusic(Gdx.files.internal("music/NewMenuMusic.mp3")),
            Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"))
    };

    Sound[] soundList = {
            Gdx.audio.newSound(Gdx.files.internal("sounds/mistakeSound.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/buttonSound.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/glassSpawn.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/loseSound.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/winSound.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/pauseSound.mp3"))
    };
    public void playMusic(int index) {
        musicList[index].play();
        musicList[index].setLooping(true);
    }

    public void stopMusic(int index) {
        musicList[index].stop();
    }

    public void playMistakeSound() {
        soundList[0].play(soundVolume);
    }

    public void playButtonSound() {
        soundList[1].play(soundVolume * 0.5f);
    }

    public void playGlassSpawnSound() {
        soundList[2].play(soundVolume);
    }

    public void playLoseSound() {
        soundList[3].play(soundVolume);
    }

    public void playWinSound() {
        soundList[4].play(soundVolume);
    }

    public void playPauseSound() {
        soundList[5].play(soundVolume);
        musicList[1].pause();
    }

    public static void playSound(Sound sound) {
        sound.play(soundVolume);
    }

    public void setMusicVolume(float vol) {
        for (Music music : musicList) music.setVolume(vol);
    }

    public static void setSoundsVolume(float vol) {
        soundVolume = vol;
    }
}
