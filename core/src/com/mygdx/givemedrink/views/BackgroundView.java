package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundView extends BaseView {
    Texture texture;

    public BackgroundView(String pathToTexture) {
        super(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.texture = new Texture(pathToTexture);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
