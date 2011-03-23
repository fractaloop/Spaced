package edu.spaced.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import edu.spaced.controllers.AccessController;
import edu.spaced.controllers.GameController;
import edu.spaced.simulation.Level;
import edu.spaced.simulation.Simulation;
import edu.spaced.simulation.entity.Entity;
import edu.spaced.simulation.entity.Player;

/**
 * Singleton class that manages the server. Central point of communication for
 * all network communication.
 * 
 * @author Logan Lowell
 *
 */
public class GameServer extends Network {
	private static final class GameServerHolder {
		public static GameServer INSTANCE = new GameServer();
	}
	
	public static GameServer getInstance() {
		return GameServerHolder.INSTANCE;
	}
	
	//////////////
	
	private Server server;
	
	private Simulation sim;
	
	private final long TICKS_PER_SECOND = 20; // 20 updates/second

	private ArrayList<Player> players;
		
	private GameServer() {
		server = new Server();
		register(server);
		
		server.addListener(this);
		
		players = new ArrayList<Player>();
		
		// Native shit
		GdxNativesLoader.load();
	}
	
	/**
	 * Start the server up!
	 */
	public void start() {
		try {
			server.bind(TCP_PORT, UDP_PORT);
		} catch (IOException e) {
			Log.error("Unable to start server!");
			Log.error(e.getMessage());
			System.exit(1);
		}
		
		// Begin a new simulation
		try {
			sim = new Simulation(Level.loadFromPath("data/test.spaced"), false);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.err.println("Failed to open default map: test.spaced");
			System.exit(1);
		}
		// Add server-side controllers. They self-register to the network.
		@SuppressWarnings("unused")
		AccessController access = new AccessController(sim);
		GameController game = new GameController(sim);
		// Start our server once we've loaded a level
		try {
			server.start();
		
			// Begin server loop
			long lastTime = System.nanoTime();
			boolean running = true;
			while(running) {
				// Ensure we run at 20 updates/sec
				long currentTime = System.nanoTime();
				running = sim.update((currentTime - lastTime) / 1000000f);
				long millisToNextUpdate = Math.max(0, (1000 / TICKS_PER_SECOND) - ((System.nanoTime() - currentTime) / 1000000));
				lastTime = currentTime;
				try {
					Thread.sleep(millisToNextUpdate);
				} catch (InterruptedException e) {
					break;
				}
			}
		} finally {
			stop();
		}
	}
	
	public void stop() {
		server.stop();
	}
	
	public void sendToTCP(int connectionID, Object object) {
		server.sendToTCP(connectionID, object);
	}
	
	public void sendToUDP(int connectionID, Object object) {
		server.sendToUDP(connectionID, object);
	}
	
	public void sendToAllTCP(Object object) {
		server.sendToAllTCP(object);
	}
	
	public void sendToAllUDP(Object object) {
		server.sendToAllUDP(object);
	}
	
	public void sendToAllExceptTCP(int connectionID, Object object) {
		server.sendToAllExceptTCP(connectionID, object);
	}
	
	public void sendToAllExceptUDP(int connectionID, Object object) {
		server.sendToAllExceptUDP(connectionID, object);
	}
	
	///////////////
	// Accessors


	public Player findPlayer(int playerId) {
		for (Player entity : players) {
			if (entity instanceof Player) {
				Player player = (Player)entity;
				if (player.getId() == playerId)
					return player;
			}
		}
		return null;
	}
	
	public synchronized Simulation getSim() {
		return sim;
	}

	public synchronized void setSim(Simulation sim) {
		this.sim = sim;
	}

	public boolean addPlayer(Player player) {
		return players.add(player);
	}

	public boolean removePlayer(Player player) {
		return players.remove(player);
	}

	public boolean removePlayer(int playerId) {
		Player player = findPlayer(playerId);
		if (player != null) {
			players.remove(player);
			return true;
		}
		return false;		
	}
}
