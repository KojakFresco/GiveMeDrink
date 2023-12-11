package com.mygdx.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.MyGdxGame;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void render(float delta) {

        myGdxGame.batch.begin();

        myGdxGame.batch.end();
    }
}
