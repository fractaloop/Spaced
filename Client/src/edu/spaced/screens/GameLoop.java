package edu.spaced.screens;

import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMapRenderer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;

import edu.spaced.gui.MessageBox;
import edu.spaced.net.GameClient;
import edu.spaced.net.listener.ChangeLevelListener;
import edu.spaced.net.listener.ConnectedListener;
import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.message.ChangeLevelMessage;
import edu.spaced.net.message.ConnectedMessage;
import edu.spaced.net.message.JoinMessage;
import edu.spaced.net.message.SpawnMessage;
import edu.spaced.simulation.Level;
import edu.spaced.simulation.Renderer;
import edu.spaced.simulation.Simulation;
import edu.spaced.simulation.entity.Player;

/**
 * The main game loop occurs in in this screen. It renders a running simulation
 * and manages the media associated with it.
 * 
 * @author Logan Lowell
 * 
 */
public class GameLoop implements Screen, ConnectedListener, JoinListener, ChangeLevelListener {
	
	Simulation sim;

	BitmapFont bigFont;
	BitmapFont smallFont;
	
	MessageBox messageBox;
	int messageBoxLines = 5;

	private Renderer renderer;

	public GameLoop() {
		GameClient.getInstance().subscribe(ConnectedMessage.class, this);
		GameClient.getInstance().subscribe(JoinMessage.class, this);
		GameClient.getInstance().subscribe(ChangeLevelMessage.class, this);
		
		// Load obvious resources
		bigFont = new BitmapFont(Gdx.files.internal("data/spaced-big.fnt"), Gdx.files.internal("data/spaced-big.png"), false);
		smallFont = new BitmapFont(Gdx.files.internal("data/spaced-small.fnt"), Gdx.files.internal("data/spaced-small.png"), false);
		
		messageBox = new MessageBox(smallFont, 0, smallFont.getLineHeight() * messageBoxLines, Gdx.graphics.getWidth(), messageBoxLines);
		messageBox.addString("Connecting...");
	}
	
	@Override
	public void render(float delta) {
		Gdx.graphics.getGL11().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		Gdx.graphics.getGL10().glColor4f(1, 1, 1, 1);
//		renderer.render(sim);

		messageBox.draw();	
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
	public void playerJoined(int playerId, Player player) {
		// Initially, we will auto-spawn the player after joining the server.
		// We'd really like to allow them to observe the ongoing game before
		// joining the action, but we'll go with the simple case for now.
		SpawnMessage spawnMsg = new SpawnMessage();
		spawnMsg.playerId = playerId;
		GameClient.getInstance().sendTCP(spawnMsg);
		
		messageBox.addString("Joined game.");		
	}

	@Override
	public void connected(Connection connection) {
		// Request joining the server
		JoinMessage joinMsg = new JoinMessage();
		joinMsg.player = new Player();
		joinMsg.player.setName("Unnamed client.");
		GameClient.getInstance().sendTCP(joinMsg);
		messageBox.addString("Connected! Joining game...");
	}

	@Override
	public void levelChanged(String filename) {
		try {
			messageBox.addString("Playing on map " + filename.substring(0, filename.length() - ".spaced".length()));
			sim = new Simulation(Level.loadFromPath("data/" + filename));
		} catch (FileNotFoundException e) {
			System.err.println("Map " + filename + " was not found! Exiting...");
			// TODO Handle missing file appropriately! Best would be to
			// download the file from the server.
			System.exit(1);
		}
	}

}
