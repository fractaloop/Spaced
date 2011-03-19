package edu.spaced.net.message;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.listener.SpawnListener;

/**
 * Network message that indicates a player spawns. If sent from a client, this
 * is a request. if sent from a server, it is a notification that a spawn
 * occurred.
 * 
 * 
 * @author Logan Lowell
 *
 */
public class SpawnMessage extends NetMessage {
	public long timestamp;
	public int playerId;
	public Vector2 position;
	public float angle;
	
	@Override
	public void publish(Connection connection, List<Object> listeners) {
		// Publish to all listeners
		for (Object listener : listeners) {
			((SpawnListener)listener).playerSpawned(timestamp, playerId, position, angle);
		}
	}

}
