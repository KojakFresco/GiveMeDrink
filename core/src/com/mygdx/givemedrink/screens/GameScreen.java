package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.views.BackgroundView;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.CharacterView;
import com.mygdx.givemedrink.views.NumberLabelView;
import com.mygdx.givemedrink.views.GlassView;
import com.mygdx.givemedrink.views.ImageView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    //TODO: timer

    MyGdxGame myGdxGame;

    public static double startAccelerometerY;
    public static double accelerometerY;

    boolean glassGot;

    public static long gameStart;
    public static long gameTimer;

    public static long spawnTimer;

    int counter;
    double combo;
    NumberLabelView counterLabel;
    NumberLabelView timerLabel;

    ArrayList<BaseView> viewArray;
    ArrayList<Drink> neededGlassesArray;
    ArrayList<CharacterView> charactersArray;

    GlassView glass;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();
        neededGlassesArray = new ArrayList<>();
        charactersArray = new ArrayList<>();

        BackgroundView background = new BackgroundView("icons/background.png");

        viewArray.add(background);
    }

    @Override
    public void show() {
        counter = 0;
        combo = 1;
        spawnCharacter();

        ImageView table = new ImageView(0, 0, (int) (888 * 2.25), (int) (168 * 2.25),
                "icons/table.jpeg");

        counterLabel = new NumberLabelView(
                0, Gdx.graphics.getHeight() - 60,
                MyGdxGame.talkFont.bitmapFont, "SCORE: ");
        timerLabel = new NumberLabelView(
                Gdx.graphics.getWidth() - 500, Gdx.graphics.getHeight() - 60,
                MyGdxGame.talkFont.bitmapFont, "");

        viewArray.add(table);
        viewArray.add(counterLabel);
        viewArray.add(timerLabel);

        counterLabel.alignCenter();

        startAccelerometerY = Gdx.input.getAccelerometerY();
        spawnTimer = TimeUtils.millis();

        gameStart = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

        gameTimer = GameSettings.TIMER - (TimeUtils.millis() - gameStart);
        accelerometerY = Gdx.input.getAccelerometerY() - startAccelerometerY;

        if (glass != null) {

            int normalSize = charactersArray.size();
            for (int i = 0; i < charactersArray.size(); ++i) {
                CharacterView character = charactersArray.get(i);

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

            if (glass.isStopped) {
                for (CharacterView character :charactersArray) {
                    if (character.getGlass(glass)) {
                        counter += 10 * combo;
                        combo += 0.5;
                        neededGlassesArray.remove(glass.drink);
                        viewArray.remove(glass);
                        glass.dispose();
                        character.sitPlace.isOccupied = false;
                        glassGot = true;
                        break;
                    }
                }

                System.out.println(glassGot);
                if (!glassGot) {
                    counter -= 30;
                    combo = 1;
                    viewArray.remove(glass);
                    glass.dispose();
                }

                counterLabel.setCounter(counter);

            }
        }


        if (viewArray.contains(glass) && glass != null) glass.move(accelerometerY);
        else if (neededGlassesArray.size() != 0) {
            glass = new GlassView(
                    0, GameSettings.TABLE_HEIGHT,
                    GameSettings.GLASS_WIDTH, GameSettings.GLASS_HEIGHT,
                    neededGlassesArray.get(MathUtils.random(0, neededGlassesArray.size() - 1))
            );
            glassGot = false;
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

        timerLabel.setTimer(gameTimer);

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
