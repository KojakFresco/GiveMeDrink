package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.GameSettings;

public class SplashScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    int number;
    int frameCounter;
    Texture texture;
    public SplashScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        number = 0;
        frameCounter = 0;
    }

    @Override
    public void render(float delta) {
        frameCounter += 1;
        if ((int) (frameCounter / 8 * delta / 0.016) > number)
            number = (int) (frameCounter / 8 * delta / 0.016);
        if (number >= 8)
            myGdxGame.setScreen(myGdxGame.menuScreen);
        else if (number < 6)
            texture = new Texture("tiles/splashScreen/splash" + number + ".png");

        ScreenUtils.clear(0, 0, 0, 1);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(texture, 480, 510, 1550, 570);

        myGdxGame.batch.end();
    }
}
