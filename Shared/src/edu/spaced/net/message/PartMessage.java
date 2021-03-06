package edu.spaced.net.message;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.listener.PartListener;

/**
 * Network message generated from a client parting a game/server.
 * 
 * @author Logan Lowell
 *
 */
public class PartMessage extends NetMessage {
	public int playerId;
	
	@Override
	public void publish(Connection connection, List<Object> listeners) {
		// Publish to all listeners
		for (Object listener : listeners) {
			((PartListener)listener).playerParted(playerId);
		}
	}

}
