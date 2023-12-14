package com.mygdx.givemedrink.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.SoundHelper;

import java.util.ArrayList;

public class ButtonView extends BaseView {

    boolean isPressed;
    int frameCounter;
    double frameMultiplexer;

    ArrayList<Texture> textureList;
    public ButtonView(int x, int y, int width, int height, ArrayList<String> pathList) {
        super(x, y, width, height);

        textureList = new ArrayList<>();

        for (String path : pathList) textureList.add(new Texture(path));

        isPressed = false;
        frameCounter = 0;
        frameMultiplexer = (double) GameSettings.BUTTON_ANIMATION_FPS
                / 60;
    }


    @Override
    public void draw(SpriteBatch batch) {
        if (!isPressed) batch.draw(textureList.get(0), x, y, width, height);
        else {
            batch.draw(textureList.get((int) (frameCounter * frameMultiplexer)), x, y, width, height);
            frameCounter = frameCounter + 1;
            if ((int) (frameCounter * frameMultiplexer) >= textureList.size()) {
                onClickListener.onClick();
                SoundHelper.playButtonSound();
                isPressed = false;
                frameCounter = 0;
            }
        }
    }

    public void alignCenter() {
        x = (Gdx.graphics.getWidth() - width) / 2;
    }

    @Override
    public boolean isHit(int tx, int ty) {
        if (tx > x && tx < x + width && ty > y && ty < y + height && !isPressed) {
            isPressed = true;
        }
        return false;
    }
}
