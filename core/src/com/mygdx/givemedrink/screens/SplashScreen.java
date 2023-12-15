package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.views.BackgroundView;
import com.mygdx.givemedrink.views.ImageView;

public class SplashScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    int number;
    int frameCounter;
    Texture texture;
    BackgroundView background;
    ImageView table;
    public SplashScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        background = new BackgroundView("icons/background.png");

        table = new ImageView(0, 0,
                GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT * 5 / 8,
                "icons/table.jpeg");

        number = 0;
        frameCounter = 0;
    }

    @Override
    public void render(float delta) {
        frameCounter += 1;
        if ((int) (frameCounter / 9 * delta / 0.016) > number)
            number = (int) (frameCounter / 9 * delta / 0.016);
        if (number > 7)
            myGdxGame.setScreen(myGdxGame.menuScreen);
        else if (number < 6)
            texture = new Texture("tiles/splashScreen/splash" + number + ".png");

        ScreenUtils.clear(0, 0, 0, 1);

        myGdxGame.batch.begin();

        background.draw(myGdxGame.batch);
        table.draw(myGdxGame.batch);
        myGdxGame.batch.draw(texture, 520, 530,
                1430, 550);

        myGdxGame.batch.end();
    }
}
