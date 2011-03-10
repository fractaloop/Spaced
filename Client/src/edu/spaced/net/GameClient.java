package edu.spaced.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

/**
 * Networked game client management.
 * 
 * @author Logan Lowell
 */
public class GameClient extends Network {
	private static final class GameClientHolder {
		public static GameClient INSTANCE = new GameClient();
	}
	
	public static GameClient getInstance() {
		return GameClientHolder.INSTANCE;
	}

	///////////////////

	private Client client;

	private GameClient() {
		client = new Client();
		
		register(client);
		
		client.addListener(this);
	}
	
	public void connectTo(final String hostname) {
		// Spawn a thread to begin a connection attempt
		new Thread("Connect") {
			public void run() {
				try {
					client.connect(5000, hostname, TCP_PORT, UDP_PORT);
				} catch (IOException ex) {
					// TODO Make this a clean exit!
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}.start();
	}
	
	public void update() {
		try {
			client.update(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int sendTCP(Object object) {
		return client.sendTCP(object);
	}
	
	public int sendUDP(Object object) {
		return client.sendUDP(object);
	}
}
