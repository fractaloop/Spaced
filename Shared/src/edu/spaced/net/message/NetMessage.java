package edu.spaced.net.message;

import java.util.List;
import com.esotericsoftware.kryonet.Connection;

public abstract class NetMessage {
	abstract public void publish(Connection connection, List<Object> listeners);
}