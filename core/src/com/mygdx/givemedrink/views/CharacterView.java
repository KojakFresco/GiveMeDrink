package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.givemedrink.CharacterAnimations;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.CharacterState;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.SitPlace;

import java.util.ArrayList;

public class CharacterView extends BaseView {

    CharacterState characterState;
    SitPlace sitPlace;
    public Drink neededDrink;
    public boolean glassSpawned;
    public boolean isOut;

    ArrayList<Texture> walkingLeftTextureList;
    ArrayList<Texture> askingTextureList;
    ArrayList<Texture> sittingTextureList;
    ArrayList<Texture> walkingRightTextureList;

    LabelView text;

    long talkStart;

    int frameCounter;
    double frameMultiplexer;

    public CharacterView(int width, int height) {
        super(Gdx.graphics.getWidth(), GameSettings.FLOOR_HEIGHT, width, height);

        walkingLeftTextureList = new ArrayList<>();
        askingTextureList = new ArrayList<>();
        sittingTextureList = new ArrayList<>();
        walkingRightTextureList = new ArrayList<>();

        ArrayList<ArrayList<String>> characterPathList = CharacterAnimations.randomCharacter();

        for (String path : characterPathList.get(0)) walkingLeftTextureList.add(new Texture(path));
        for (String path : characterPathList.get(1)) askingTextureList.add(new Texture(path));
        for (String path : characterPathList.get(2)) sittingTextureList.add(new Texture(path));
        for (String path : characterPathList.get(3)) walkingRightTextureList.add(new Texture(path));

        characterState = CharacterState.IS_WALKING_LEFT;
        sitPlace = SitPlace.randomPlace();
        neededDrink = Drink.randomDrink();
        frameCounter = 0;
        frameMultiplexer = (double) GameSettings.CHARACTER_ANIMATION_FPS / 60;
        glassSpawned = false;
        isOut = false;

    }

    @Override
    public void draw(SpriteBatch batch) {
        if (characterState == CharacterState.IS_WALKING_LEFT) {
            batch.draw(walkingLeftTextureList.get((int) (frameCounter * frameMultiplexer)),
                    x, y, width, height);
            frameCounter = (int) ((frameCounter + 1) %
                    (walkingLeftTextureList.size() / frameMultiplexer));
        }
        else if (characterState == CharacterState.IS_ASKING) {
            text.draw(batch);
            batch.draw(askingTextureList.get((int) (frameCounter * frameMultiplexer)),
                    x, y, width, height);
            frameCounter = (int) ((frameCounter + 1) %
                    (askingTextureList.size() / frameMultiplexer));
            if (TimeUtils.millis() - talkStart >= 2000) {
                text.dispose();
                characterState = CharacterState.IS_SITTING;
            }
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
            if (x <= sitPlace.placeX) {
                SitPlace.changeOccupation(sitPlace, true);
                characterState = CharacterState.IS_ASKING;
                talkStart = TimeUtils.millis();
                text = new LabelView(x, y + height + 50, MyGdxGame.talkFont.bitmapFont,
                        "give me " + neededDrink.drinkName);
            }

        }
        else if (characterState == CharacterState.IS_WALKING_RIGHT) {
            x += GameSettings.CHARACTER_SPEED;
            if (x >= Gdx.graphics.getWidth()) isOut = true;
        }
    }

    public boolean isSitting() {
        return characterState == CharacterState.IS_SITTING
                || characterState == CharacterState.IS_ASKING;
    }
    public boolean getGlass(GlassView glass) {
        if (glass.x >= x - 30 && glass.x + glass.width <= x + width + 30
                && glass.isStopped && characterState == CharacterState.IS_SITTING
                && neededDrink == glass.drink) {
            SitPlace.changeOccupation(sitPlace, false);
            characterState = CharacterState.IS_WALKING_RIGHT;
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        for (Texture texture : walkingLeftTextureList) texture.dispose();
        for (Texture texture : sittingTextureList) texture.dispose();
    }
}
