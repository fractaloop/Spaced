package edu.spaced.net.message;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.listener.DisconnectedListener;

public class DisconnectedMessage extends NetMessage {
	@Override
	public void publish(Connection connection, List<Object> listeners) {
		// Publish to all listeners
		for (Object listener : listeners) {
			((DisconnectedListener)listener).disconnected(connection);
		}
	}
}