package edu.spaced.net.listener;

import com.esotericsoftware.kryonet.Connection;

public interface ConnectedListener {
	public void connected(Connection connection);
}
