package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.MemoryHelper;
import com.mygdx.givemedrink.utils.SoundHelper;
import com.mygdx.givemedrink.views.BackgroundView;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.views.ButtonView;
import com.mygdx.givemedrink.views.ImageView;
import com.mygdx.givemedrink.views.LabelView;
import com.mygdx.givemedrink.views.SliderView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    SliderView musicSlider;
    SliderView soundsSlider;

    ArrayList<BaseView> viewArray;
    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        ArrayList<String> backButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            backButtonAnimation.add("tiles/buttons/backButton/backButton" + i + ".png");

        BackgroundView background = new BackgroundView("icons/background.png");
        ImageView table = new ImageView(0, 0,
                GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT * 5 / 8,
                "icons/table.jpeg");

        ButtonView backButton = new ButtonView(
                GameSettings.SCREEN_WIDTH / 25, GameSettings.SCREEN_HEIGHT * 19 / 40,
                GameSettings.SCREEN_WIDTH / 15, GameSettings.SCREEN_HEIGHT / 7,
                backButtonAnimation);

        LabelView settingsTitle = new LabelView(0, GameSettings.SCREEN_HEIGHT / 2,
                MyGdxGame.titleFont.bitmapFont,
                "Settings");

        LabelView musicText = new LabelView(
                GameSettings.SCREEN_WIDTH * 11 / 40, GameSettings.SCREEN_HEIGHT * 7 / 24,
                MyGdxGame.talkFont.bitmapFont,
                "Music");
        musicSlider = new SliderView(
                GameSettings.SCREEN_WIDTH * 2 / 5, GameSettings.SCREEN_HEIGHT * 23 / 98,
                GameSettings.SCREEN_WIDTH / 3, 150,
                MemoryHelper.loadMusicVolume(), myGdxGame.camera);

        LabelView soundsText = new LabelView(
                GameSettings.SCREEN_WIDTH * 25 / 97, GameSettings.SCREEN_HEIGHT * 5 / 48,
                MyGdxGame.talkFont.bitmapFont,
                "Sounds");
        soundsSlider = new SliderView(
                GameSettings.SCREEN_WIDTH * 2 / 5, GameSettings.SCREEN_HEIGHT * 5 / 98,
                GameSettings.SCREEN_WIDTH / 3, 150,
                MemoryHelper.loadSoundsVolume(), myGdxGame.camera);

        backButton.setOnClickListener(onBackButtonClicked);
        musicSlider.setOnSliderNewValueListener(onMusicSlide);
        soundsSlider.setOnSliderNewValueListener(onSoundsSlide);

        settingsTitle.alignCenter();

        viewArray.add(background);
        viewArray.add(table);
        viewArray.add(backButton);
        viewArray.add(settingsTitle);
        viewArray.add(musicText);
        viewArray.add(musicSlider);
        viewArray.add(soundsText);
        viewArray.add(soundsSlider);

    }

    @Override
    public void show() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(musicSlider);
        multiplexer.addProcessor(soundsSlider);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        ScreenUtils.clear(0, 0, 0, 1);

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    public void handleInput() {

        if (Gdx.input.isTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (BaseView view :viewArray)
                view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
        }
    }

    BaseView.OnClickListener onBackButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.menuScreen);
            SoundHelper.playButtonSound();
        }
    };

    SliderView.OnSliderNewValueListener onMusicSlide = new SliderView.OnSliderNewValueListener() {
        @Override
        public void onNewValue(float newValue) {
            SoundHelper.setMusicVolume(newValue);
            MemoryHelper.saveMusicVolume(newValue);
        }
    };

    SliderView.OnSliderNewValueListener onSoundsSlide = new SliderView.OnSliderNewValueListener() {
        @Override
        public void onNewValue(float newValue) {
            SoundHelper.setSoundsVolume(newValue);
            MemoryHelper.saveSoundsVolume(newValue);
        }
    };
}
