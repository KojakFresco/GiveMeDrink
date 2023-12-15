package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.screens.GameScreen;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.GameSettings;

public class GlassView extends BaseView {

    public Drink drink;

    boolean isFalling;
    public boolean isStopped;
    Texture texture;

    double alpha;
    double aX;
    double aY;
    double previousVelocityX;
    double previousVelocityY;
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

    public void move(double accelerometerY, float delta) {
        delta *= 8;
        previousVelocityX = velocityX;
        previousVelocityY = velocityY;
        alpha = accelerometerY * Math.PI / 20;
        if (!isFalling && !isStopped) {
            if (alpha >= Math.PI / 20) {
                aX = 9.8 * (Math.sin(alpha) - GameScreen.frictionFactor * Math.cos(alpha));
                velocityX = previousVelocityX + aX * delta;
            }
            else if (alpha < 0 && velocityX > 0) {
                aX = 9.8 * (Math.sin(alpha) + GameScreen.frictionFactor * Math.cos(alpha));
                velocityX = previousVelocityX + aX * delta;
            }
            else if (velocityX < 0) {
                velocityX = 0;
                isStopped = true;
            }
        }
        if (((x >= GameSettings.SCREEN_WIDTH - 270) || isFalling) && y >= 0) {
            aY = 120;
            velocityY = previousVelocityY - aY * delta;
            isFalling = true;
        } else {
            aY = 0;
            velocityY = 0;
            isFalling = false;
        }

        x += previousVelocityX * delta + aX * Math.pow(delta, 2) / 2;
        y += previousVelocityY * delta + aY * Math.pow(delta, 2) / 2;
    }

    public boolean isOut() {
        return x >= GameSettings.SCREEN_WIDTH;
    }
}
