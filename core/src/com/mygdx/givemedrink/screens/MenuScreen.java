package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.givemedrink.views.NumberLabelView;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    ArrayList<BaseView> viewArray;

    NumberLabelView highScore;
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        BackgroundView background = new BackgroundView("icons/background.png");

        ArrayList<String> startButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            startButtonAnimation.add("tiles/buttons/start/start" + i + ".png");

        ArrayList<String> aboutButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            aboutButtonAnimation.add("tiles/buttons/about/about" + i + ".png");

        ArrayList<String> settingsButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            settingsButtonAnimation.add("tiles/buttons/settings/settings" + i + ".png");

        ArrayList<String> exitButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            exitButtonAnimation.add("tiles/buttons/exit/exit" + i + ".png");


        ImageView gameName = new ImageView(0, 540,
                GameSettings.SCREEN_WIDTH * 7 / 10, GameSettings.SCREEN_HEIGHT / 3,
                "icons/logo.png");

        ImageView table = new ImageView(0, 0,
                GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT * 5 / 8,
                "icons/table.jpeg");

        ButtonView playButton = new ButtonView(
                GameSettings.SCREEN_WIDTH / 15, GameSettings.SCREEN_HEIGHT / 5,
                GameSettings.SCREEN_WIDTH / 5, GameSettings.SCREEN_HEIGHT / 6,
                startButtonAnimation);
        ButtonView aboutButton = new ButtonView(
                GameSettings.SCREEN_WIDTH * 4 / 15, GameSettings.SCREEN_HEIGHT / 5,
                GameSettings.SCREEN_WIDTH / 5, GameSettings.SCREEN_HEIGHT / 6,
                aboutButtonAnimation);
        ButtonView settingsButton = new ButtonView(
                GameSettings.SCREEN_WIDTH * 7 / 15, GameSettings.SCREEN_HEIGHT / 5,
                GameSettings.SCREEN_WIDTH / 5, GameSettings.SCREEN_HEIGHT / 6,
                settingsButtonAnimation);
        ButtonView exitButton = new ButtonView(
                GameSettings.SCREEN_WIDTH * 10 / 15, GameSettings.SCREEN_HEIGHT / 5,
                GameSettings.SCREEN_WIDTH / 5, GameSettings.SCREEN_HEIGHT / 6,
                exitButtonAnimation);

        highScore = new NumberLabelView(130, 130, MyGdxGame.talkFont.bitmapFont,
                "HIGH SCORE: ");


        playButton.setOnClickListener(onPlayButtonClicked);
        aboutButton.setOnClickListener(onAboutButtonClicked);
        settingsButton.setOnClickListener(onSettingsButtonClicked);
        exitButton.setOnClickListener(onExitButtonClicked);

        gameName.alignCenter();

        viewArray.add(background);
        viewArray.add(table);
        viewArray.add(gameName);
        viewArray.add(playButton);
        viewArray.add(aboutButton);
        viewArray.add(settingsButton);
        viewArray.add(exitButton);
        viewArray.add(highScore);
    }

    @Override
    public void show() {
        SoundHelper.playMusic(0);
        SoundHelper.stopMusic(1);

        highScore.setCounter(MemoryHelper.loadHighScore());
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

    @Override
    public void resize(int width, int height) {
        myGdxGame.viewport.update(width, height, true);
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.viewport.unproject(myGdxGame.touch);

            System.out.println(myGdxGame.touch.x);
            for (BaseView view : viewArray)
                view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
        }
    }

    BaseView.OnClickListener onPlayButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.gameScreen);
            SoundHelper.playButtonSound();
        }
    };

    BaseView.OnClickListener onAboutButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.aboutScreen);
            SoundHelper.playButtonSound();
        }
    };

    BaseView.OnClickListener onSettingsButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.settingsScreen);
            SoundHelper.playButtonSound();
        }
    };

    BaseView.OnClickListener onExitButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            Gdx.app.exit();
            SoundHelper.playButtonSound();
        }
    };
}
