package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.BackgroundView;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.views.ImageView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    //TODO: make settings screen

    MyGdxGame myGdxGame;

    ArrayList<BaseView> viewArray;
    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        BackgroundView background = new BackgroundView("icons/background.png");

        viewArray.add(background);

    }

    @Override
    public void render(float delta) {
        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
}
