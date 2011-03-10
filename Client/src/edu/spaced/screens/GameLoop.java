package edu.spaced.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;

import edu.spaced.net.GameClient;
import edu.spaced.net.listener.ConnectedListener;
import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.message.ConnectedMessage;
import edu.spaced.net.message.JoinMessage;
import edu.spaced.simulation.Level;
import edu.spaced.simulation.Simulation;
import edu.spaced.simulation.entity.Player;

/**
 * The main game loop occurs in in this screen. It renders a running
 * simulation and manages the media associated with it.
 * 
 * @author Logan Lowell
 *
 */
public class GameLoop implements Screen, ConnectedListener, JoinListener {
	private enum State {
		JOINING,
		OBSERVING,
		PLAYING,
		DEAD
	}
	State state = State.JOINING;
	Simulation sim;

	float foo = 0;

	public GameLoop() {
		GameClient.getInstance().subscribe(ConnectedMessage.class, this);
		GameClient.getInstance().subscribe(JoinMessage.class, this);
		
		sim = new Simulation(Level.load("simple.tmx"));
	}
	
	@Override
	public void render(float delta) {
		foo += delta;
		Gdx.graphics.getGL11().glClearColor(0, (float)(Math.sin(foo)*Math.sin(foo)), 0, 1);
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

	@Override
	public void playerJoined(int playerID, Player player) {
		Log.info("Game", "Joined game");
	}

	@Override
	public void connected(Connection connection) {
		// Request joining the server
		JoinMessage joinMsg = new JoinMessage();
		joinMsg.player = new Player();
		joinMsg.player.setName("Unnamed client.");
		GameClient.getInstance().sendTCP(joinMsg);	
		Log.debug("GameLoop", "Attempting to join...");
	}

}
