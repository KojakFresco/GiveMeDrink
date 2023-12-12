package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.givemedrink.utils.GameSettings;
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

    public static long spawnTimer;

    ArrayList<BaseView> viewArray;
    ArrayList<GlassView> glassesArray;
    ArrayList<CharacterView> charactersArray;

    ImageView table;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();
        glassesArray = new ArrayList<>();
        charactersArray = new ArrayList<>();
    }

    @Override
    public void show() {
        spawnCharacter();

        table = new ImageView(0, 0, Gdx.graphics.getWidth() - 300, 500,
                "icons/table.jpg");

        viewArray.add(table);

        startAccelerometerY = Gdx.input.getAccelerometerY();
        spawnTimer = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

        gyroscopeZ = Gdx.input.getGyroscopeZ();
        accelerometerY = Gdx.input.getAccelerometerY() - startAccelerometerY;

        for (GlassView glass : glassesArray) glass.move(accelerometerY);
        for (CharacterView character : charactersArray) {
            character.move();
            if (!character.glassSpawned && character.isSitting()) {
                character.glassSpawned = true;
                GlassView glass = new GlassView(
                        0, GameSettings.TABLE_HEIGHT,
                        GameSettings.GLASS_WIDTH, GameSettings.GLASS_HEIGHT,
                        character.neededDrink
                );
                viewArray.add(glass);
                glassesArray.add(glass);
            }
        }

        if (charactersArray.size() < 3 && TimeUtils.millis() - spawnTimer >= 5000)
            spawnCharacter();


        int normalSize = charactersArray.size();
        for (int i = 0; i < charactersArray.size(); ++i) {
            CharacterView character = charactersArray.get(i);
            for (GlassView glass : glassesArray) {
                if (character.getGlass(glass)) {
                    System.out.println("thanks");
                    glassesArray.remove(glass);
                    viewArray.remove(glass);
                    glass.dispose();
                }
            }
            if (character.isOut) {
                viewArray.remove(character);
                charactersArray.remove(character);
                character.dispose();
            }
            if (normalSize != charactersArray.size()) --i;
        }

        ScreenUtils.clear(0.3f, 0.2f, 0.2f, 1);

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    public void spawnCharacter() {
        spawnTimer = TimeUtils.millis();
        CharacterView character = new CharacterView(400, 700);
        charactersArray.add(character);
        viewArray.add(0, character);
    }
}
