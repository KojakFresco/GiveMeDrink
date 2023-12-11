package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.CharacterState;
import com.mygdx.givemedrink.utils.GameSettings;

import java.util.ArrayList;

public class CharacterView extends BaseView {

    CharacterState characterState;

    ArrayList<Texture> walkingLeftTextureList;
    ArrayList<Texture> sittingTextureList;
    ArrayList<Texture> walkingRightTextureList;



    int frameCounter;
    double frameMultiplexer;

    public CharacterView(int width, int height) {
        super(Gdx.graphics.getWidth(), GameSettings.FLOOR_HEIGHT, width, height);

        walkingLeftTextureList = new ArrayList<>();
        sittingTextureList = new ArrayList<>();
        walkingRightTextureList = new ArrayList<>();

        ArrayList<ArrayList<String>> characterPathList =
                MyGdxGame.characterAnimations.charactersPathsList.get(
                        MathUtils.random(0, MyGdxGame.characterAnimations.charactersPathsList.size() - 1));
        for (String path : characterPathList.get(0)) walkingLeftTextureList.add(new Texture(path));
        for (String path : characterPathList.get(1)) sittingTextureList.add(new Texture(path));
        for (String path : characterPathList.get(2)) walkingRightTextureList.add(new Texture(path));

        characterState = CharacterState.IS_WALKING_LEFT;
        frameCounter = 0;
        frameMultiplexer = (double) GameSettings.CHARACTER_ANIMATION_FPS / 60;

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (characterState == CharacterState.IS_WALKING_LEFT) {
            batch.draw(walkingLeftTextureList.get((int) (frameCounter * frameMultiplexer)),
                    x, y, width, height);
            frameCounter = (int) ((frameCounter + 1) %
                    (walkingLeftTextureList.size() / frameMultiplexer));
        }
        else if (characterState == CharacterState.IS_SITTING) {
            batch.draw(sittingTextureList.get((int) (frameCounter * frameMultiplexer)),
                    x, y, width, height);
            frameCounter = (int) ((frameCounter + 1) %
                    (sittingTextureList.size() / frameMultiplexer));
        }
        else if (characterState == CharacterState.IS_WALKING_RIGHT) {
            batch.draw(walkingRightTextureList.get((int) (frameCounter * frameMultiplexer)),
                    x, y, width, height);
            frameCounter = (int) ((frameCounter + 1) %
                    (walkingRightTextureList.size() / frameMultiplexer));
        }
    }

    public void move() {
        if (characterState == CharacterState.IS_WALKING_LEFT) {
            x -= GameSettings.CHARACTER_SPEED;
            if (x <= 400) characterState = CharacterState.IS_SITTING;
        }
    }

    @Override
    public void dispose() {
        for (Texture texture : walkingLeftTextureList) texture.dispose();
        for (Texture texture : sittingTextureList) texture.dispose();
    }
}
