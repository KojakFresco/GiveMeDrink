package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.utils.GameSettings;

public class BaseView extends InputAdapter {

    int x;
    int y;
    int width;
    int height;

    OnClickListener onClickListener;

    public BaseView(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public BaseView(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void draw(SpriteBatch batch) {}

    public boolean isHit(int tx, int ty) {
        if (tx > x && tx < x + width && ty > y && ty < y + height) {
            if (onClickListener != null) onClickListener.onClick();
            return true;
        }
        return false;
    }

    public void alignCenter() {
        x = GameSettings.SCREEN_WIDTH / 2 - width / 2;
    }

    public void dispose() {}

    public interface OnClickListener {
        void onClick();
    }
}
