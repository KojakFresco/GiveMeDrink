package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.utils.GameSettings;

public class Blackout extends BaseView{
    Texture blackoutTexture;

    public Blackout() {
        super(0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fill();
        blackoutTexture = new Texture(pixmap);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(blackoutTexture, x, y, width, height);
    }
}
