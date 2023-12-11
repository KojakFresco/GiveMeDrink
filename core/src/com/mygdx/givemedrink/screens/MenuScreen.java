package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
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

        viewArray = new ArrayList<>();

        ArrayList<String> playButtonAnimation = new ArrayList<>();
        for (int i = 0; i < 5; ++i) playButtonAnimation.add("tiles/buttons/play" + i + ".png");

        ButtonView playButton = new ButtonView(0, 500,
                200, 80, playButtonAnimation);


        playButton.setOnClickListener(onPlayButtonClicked);

        playButton.alignCenter();

        viewArray.add(playButton);
    }
    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY());
            myGdxGame.viewport.unproject(myGdxGame.touch);

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
