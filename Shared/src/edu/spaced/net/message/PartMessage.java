package edu.spaced.net.message;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.listener.PartListener;
import edu.spaced.simulation.entity.Player;

/**
 * Network message generated from a client parting a game/server.
 * 
 * @author Logan Lowell
 *
 */
public class PartMessage implements NetMessage {
	Player player;
	
	@Override
	public void publish(Connection connection, List<Object> listeners) {
		// Publish to all listeners
		for (Object listener : listeners) {
			((PartListener)listener).playerParted(player);
		}
	}

}
