package edu.spaced.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import edu.spaced.simulation.Level;
import edu.spaced.simulation.Simulation;

public class GameServer extends Network {
	
	private Server server;
	
	private final long TICKS_PER_SECOND = 20; // 20 updates/second
		
	public GameServer() {
		server = new Server();
		register(server);
		
		server.addListener(this);
	}
	
	public void start() {
		try {
			server.bind(TCP_PORT, UDP_PORT);
		} catch (IOException e) {
			Log.error("Unable to start server!");
			Log.error(e.getMessage());
			System.exit(1);
		}
		
		// Begin a single simulation
		Simulation sim = new Simulation(Level.load("simple.tmx"));

		// Start our server once we've loaded a level
		try {
			server.start();
		
			// Begin server loop
			long lastTime = System.nanoTime();
			boolean running = true;
			while(running) {
				// Ensure we run at 
				long currentTime = System.nanoTime();
				running = sim.update((currentTime - lastTime) / 1000000f);
				long millisToNextUpdate = Math.max(0, (1000 / TICKS_PER_SECOND) - ((System.nanoTime() - currentTime) / 1000000));
				lastTime = currentTime;
				try {
					Thread.sleep(millisToNextUpdate);
				} catch (InterruptedException e) {
					break;
				} // About 20x a second assuming a 5ms update
			}
		} finally {
			stop();
		}
	}
	
	public void stop() {
		server.stop();
	}
	
	
}
