package com.mygdx.givemedrink;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.givemedrink.screens.GameScreen;
import com.mygdx.givemedrink.screens.MenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public FitViewport viewport;
	public Vector3 touch;

	public MenuScreen menuScreen;
	public GameScreen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		touch = new Vector3();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(menuScreen);

	}

	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
