package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.givemedrink.CharacterAnimations;
import com.mygdx.givemedrink.MyGdxGame;
import com.mygdx.givemedrink.utils.CharacterState;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.SitPlace;
import com.mygdx.givemedrink.utils.SoundHelper;

import java.util.ArrayList;

public class CharacterView extends BaseView {

    CharacterState characterState;
    public SitPlace sitPlace;
    public Drink neededDrink;
    public boolean orderAccepted;
    public boolean isOut;

    ArrayList<ArrayList<String>> characterPathList;

    ArrayList<Texture> walkingLeftTextureList;
    ArrayList<Texture> askingTextureList;
    ArrayList<Texture> sittingTextureList;
    ArrayList<Texture> walkingRightTextureList;
    ArrayList<Sound> soundsList;

    LabelView text;

    long talkStart;

    int frameCounter;
    double frameMultiplexer;

    public CharacterView() {
        super(Gdx.graphics.getWidth(), GameSettings.FLOOR_HEIGHT, 400, 700);

        walkingLeftTextureList = new ArrayList<>();
        askingTextureList = new ArrayList<>();
        sittingTextureList = new ArrayList<>();
        walkingRightTextureList = new ArrayList<>();
        soundsList = new ArrayList<>();

        characterPathList = CharacterAnimations.randomCharacter();

        for (String path : characterPathList.get(0)) walkingLeftTextureList.add(new Texture(path));
        for (String path : characterPathList.get(1)) askingTextureList.add(new Texture(path));
        for (String path : characterPathList.get(2)) sittingTextureList.add(new Texture(path));
        for (String path : characterPathList.get(3)) walkingRightTextureList.add(new Texture(path));
        soundsList.addAll(CharacterAnimations.characterSoundsList.get(
                CharacterAnimations.getIndexOfCharacter(characterPathList)));

        characterState = CharacterState.IS_WALKING_LEFT;
        sitPlace = SitPlace.randomPlace();
        neededDrink = Drink.randomDrink();
        frameCounter = 0;
        frameMultiplexer = (double) GameSettings.CHARACTER_ANIMATION_FPS / 60;
        orderAccepted = false;
        isOut = false;
        sitPlace.isOccupied = true;

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
                frameCounter = 0;
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
                characterState = CharacterState.IS_ASKING;
                frameCounter = 0;
                talkStart = TimeUtils.millis();
                text = new LabelView(x, y + height + 50, MyGdxGame.talkFont.bitmapFont,
                        neededDrink.drinkName);
                text.alignCenter(x + width / 2);
                SoundHelper.playSound(soundsList.get(neededDrink.number));
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
            characterState = CharacterState.IS_WALKING_RIGHT;
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        for (Texture texture : walkingLeftTextureList) texture.dispose();
        for (Texture texture : sittingTextureList) texture.dispose();
        sitPlace.isOccupied = false;
    }
}
