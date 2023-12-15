package com.mygdx.givemedrink;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.givemedrink.screens.AboutScreen;
import com.mygdx.givemedrink.screens.GameScreen;
import com.mygdx.givemedrink.screens.MenuScreen;
import com.mygdx.givemedrink.screens.SettingsScreen;
import com.mygdx.givemedrink.utils.FontHelper;
import com.mygdx.givemedrink.utils.GameSettings;
import com.mygdx.givemedrink.utils.MemoryHelper;
import com.mygdx.givemedrink.utils.SoundHelper;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public FitViewport viewport;
	public Vector3 touch;

	public static CharacterAnimations characterAnimations;

	public MenuScreen menuScreen;
	public AboutScreen aboutScreen;
	public SettingsScreen settingsScreen;
	public GameScreen gameScreen;

	public static FontHelper talkFont;
	public static FontHelper titleFont;

	@Override
	public void create () {
		batch = new SpriteBatch();
		touch = new Vector3();
		camera = new OrthographicCamera(2306, 1080);
		camera.setToOrtho(false);
		viewport = new FitViewport(2306, 1080, camera);

		characterAnimations = new CharacterAnimations();

		talkFont = new FontHelper(50,
				"fonts/press-start-k.ttf", Color.WHITE);
		titleFont = new FontHelper(100,
				"fonts/big_8.ttf", Color.WHITE);

		menuScreen = new MenuScreen(this);
		aboutScreen = new AboutScreen(this);
		settingsScreen = new SettingsScreen(this);
		gameScreen = new GameScreen(this);

		setScreen(menuScreen);

		SoundHelper.setMusicVolume(MemoryHelper.loadMusicVolume());

	}

	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
