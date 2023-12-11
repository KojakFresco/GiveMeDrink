package com.mygdx.givemedrink.views;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class ButtonView extends BaseView {

    boolean isPressed;

    ArrayList<Texture> textureList;
    public ButtonView(int x, int y, int width, int height) {
        super(x, y, width, height);

        isPressed = false;
    }


    @Override
    public void draw(SpriteBatch batch) {
        if (!isPressed) batch.draw(textureList.get(0), x, y, width, height);
        else doAnimation();
    }

    @Override
    public boolean isHit(int tx, int ty) {
        return super.isHit(tx, ty);

    }

    public void doAnimation() {
    }
}
