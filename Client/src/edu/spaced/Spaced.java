package edu.spaced;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.spaced.net.GameClient;
import edu.spaced.screens.GameLoop;

/**
 * Spaced application central. This class manages the full application cycle,
 * handling switching between different screens.
 * 
 * @author Logan Lowell
 */
public class Spaced implements ApplicationListener {
	private Screen screen;
	
	@Override
	public void create() {
		GameClient.getInstance().connectTo("localhost");
		
		screen = new GameLoop();
	}

	@Override
	public void resume() {
		screen.resume();
	}

	@Override
	public void render() {
		// Network update
		GameClient.getInstance().update();
		// Game update
		screen.render(Gdx.graphics.getDeltaTime());
    }

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}

	@Override
	public void pause() {
		screen.pause();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}

}
