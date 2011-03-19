package edu.spaced.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMapRenderer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;

import edu.spaced.net.GameClient;
import edu.spaced.net.listener.ConnectedListener;
import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.message.ConnectedMessage;
import edu.spaced.net.message.JoinMessage;
import edu.spaced.net.message.SpawnMessage;
import edu.spaced.simulation.Level;
import edu.spaced.simulation.Simulation;
import edu.spaced.simulation.entity.Player;

/**
 * The main game loop occurs in in this screen. It renders a running simulation
 * and manages the media associated with it.
 * 
 * @author Logan Lowell
 * 
 */
public class GameLoop implements Screen, ConnectedListener, JoinListener {
	
	Simulation sim;

	float foo = 0;
	TiledMap level;
	TileAtlas atlas;
	TiledMapRenderer renderer;
	

	public GameLoop() {
		GameClient.getInstance().subscribe(ConnectedMessage.class, this);
		GameClient.getInstance().subscribe(JoinMessage.class, this);
		
		// TODO This isn't going to be tiled, but I need to check some code in!
		sim = new Simulation(Level.loadFile("simple.tmx"));
		level = TiledLoader.createMap(Gdx.app.getFiles().getFileHandle("test-map.tmx", Files.FileType.Internal));
		FileHandle packFile = Gdx.app.getFiles().getFileHandle("test-map packfile", Files.FileType.Internal);
		FileHandle imagesDir = Gdx.app.getFiles().getFileHandle(".", Files.FileType.Internal);
		atlas = new TileAtlas(level, packFile, imagesDir);
		renderer = new TiledMapRenderer(level, atlas, 16, 16);
	}

	@Override
	public void render(float delta) {
		foo += delta;
		Gdx.graphics.getGL11().glClearColor(0,
				(float) (Math.sin(foo) * Math.sin(foo)), 0, 1);
		Gdx.graphics.getGL10().glClear(
				GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		renderer.render();
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
		Log.info("Game", "Joined game");
		
		// Initially, we will auto-spawn the player after joining the server.
		// We'd really like to allow them to observe the ongoing game before
		// joining the action, but we'll go with the simple case for now.
		SpawnMessage spawnMsg = new SpawnMessage();
		spawnMsg.playerId = playerId;
		GameClient.getInstance().sendTCP(spawnMsg);
		
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
