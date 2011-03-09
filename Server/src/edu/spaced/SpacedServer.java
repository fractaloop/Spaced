package edu.spaced;

import edu.spaced.net.GameServer;

public class SpacedServer {
	public static void main(String[] args) {
		// Start the network server
		GameServer server = new GameServer();
		server.start();
	}
}
