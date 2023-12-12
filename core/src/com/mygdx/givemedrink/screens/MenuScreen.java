package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.views.ButtonView;
import com.mygdx.givemedrink.views.ImageView;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    ArrayList<BaseView> viewArray;
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        ArrayList<String> startButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            startButtonAnimation.add("tiles/buttons/start/start" + i + ".png");
        ArrayList<String> aboutButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            aboutButtonAnimation.add("tiles/buttons/about/about" + i + ".png");
        ArrayList<String> exitButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            exitButtonAnimation.add("tiles/buttons/exit/exit" + i + ".png");


        ImageView gameName = new ImageView(0, 650, 1000, 350,
                "icons/table.jpg");
        ButtonView playButton = new ButtonView(0, 450,
                320, 120, startButtonAnimation);
        ButtonView aboutButton = new ButtonView(0, 300,
                320, 120, aboutButtonAnimation);
        ButtonView exitButton = new ButtonView(0, 150,
                320, 120, exitButtonAnimation);

        playButton.setOnClickListener(onPlayButtonClicked);
        aboutButton.setOnClickListener(onAboutButtonClicked);
        exitButton.setOnClickListener(onExitButtonClicked);

        gameName.alignCenter();
        playButton.alignCenter();
        aboutButton.alignCenter();
        exitButton.alignCenter();

        viewArray.add(gameName);
        viewArray.add(playButton);
        viewArray.add(aboutButton);
        viewArray.add(exitButton);
    }
    @Override
    public void render(float delta) {

        handleInput();

        ScreenUtils.clear(0, 0, 0, 1);

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);

            System.out.println(myGdxGame.touch.x);
            for (BaseView view : viewArray)
                view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
        }
    }

    BaseView.OnClickListener onPlayButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }
    };

    BaseView.OnClickListener onAboutButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.aboutScreen);
        }
    };

    BaseView.OnClickListener onExitButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            Gdx.app.exit();
        }
    };
}
