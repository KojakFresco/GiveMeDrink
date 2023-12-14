package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.utils.Drink;
import com.mygdx.givemedrink.utils.GameSettings;

public class GlassView extends BaseView {

    public Drink drink;

    boolean isFalling;
    public boolean isStopped;
    Texture texture;

    double alpha;
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
        alpha = accelerometerY * Math.PI / 20;
        if (!isFalling && !isStopped) {
            if (alpha >= Math.PI / 20)
                velocityX += 9.8 *
                        (Math.sin(alpha) - GameSettings.FRICTION_FACTOR * Math.cos(alpha)) / 60;
            else if (alpha < 0 && velocityX > 0) {
                velocityX += 9.8 *
                        (Math.sin(alpha) + GameSettings.FRICTION_FACTOR * Math.cos(alpha)) / 60;
            }
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
        //TODO: better falling physics

        x += velocityX;
        y += velocityY;
    }

    public boolean isOut() {
        return x >= Gdx.graphics.getWidth();
    }
}
