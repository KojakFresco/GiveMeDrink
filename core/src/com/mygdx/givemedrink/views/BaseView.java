package com.mygdx.givemedrink.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseView {

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

    public void dispose() {}

    public interface OnClickListener {
        void onClick();
    }
}
