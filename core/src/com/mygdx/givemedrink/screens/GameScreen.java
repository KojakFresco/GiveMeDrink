package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.CharacterView;
import com.mygdx.givemedrink.views.GlassView;
import com.mygdx.givemedrink.views.ImageView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;


    public static double startAccelerometerY;
    public static double gyroscopeZ;
    public static double accelerometerY;


    ArrayList<BaseView> viewArray;
    ArrayList<GlassView> glassesArray;
    ArrayList<CharacterView> charactersArray;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();
        glassesArray = new ArrayList<>();
        charactersArray = new ArrayList<>();
    }

    @Override
    public void show() {
        CharacterView character = new CharacterView(400, 700);

        ImageView table = new ImageView(0, 0, Gdx.graphics.getWidth() - 300, 500,
                "icons/table.jpg");

        ImageView burger = new ImageView(100, 450, 265 / 2, 185 / 2,
                "icons/burger0.png");
        GlassView glass = new GlassView(0, 450, 98, 144);

        viewArray.add(character);
        viewArray.add(table);
        viewArray.add(glass);
        viewArray.add(burger);

        charactersArray.add(character);

        glassesArray.add(glass);

        startAccelerometerY = Gdx.input.getAccelerometerY();
    }

    @Override
    public void render(float delta) {

        gyroscopeZ = Gdx.input.getGyroscopeZ();
        accelerometerY = Gdx.input.getAccelerometerY() - startAccelerometerY;

        for (GlassView glass : glassesArray) glass.move(accelerometerY);
        for (CharacterView character : charactersArray) character.move();

        for (CharacterView character :charactersArray) {
            if (character.getGlass(glassesArray.get(0))) {

            }
        }

        ScreenUtils.clear(0.3f, 0.2f, 0.2f, 1);

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
}
