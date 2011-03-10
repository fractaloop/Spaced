package edu.spaced.net.message;

import java.util.List; 

import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.message.NetMessage;
import edu.spaced.net.listener.ChatListener;

public class ChatMessage extends NetMessage {
	public String text;
	public int from;
	
	@Override
	public void publish(Connection connection, List<Object> listeners) {
		// Publish to all listeners
		for (Object listener : listeners) {
			((ChatListener)listener).chatMessage(text, from);
		}
	}
}