package edu.spaced.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

import edu.spaced.simulation.Level;
import edu.spaced.simulation.Simulation;

/**
 * The main game loop occurs in in this screen. It renders a running
 * simulation and manages the media associated with it.
 * 
 * @author Logan Lowell
 *
 */
public class GameLoop implements Screen {
	Simulation sim;
	
	public GameLoop() {
		sim = new Simulation(Level.load("simple.tmx"));
	}
	@Override
	public void render(float delta) {
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
