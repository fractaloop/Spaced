package edu.spaced.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

import edu.spaced.net.message.*;
import edu.spaced.simulation.entity.Player;

/**
 * Network overhead class that is shared for clients and servers. This class
 * handles incoming packets and manages subscription.
 * 
 * @author Logan Lowell
 */
public class Network extends Listener {
	// Message listeners
	Map<Class<? extends NetMessage>, List<Object>> listeners = Collections
			.synchronizedMap(new HashMap<Class<? extends NetMessage>, List<Object>>());

	static public final int TCP_PORT = 44554;
	static public final int UDP_PORT = 44555;
	
	/**
	 * Registers all classes that will be serialized over the network with an
	 * endpoint (client or server).
	 * 
	 * @param endPoint the client or server to register with
	 */
	protected void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		// Don't forget to register all subclasses!
		kryo.register(JoinMessage.class);
			kryo.register(Player.class);
				kryo.register(Vector2.class);
				kryo.register(Player.State.class);
		kryo.register(PartMessage.class);
		kryo.register(ChatMessage.class);
		kryo.register(ChangeLevelMessage.class);
		kryo.register(SpawnMessage.class);
		kryo.register(MoveMessage.class);
		kryo.register(DeathMessage.class);
	}

	/**
	 * Subscribe an object to receive event notifications if a specified type of
	 * message is received.
	 * 
	 * @param msgClass the class of the message to trigger the event
	 * @param listener the instance to receive the event
	 */
	public void subscribe(Class<? extends NetMessage> msgClass, Object listener) {
		// Check if anyone's registered for this msg already
		if (listeners.get(msgClass) != null) {
			listeners.get(msgClass).add(listener);	
		} else {
			ArrayList<Object> listenerList = new ArrayList<Object>();
			listenerList.add(listener);
			listeners.put(msgClass, listenerList);
		}
	}

	public void connected(Connection connection) {
		ConnectedMessage msg = new ConnectedMessage();
		List<Object> listenerList = listeners.get(ConnectedMessage.class);
		if (listenerList != null)
			msg.publish(connection, listenerList);
	}

	public void disconnected(Connection connection) {
		DisconnectedMessage msg = new DisconnectedMessage();
		List<Object> listenerList = listeners.get(DisconnectedMessage.class);
		if (listenerList != null)
			msg.publish(connection, listenerList);
	}
	
	/**
	 * When a network object is received, resolve it's original type and then
	 * let it publish itself.
	 * 
	 * @param connection connection the message was received on
	 * @param object the message object received
	 */
	public void received(Connection connection, Object object) {
		if (object instanceof NetMessage) {
			NetMessage msg = (NetMessage) object;
			List<Object> listenerList = listeners.get(msg.getClass());
			if (listenerList != null)
				msg.publish(connection, listenerList);
		}
	}

}