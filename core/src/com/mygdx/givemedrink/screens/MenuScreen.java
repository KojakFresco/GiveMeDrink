package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.views.ButtonView;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    ArrayList<BaseView> viewArray;
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        ArrayList<String> playButtonAnimation = new ArrayList<>();
        for (int i = 0; i < 5; ++i)
            playButtonAnimation.add("tiles/buttons/play/play0" + (i + 1) + ".png");
        ArrayList<String> exitButtonAnimation = new ArrayList<>();

        ButtonView playButton = new ButtonView(0, 500,
                300, 120, playButtonAnimation);

        //TODO: add aboutButton

        playButton.setOnClickListener(onPlayButtonClicked);

        playButton.alignCenter();

        viewArray.add(playButton);
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
}
