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
	private GameClient gameClient;
	private Screen screen;
	
	@Override
	public void create() {
		gameClient = new GameClient();
		gameClient.connect("localhost");
		
		screen = new GameLoop();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// Network update
		gameClient.update();
		// Game update
		screen.render(Gdx.graphics.getDeltaTime());
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

	}

}
