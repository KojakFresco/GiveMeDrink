package com.mygdx.givemedrink.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageView extends BaseView {

    Texture texture;

    public ImageView(int x, int y, int width, int height, String pathToImage) {
        super(x, y, width, height);
        texture = new Texture(pathToImage);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
}
