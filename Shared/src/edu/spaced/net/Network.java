package edu.spaced.net;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

import edu.spaced.net.message.NetMessage;
import edu.spaced.net.message.JoinMesssage;
import edu.spaced.net.message.PartMessage;
import edu.spaced.net.message.ChatMessage;

// This class is a convenient place to keep things common to both the client and server.
public class Network extends Listener {
	private static final class NetworkHolder {
		public static final Network INSTANCE = new Network();
	}
	
	public static Network getInstance() {
		return NetworkHolder.INSTANCE;
	}
	// Message listeners
	Map<NetMessage, List<Object>> listeners = Collections.synchronizedMap(new HashMap<NetMessage, List<Object>>());
	
	static public final int TCP_PORT = 44554;
	static public final int UDP_PORT = 44555;

	// This registers objects that are going to be sent over the network.
	protected void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(JoinMesssage.class);
		kryo.register(PartMessage.class);
		kryo.register(ChatMessage.class);
	}
	
	public void connected(Connection connection) {
	}

	/**
	 * Subscribe an object to receive event notifications if a specified type of message is received.
	 * @param msg the type of the message to trigger the event
	 * @param listener the class to receive the event
	 */
	public void subscribe(NetMessage msg, Object listener) {
		listeners.get(msg).add(listener);
	}

	/**
	 * When a network object is received, resolve it's original type and then let it publish itself.
	 * @param connection connection the message was received on
	 * @param object the message object received
	 */
	public void received(Connection connection, Object object) {
		if (object instanceof NetMessage) {
			NetMessage msg = (NetMessage)object;
			msg.publish(connection, listeners.get(msg));
		}
	}

	public void disconnected(Connection connection) {
	}
}