package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.BackgroundView;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.views.SliderView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    SliderView slider;

    ArrayList<BaseView> viewArray;
    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        BackgroundView background = new BackgroundView("icons/background.png");

        slider = new SliderView(200, 600, 500, 100, myGdxGame.camera);

        slider.setOnSliderNewValueListener(onSlide);

        viewArray.add(background);
        viewArray.add(slider);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(slider);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
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

        if (Gdx.input.isTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (BaseView view :viewArray)
                view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
        }
    }

    SliderView.OnSliderNewValueListener onSlide = new SliderView.OnSliderNewValueListener() {
        @Override
        public void onNewValue(float newValue) {

        }
    };
}
