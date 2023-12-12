package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.givemedrink.utils.Glass;

public class GlassView extends BaseView {

    Glass glass;

    boolean isFalling;
    boolean isStopped;
    Texture texture;

    double velocityX;
    double velocityY;
    public GlassView(int x, int y, int width, int height) {
        super(x, y, width, height);
        String pathToImage = "icons/drink" + MathUtils.random(0, 2) + ".png";
        texture = new Texture(pathToImage);

        glass = Glass.randomGlass();

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
