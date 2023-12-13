package com.mygdx.givemedrink.views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class CounterView extends LabelView {

    String message;
    public CounterView(int x, int y, BitmapFont font, String message) {
        super(x, y, font, message + 0);
        this.message = message;
    }

    public void setMessage(int counter) {
        String message = this.message + counter;
        super.setMessage(message);
    }
}
