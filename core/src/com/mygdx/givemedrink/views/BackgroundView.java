package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.utils.GameSettings;

public class BackgroundView extends BaseView {
    Texture texture;

    public BackgroundView(String pathToTexture) {
        super(0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

        this.texture = new Texture(pathToTexture);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
