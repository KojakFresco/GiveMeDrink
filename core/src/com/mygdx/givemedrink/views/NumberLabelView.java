package com.mygdx.givemedrink.views;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class NumberLabelView extends LabelView {

    String message;
    public NumberLabelView(int x, int y, BitmapFont font, String message) {
        super(x, y, font, message + 0);
        this.message = message;
    }

    public void setCounter(int counter) {
        super.setMessage(message + counter);
    }

    public void setTimer(long timer) {
        timer = timer / 1000;
        int timerSecs = (int) timer % 60;
        int timerMins = (int) timer / 60;
        super.setMessage(message + timerMins + ":" + timerSecs);
    }
}
