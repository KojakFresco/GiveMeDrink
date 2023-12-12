package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.CharacterView;
import com.mygdx.givemedrink.views.GlassView;
import com.mygdx.givemedrink.views.ImageView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    //TODO: counter
    //TODO: timer

    MyGdxGame myGdxGame;


    public static double startAccelerometerY;
    public static double accelerometerY;

    public static long spawnTimer;

    ArrayList<BaseView> viewArray;
    ArrayList<Drink> neededGlassesArray;
    ArrayList<CharacterView> charactersArray;

    ImageView table;
    GlassView glass;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();
        neededGlassesArray = new ArrayList<>();
        charactersArray = new ArrayList<>();

        ImageView background = new ImageView(0, 0, (int) (1020 * 2.25), 1080,
                "icons/background.jpeg");

        viewArray.add(background);
    }

    @Override
    public void show() {
        spawnCharacter();

        table = new ImageView(0, 0, (int) (888 * 2.25), (int) (168 * 2.25),
                "icons/table.jpeg");

        viewArray.add(table);

        startAccelerometerY = Gdx.input.getAccelerometerY();
        spawnTimer = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        //TODO: if glass stopped in a wrong place dispose it

        accelerometerY = Gdx.input.getAccelerometerY() - startAccelerometerY;

        if (glass != null) {
            int normalSize = charactersArray.size();
            for (int i = 0; i < charactersArray.size(); ++i) {
                CharacterView character = charactersArray.get(i);

                if (character.getGlass(glass)) {
                    neededGlassesArray.remove(glass.drink);
                    viewArray.remove(glass);
                    glass.dispose();
                    character.sitPlace.isOccupied = false;
                }

                if (character.isOut) {
                    viewArray.remove(character);
                    charactersArray.remove(character);
                    character.dispose();
                }

                if (normalSize != charactersArray.size()) {
                    --i;
                    normalSize = charactersArray.size();
                }
            }
        }

        if (viewArray.contains(glass) && glass != null) glass.move(accelerometerY);
        else if (neededGlassesArray.size() != 0) {
            glass = new GlassView(
                    0, GameSettings.TABLE_HEIGHT,
                    GameSettings.GLASS_WIDTH, GameSettings.GLASS_HEIGHT,
                    neededGlassesArray.get(MathUtils.random(0, neededGlassesArray.size() - 1))
            );
            viewArray.add(glass);
        }
        for (CharacterView character : charactersArray) {
            character.move();
            if (!character.orderAccepted && character.isSitting()) {
                neededGlassesArray.add(character.neededDrink);
                character.orderAccepted = true;
            }
        }

        if (charactersArray.size() < 3 && TimeUtils.millis() - spawnTimer >= 2000)
            spawnCharacter();

        ScreenUtils.clear(0.3f,0.2f,0.2f,1);

        myGdxGame.batch.begin();

        for(BaseView view :viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    public void spawnCharacter() {
        spawnTimer = TimeUtils.millis();
        CharacterView character = new CharacterView(400, 700);
        charactersArray.add(character);
        viewArray.add(1, character);
    }
}
