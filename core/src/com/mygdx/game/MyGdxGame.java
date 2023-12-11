package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	public SpriteBatch batch;
	Texture img;
	Texture barTable;

	public static double startAccelerometerY;
	public static double gyroscopeZ;
	public static double accelerometerY;

	int x;
	int y;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("icons/drink1.png");
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() - 300, 550, Pixmap.Format.RGB888);
		pixmap.setColor(Color.BROWN);
		pixmap.fill();
		pixmap.setColor(Color.DARK_GRAY);
		pixmap.fillRectangle(0, 0, Gdx.graphics.getWidth() - 300, 100);
		barTable = new Texture(pixmap);

		startAccelerometerY = Gdx.input.getAccelerometerY();
		x = 0;
		y = 500;
	}

	@Override
	public void render () {
		gyroscopeZ = Gdx.input.getGyroscopeZ();
		accelerometerY = Gdx.input.getAccelerometerY() - startAccelerometerY;
		if (Math.abs(accelerometerY) >= 1) x += accelerometerY * 3;
		if (x >= Gdx.graphics.getWidth() - 255 && y >= 0) y -= 10;

		ScreenUtils.clear(0.3f, 0.2f, 0.2f, 1);

		batch.begin();

		batch.draw(barTable, 0, 0);
		batch.draw(img, x, y, 98, 144);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
