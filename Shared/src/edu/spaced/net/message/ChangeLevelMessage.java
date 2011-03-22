package edu.spaced.net.message;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.listener.ChangeLevelListener;

public class ChangeLevelMessage extends NetMessage {
	public String filename;
	
	@Override
	public void publish(Connection connection, List<Object> listeners) {
		// Publish to all listeners
		for (Object listener : listeners) {
			((ChangeLevelListener)listener).levelChanged(filename);
		}
	}
}
