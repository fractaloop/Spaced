package edu.spaced.net;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;

/**
 * Networked game client management.
 * 
 * @author Logan Lowell
 */
public class GameClient extends Network {
	private Client client;
	
	public GameClient() {
		client = new Client();
		register(client);
		
		client.addListener(this);
	}
	
	public void connect(final String hostname) {
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
}
