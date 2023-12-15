package com.mygdx.givemedrink.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.SoundHelper;
import com.mygdx.givemedrink.views.BackgroundView;
import com.mygdx.givemedrink.views.BaseView;
import com.mygdx.givemedrink.views.ButtonView;
import com.mygdx.givemedrink.views.ImageView;
import com.mygdx.givemedrink.views.LabelView;

import java.util.ArrayList;

public class AboutScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;

    ArrayList<BaseView> viewArray;
    public AboutScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        viewArray = new ArrayList<>();

        ArrayList<String> backButtonAnimation = new ArrayList<>();
        for (int i = 0; i < GameSettings.BUTTON_FRAMES_COUNT; ++i)
            backButtonAnimation.add("tiles/buttons/backButton/backButton" + i + ".png");

        BackgroundView background = new BackgroundView("icons/background.png");
        ImageView table = new ImageView(0, 0,
                GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT - 400,
                "icons/table.jpeg");

        ButtonView backButton = new ButtonView(
                GameSettings.SCREEN_WIDTH / 25, GameSettings.SCREEN_HEIGHT * 19 / 40,
                GameSettings.SCREEN_WIDTH / 15, GameSettings.SCREEN_HEIGHT / 7,
                backButtonAnimation);
        LabelView educationTitle = new LabelView(0, GameSettings.SCREEN_HEIGHT / 2,
                MyGdxGame.titleFont.bitmapFont,
                "Education");
        LabelView aboutText = new LabelView(
                GameSettings.SCREEN_HEIGHT * 30 / 1080, GameSettings.SCREEN_WIDTH * 50 / 2306,
                MyGdxGame.talkFont.bitmapFont,
                "You are a bartender.\nServe drinks that were ordered by rotating\n" +
                        "your phone to the right and stop drinks\nfrom moving by rotating" +
                        "your phone to\nthe left. Throw bad drinks off the table,\nbut" +
                        "don't do that with the right ones.\nYou can make 5 mistakes" +
                        " before you lose.");

        backButton.setOnClickListener(onBackButtonClicked);

        educationTitle.alignCenter();

        viewArray.add(background);
        viewArray.add(table);
        viewArray.add(backButton);
        viewArray.add(educationTitle);
        viewArray.add(aboutText);
    }

    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);

        ScreenUtils.clear(0, 0, 0, 1);

        myGdxGame.batch.begin();

        for (BaseView view : viewArray) view.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        myGdxGame.viewport.update(width, height, true);
    }

    public void handleInput() {

        if (Gdx.input.isTouched()) {
            myGdxGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.touch = myGdxGame.camera.unproject(myGdxGame.touch);
            for (BaseView view :viewArray)
                view.isHit((int) myGdxGame.touch.x, (int) myGdxGame.touch.y);
        }
    }

    BaseView.OnClickListener onBackButtonClicked = new BaseView.OnClickListener() {
        @Override
        public void onClick() {
            myGdxGame.setScreen(myGdxGame.menuScreen);
            SoundHelper.playButtonSound();
        }
    };
}
