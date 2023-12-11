package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Glass extends BaseView {

    boolean isFalling;
    Texture texture;
    public Glass(int x, int y, int width, int height) {
        super(x, y, width, height);
        String pathToImage = "icons/drink" + MathUtils.random(0, 2) + ".png";
        texture = new Texture(pathToImage);
        isFalling = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void move(double accelerometerY) {
        if (Math.abs(accelerometerY) >= 1 && !isFalling) x += accelerometerY * 3;
        if (((x >= Gdx.graphics.getWidth() - 255) || isFalling) && y >= 0) {
            y -= 20;
            isFalling = true;
        } else isFalling = false;
    }
}
