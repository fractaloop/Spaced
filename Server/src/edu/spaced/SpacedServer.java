package edu.spaced;

import com.esotericsoftware.minlog.Log;

import edu.spaced.net.GameServer;

/**
 * Program entry point.
 * 
 * @author Logan Lowell
 *
 */
public class SpacedServer {
	public static void main(String[] args) {
		Log.set(Log.LEVEL_DEBUG);
		// Start the network server
		GameServer.getInstance().start();
	}
}
