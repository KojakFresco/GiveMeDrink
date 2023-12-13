package com.mygdx.givemedrink.views;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.givemedrink.utils.MemoryHelper;

public class SliderView extends BaseView {

    ImageView stickImage;
    ImageView pointerImage;

    OnSliderNewValueListener onSliderNewValueListener;

    OrthographicCamera camera;

    boolean isDragging;

    public SliderView(int x, int y, int width, int height,
                      OrthographicCamera camera) {
        super(x, y, width, height);
        this.camera = camera;

        stickImage = new ImageView(x, y + height / 3, width, height / 3,
                "icons/table.jpg");
        pointerImage = new ImageView(
                (int) (x + (width - height) * MemoryHelper.loadMusicVolume()), y,
                height, height, "tiles/walter0.png");
    }

    public void draw(SpriteBatch batch) {
        stickImage.draw(batch);
        pointerImage.draw(batch);
    }

    public void setOnSliderNewValueListener(OnSliderNewValueListener onSliderNewValueListener) {
        this.onSliderNewValueListener = onSliderNewValueListener;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 vector = new Vector3(screenX, screenY, 0);
        vector = camera.unproject(vector);

        if (this.isHit((int) vector.x, (int) vector.y)) {
            this.isDragging = true;
            this.pointerImage.x = (int) (vector.x - this.pointerImage.width);
        };
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 vector = new Vector3(screenX, screenY, 0);
        vector = camera.unproject(vector);
        if (this.isDragging) {
            int newX = (int) (vector.x - this.pointerImage.width / 2);
            newX = Math.min(this.x + this.stickImage.width
                    - this.pointerImage.width, newX);
            newX = Math.max(this.x, newX);

            this.pointerImage.x = newX;
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (this.isDragging) this.isDragging = false;
        this.onSliderNewValueListener.onNewValue(
                (float) (this.pointerImage.x - this.x)
                        / (this.width - this.pointerImage.width));

        return super.touchUp(screenX, screenY, pointer, button);
    }

    public interface OnSliderNewValueListener {
        void onNewValue(float newValue);
    }

    @Override
    public void dispose() {
        stickImage.dispose();
        pointerImage.dispose();
    }
}
