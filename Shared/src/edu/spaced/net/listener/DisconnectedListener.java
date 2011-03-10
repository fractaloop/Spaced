package edu.spaced.net.listener;

import com.esotericsoftware.kryonet.Connection;

public interface DisconnectedListener {
	public void disconnected(Connection connection);
}
