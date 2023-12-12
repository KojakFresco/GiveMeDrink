package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.CharacterView;
import com.mygdx.givemedrink.views.Glass;
import com.mygdx.givemedrink.views.ImageView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    Texture barTable;

    public static double startAccelerometerY;
    public static double gyroscopeZ;
    public static double accelerometerY;


    ArrayList<BaseView> viewArray;
    ArrayList<Glass> glassesArray;
    ArrayList<CharacterView> charactersArray;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();
        glassesArray = new ArrayList<>();
        charactersArray = new ArrayList<>();

        //TODO: add show()

        CharacterView character = new CharacterView(400, 700);

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() - 300, 500,
                Pixmap.Format.RGB888);
        pixmap.setColor(Color.BROWN);
        pixmap.fill();
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fillRectangle(0, 0, Gdx.graphics.getWidth() - 300, 100);
        barTable = new Texture(pixmap);

        ImageView burger = new ImageView(100, 450, 265 / 2, 185 / 2,
                "icons/burger0.png");
        Glass glass = new Glass(0, 450, 98, 144);

        viewArray.add(character);
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

        for (Glass glass : glassesArray) glass.move(accelerometerY);
        for (CharacterView character : charactersArray) character.move();

        ScreenUtils.clear(0.3f, 0.2f, 0.2f, 1);

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(barTable, 0, 0);
        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }
}
