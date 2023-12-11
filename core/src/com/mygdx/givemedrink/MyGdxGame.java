package com.mygdx.givemedrink;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.givemedrink.screens.GameScreen;
import com.mygdx.givemedrink.screens.MenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;

	MenuScreen menuScreen;
	GameScreen gameScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();

		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(gameScreen);

	}

	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
