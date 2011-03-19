package edu.spaced.net.message;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.listener.MoveListener;

public class MoveMessage extends NetMessage {
	public long timestamp;
	public int playerId;
	public Vector2 position;
	public float angle;
	public Vector2 velocity;
	// No angular velocity since we want arcade physics.

	@Override
	public void publish(Connection connection, List<Object> listeners) {
		// Publish to all listeners
		for (Object listener : listeners) {
			((MoveListener)listener).playerMoved(timestamp, playerId, position, angle, velocity);
		}
	}

}
