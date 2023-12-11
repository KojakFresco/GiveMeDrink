package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.views.ButtonView;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    ArrayList<BaseView> viewArray;
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        ButtonView playButton = new ButtonView()
        viewArray = new ArrayList<>();
    }
    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    public void handleInput() {

    }
}
