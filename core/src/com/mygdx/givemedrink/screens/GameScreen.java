package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.GameState;
import com.mygdx.givemedrink.utils.MemoryHelper;
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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;

    public GameState gameState;

    Random random;

    public static double startAccelerometerY;
    public static double accelerometerY;
    public static double frictionFactor;

    boolean glassGot;

    public static long gameStart;
    public static long gameTimer;
    public static long pauseTimer;

    public static long spawnTimer;

    int counter;
    double combo;
    NumberLabelView counterLabel;
    NumberLabelView timerLabel;

    public static boolean[] placesOccupation;
    public static int[] placesX;

    ArrayList<BaseView> playViewArray;
    ArrayList<Drink> neededGlassesArray;
    ArrayList<CharacterView> charactersArray;

    ArrayList<BaseView> pauseViewArray;
    ArrayList<BaseView> winViewArray;
    ArrayList<BaseView> loseViewArray;

    ButtonView pauseButton;
    NumberLabelView highScore;

    GlassView glass;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        random = new Random();

        placesOccupation = new boolean[]{false, false, false};
        placesX = new int[]{
                GameSettings.FIRST_SIT_X, GameSettings.SECOND_SIT_X, GameSettings.THIRD_SIT_X
        };

        playViewArray = new ArrayList<>();
        pauseViewArray = new ArrayList<>();
        winViewArray = new ArrayList<>();
        loseViewArray = new ArrayList<>();

        neededGlassesArray = new ArrayList<>();
        charactersArray = new ArrayList<>();

        ArrayList<String> pauseButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            pauseButtonAnimation.add("tiles/buttons/pauseButton/pauseButton" + i + ".png");

        ArrayList<String> retryButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            retryButtonAnimation.add("tiles/buttons/retry/retry" + i + ".png");

        ArrayList<String> menuButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            menuButtonAnimation.add("tiles/buttons/menu/menu" + i + ".png");

        pauseButton = new ButtonView(80, 900,
                150, 150, pauseButtonAnimation);

        Blackout blackout = new Blackout();
        LabelView winLabel = new LabelView(0, 900, MyGdxGame.titleFont.bitmapFont,
                "You won this game!");

        LabelView loseLabel = new LabelView(0, 900, MyGdxGame.titleFont.bitmapFont,
                "Sorry, but you lost");

        LabelView pauseLabel = new LabelView(0, 900, MyGdxGame.titleFont.bitmapFont,
                "PAUSED");

        highScore = new NumberLabelView(800, 650,
                MyGdxGame.talkFont.bitmapFont,
                "HIGH SCORE: ");

        ButtonView retryButton = new ButtonView(0, 300,
                480, 180, retryButtonAnimation);
        ButtonView menuButton = new ButtonView(0, 100,
                480, 180, menuButtonAnimation);

        winLabel.alignCenter();
        loseLabel.alignCenter();
        pauseLabel.alignCenter();
        retryButton.alignCenter();
        menuButton.alignCenter();

        menuButton.setOnClickListener(onMenuButtonClicked);
        retryButton.setOnClickListener(onRetryButtonClicked);
        pauseButton.setOnClickListener(onPauseButtonClicked);

        pauseViewArray.add(blackout);
        pauseViewArray.add(pauseLabel);
        pauseViewArray.add(pauseButton);
        pauseViewArray.add(retryButton);
        pauseViewArray.add(menuButton);

        winViewArray.add(blackout);
        winViewArray.add(winLabel);
        winViewArray.add(highScore);
        winViewArray.add(retryButton);
        winViewArray.add(menuButton);

        loseViewArray.add(blackout);
        loseViewArray.add(loseLabel);
        loseViewArray.add(highScore);
        loseViewArray.add(retryButton);
        loseViewArray.add(menuButton);

    }

    @Override
    public void show() {
        counter = 0;
        combo = 1;
        frictionFactor = 0.05;

        BackgroundView background = new BackgroundView("icons/background.png");

        ImageView table = new ImageView(0, 0, (int) (888 * 2.25), (int) (168 * 2.25),
                "icons/table.jpeg");

        counterLabel = new NumberLabelView(
                0, 1020,
                MyGdxGame.talkFont.bitmapFont, "SCORE: ");
        timerLabel = new NumberLabelView(
                GameSettings.SCREEN_WIDTH - 500, GameSettings.SCREEN_HEIGHT - 60,
                MyGdxGame.talkFont.bitmapFont, "");

        playViewArray.add(background);
        playViewArray.add(table);
        playViewArray.add(pauseButton);
        playViewArray.add(counterLabel);
        playViewArray.add(timerLabel);

        counterLabel.alignCenter();

        startAccelerometerY = Gdx.input.getAccelerometerY();
        spawnTimer = TimeUtils.millis();

        gameStart = TimeUtils.millis();
        gameState = GameState.IS_PLAYING;

        MyGdxGame.soundHelper.stopMusic(0);
        MyGdxGame.soundHelper.playMusic(1);

        spawnCharacter();
    }

    @Override
    public void render(float delta) {
        if (gameState == GameState.IS_PLAYING) {

            handleInput(playViewArray);

            gameTimer = GameSettings.TIMER - TimeUtils.millis() + gameStart;
            accelerometerY = Gdx.input.getAccelerometerY() - startAccelerometerY;
            frictionFactor -= 0.00004;

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

            glassLogic(delta);

            for (CharacterView character : charactersArray) {
                character.setFrameMultiplexer(Gdx.graphics.getFramesPerSecond());
                character.move(delta);
                if (!character.orderAccepted && character.isSitting()) {
                    neededGlassesArray.add(character.neededDrink);
                    character.orderAccepted = true;
                }
            }

            if (charactersArray.size() < 3 && TimeUtils.millis() - spawnTimer >= 3000)
                spawnCharacter();

            timerLabel.setTimer(gameTimer);

            if (gameTimer <= 0) {
                if (counter > MemoryHelper.loadHighScore()) MemoryHelper.saveHighScore(counter);
                highScore.setCounter(MemoryHelper.loadHighScore());

                if (counter >= GameSettings.WIN_SCORE) {
                    gameState = GameState.WON;
                    highScore.setCounter(MemoryHelper.loadHighScore());
                    MyGdxGame.soundHelper.playWinSound();
                    MyGdxGame.soundHelper.stopMusic(1);
                }
                else {
                    gameState = GameState.LOST;
                    MyGdxGame.soundHelper.playLoseSound();
                    MyGdxGame.soundHelper.stopMusic(1);
                }
            }

        }
        else if (gameState == GameState.ON_PAUSED) handleInput(pauseViewArray);
        else if (gameState == GameState.WON) handleInput(winViewArray);
        else if (gameState == GameState.LOST) handleInput(loseViewArray);

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        ScreenUtils.clear(0.3f,0.2f,0.2f,1);

        myGdxGame.batch.begin();

        for(BaseView view : playViewArray) view.draw(myGdxGame.batch);

        if (gameState == GameState.ON_PAUSED)
            for (BaseView view : pauseViewArray) view.draw(myGdxGame.batch);
        else if (gameState == GameState.WON)
            for (BaseView view : winViewArray) view.draw(myGdxGame.batch);
        else if (gameState == GameState.LOST)
            for (BaseView view : loseViewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        myGdxGame.viewport.update(width, height, true);
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

    public void glassLogic(float delta) {
        if (glass != null) {

            if (glass.isStopped) {
                for (CharacterView character : charactersArray) {
                    if (character.getGlass(glass)) {
                        counter += 10 * combo;
                        combo += 0.5;
                        neededGlassesArray.remove(glass.drink);
                        playViewArray.remove(glass);
                        glass.dispose();
                        glassGot = true;
                        break;
                    }
                }

                if (!glassGot) {
                    MyGdxGame.soundHelper.playMistakeSound();
                    counter -= 30;
                    combo = 1;
                    playViewArray.remove(glass);
                    glass.dispose();
                }

            }
            else if (glass.isOut()) {
                if (glass.drink != Drink.WRONGDRINK) {
                    MyGdxGame.soundHelper.playMistakeSound();
                    counter -= 30;
                    combo = 1;
                }
                playViewArray.remove(glass);
                glass.dispose();
            }

            counterLabel.setCounter(counter);

        }


        if (playViewArray.contains(glass) && glass != null) glass.move(accelerometerY, delta);
        else if (neededGlassesArray.size() != 0) {

            int chance = random.nextInt(100);
            Drink drink;

            if (chance % 7 == 0) drink = Drink.WRONGDRINK;
            else drink = neededGlassesArray.get(
                    MathUtils.random(0, neededGlassesArray.size() - 1));

            glass = new GlassView(
                    0, GameSettings.TABLE_HEIGHT,
                    GameSettings.GLASS_WIDTH, GameSettings.GLASS_HEIGHT, drink
            );
            glassGot = false;
            playViewArray.add(glass);
            MyGdxGame.soundHelper.playGlassSpawnSound();
        }
    }

    public void spawnCharacter() {
        spawnTimer = TimeUtils.millis();
        CharacterView character = new CharacterView();
        charactersArray.add(character);
        playViewArray.add(1, character);
    }

    public static AbstractMap.SimpleEntry<Integer, Integer> randomPlace() {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            if (!placesOccupation[i]) {
                System.out.println("occupat:" + placesOccupation[i]);
                indexes.add(i);
                System.out.println("index1:" + i);
            }
        }
        System.out.println("size:" + indexes.size());
        int index = MathUtils.random(indexes.size() - 1);
        System.out.println("index: " + indexes.get(index));
        GameScreen.placesOccupation[indexes.get(index)] = true;
        return new AbstractMap.SimpleEntry<>(indexes.get(index), placesX[indexes.get(index)]);
    }

    BaseView.OnClickListener onPauseButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            if (gameState == GameState.IS_PLAYING) {
                gameState = GameState.ON_PAUSED;
                pauseTimer = TimeUtils.millis();
                MyGdxGame.soundHelper.playPauseSound();
            }
            else if (gameState == GameState.ON_PAUSED) {
                pauseTimer = TimeUtils.millis() - pauseTimer;
                spawnTimer += pauseTimer;
                gameStart += pauseTimer;
                gameState = GameState.IS_PLAYING;
                MyGdxGame.soundHelper.playMusic(1);
            }
        }
    };
    BaseView.OnClickListener onMenuButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.menuScreen);
            for (BaseView view : playViewArray) view.dispose();
            playViewArray.clear();
            neededGlassesArray.clear();
            charactersArray.clear();
            MyGdxGame.soundHelper.playButtonSound();
        }
    };

    BaseView.OnClickListener onRetryButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            for (BaseView view : playViewArray) view.dispose();
            playViewArray.clear();
            neededGlassesArray.clear();
            charactersArray.clear();
            MyGdxGame.soundHelper.stopMusic(1);
            MyGdxGame.soundHelper.playButtonSound();
            show();
        }
    };
}
