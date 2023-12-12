package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.utils.Drink;

public class GlassView extends BaseView {

    Drink drink;

    boolean isFalling;
    boolean isStopped;
    Texture texture;

    double velocityX;
    double velocityY;

    public GlassView(int x, int y, int width, int height, Drink drink) {
        super(x, y, width, height);
        this.drink = drink;

        texture = new Texture(drink.texturePath);

        isFalling = false;
        isStopped = false;
        velocityX = 0;
        velocityY = 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void move(double accelerometerY) {
        //TODO: add better glass physics
        // a = g*(sin(alpha) +- mu*cos(alpha))
        if (!isFalling && !isStopped) {
            if (accelerometerY >= 1)
                velocityX += accelerometerY / 15;
            else if (accelerometerY <= 0 && velocityX > 0) velocityX += accelerometerY / 15;
            else if (velocityX < 0) {
                velocityX = 0;
                isStopped = true;
            }
        }
        if (((x >= Gdx.graphics.getWidth() - 255) || isFalling) && y >= 0) {
            velocityY -= 2;
            isFalling = true;
        } else {
            velocityY = 0;
            isFalling = false;
        }

        x += velocityX;
        y += velocityY;
    }
}
