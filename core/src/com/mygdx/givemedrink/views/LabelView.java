package com.mygdx.givemedrink.views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LabelView extends BaseView {
    BitmapFont font;
    String message;

    public LabelView(int x, int y, BitmapFont font, String message) {
        super(x, y);

        this.font = font;
        this.message = message;

        GlyphLayout glyphLayout = new GlyphLayout(font, message);
        width = (int) glyphLayout.width;
        height = (int) glyphLayout.height;

        this.x = x;
        setY(y);
    }

    public void setY(int y) {
        this.y = y;
        this.y += height;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        font.draw(spriteBatch, message, x, y);
    }

    @Override
    public void dispose() {
    }
}
