package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.GameState;
import com.mygdx.givemedrink.utils.SoundHelper;
import com.mygdx.givemedrink.views.BackgroundView;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.views.Blackout;
import com.mygdx.givemedrink.views.ButtonView;
import com.mygdx.givemedrink.views.CharacterView;
import com.mygdx.givemedrink.views.LabelView;
import com.mygdx.givemedrink.views.NumberLabelView;
import com.mygdx.givemedrink.views.GlassView;
import com.mygdx.givemedrink.views.ImageView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    GameState gameState;

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

    ArrayList<BaseView> playViewArray;
    ArrayList<Drink> neededGlassesArray;
    ArrayList<CharacterView> charactersArray;

    ArrayList<BaseView> winViewArray;

    GlassView glass;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        playViewArray = new ArrayList<>();
        winViewArray = new ArrayList<>();

        neededGlassesArray = new ArrayList<>();
        charactersArray = new ArrayList<>();

        ArrayList<String> menuButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            menuButtonAnimation.add("tiles/buttons/menu/menu" + i + ".png");

        Blackout blackout = new Blackout();
        LabelView winLabel = new LabelView(0, 900, MyGdxGame.titleFont.bitmapFont,
                "You won this game, man!");
        ButtonView menuButton = new ButtonView(0, 200,
                320, 120, menuButtonAnimation);

        winLabel.alignCenter();
        menuButton.alignCenter();

        menuButton.setOnClickListener(onMenuButtonClicked);

        winViewArray.add(blackout);
        winViewArray.add(winLabel);
        winViewArray.add(menuButton);

    }

    @Override
    public void show() {
        counter = 0;
        combo = 1;

        BackgroundView background = new BackgroundView("icons/background.png");
        playViewArray.add(background);

        spawnCharacter();

        ImageView table = new ImageView(0, 0, (int) (888 * 2.25), (int) (168 * 2.25),
                "icons/table.jpeg");

        counterLabel = new NumberLabelView(
                0, Gdx.graphics.getHeight() - 60,
                MyGdxGame.talkFont.bitmapFont, "SCORE: ");
        timerLabel = new NumberLabelView(
                Gdx.graphics.getWidth() - 500, Gdx.graphics.getHeight() - 60,
                MyGdxGame.talkFont.bitmapFont, "");

        playViewArray.add(table);
        playViewArray.add(counterLabel);
        playViewArray.add(timerLabel);

        counterLabel.alignCenter();

        startAccelerometerY = Gdx.input.getAccelerometerY();
        spawnTimer = TimeUtils.millis();

        gameStart = TimeUtils.millis();
        gameState = GameState.IS_PLAYING;

        SoundHelper.playBackSound(0);
    }

    @Override
    public void render(float delta) {
        if (gameState == GameState.IS_PLAYING) {

            handleInput(playViewArray);

            gameTimer = GameSettings.TIMER - (TimeUtils.millis() - gameStart);
            accelerometerY = Gdx.input.getAccelerometerY() - startAccelerometerY;

            if (glass != null) {

                int normalSize = charactersArray.size();
                for (int i = 0; i < charactersArray.size(); ++i) {
                    CharacterView character = charactersArray.get(i);

                    if (character.isOut) {
                        playViewArray.remove(character);
                        charactersArray.remove(character);
                        character.dispose();
                    }

                    if (normalSize != charactersArray.size()) {
                        --i;
                        normalSize = charactersArray.size();
                    }
                }

                if (glass.isStopped) {
                    for (CharacterView character : charactersArray) {
                        if (character.getGlass(glass)) {
                            counter += 10 * combo;
                            combo += 0.5;
                            neededGlassesArray.remove(glass.drink);
                            playViewArray.remove(glass);
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
                        playViewArray.remove(glass);
                        glass.dispose();
                    }

                    counterLabel.setCounter(counter);

                }
            }


            if (playViewArray.contains(glass) && glass != null) glass.move(accelerometerY);
            else if (neededGlassesArray.size() != 0) {
                glass = new GlassView(
                        0, GameSettings.TABLE_HEIGHT,
                        GameSettings.GLASS_WIDTH, GameSettings.GLASS_HEIGHT,
                        neededGlassesArray.get(MathUtils.random(0, neededGlassesArray.size() - 1))
                );
                glassGot = false;
                playViewArray.add(glass);
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

            if (gameTimer <= 0) gameState = GameState.WON;

        }
        else if (gameState == GameState.WON) handleInput(winViewArray);

        ScreenUtils.clear(0.3f,0.2f,0.2f,1);

        myGdxGame.batch.begin();

        for(BaseView view : playViewArray) view.draw(myGdxGame.batch);

        if (gameState == GameState.WON)
            for (BaseView view : winViewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    public void handleInput(ArrayList<BaseView> viewArray) {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);

            System.out.println(myGdxGame.touch.x);
            for (BaseView view : viewArray)
                view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
        }
    }

    public void spawnCharacter() {
        spawnTimer = TimeUtils.millis();
        CharacterView character = new CharacterView(400, 700);
        charactersArray.add(character);
        playViewArray.add(1, character);
    }

    BaseView.OnClickListener onMenuButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.menuScreen);
            for (BaseView view : playViewArray) view.dispose();
            playViewArray.clear();
            neededGlassesArray.clear();
            charactersArray.clear();
        }
    };
}
