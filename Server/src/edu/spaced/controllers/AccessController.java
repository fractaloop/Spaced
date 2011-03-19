package edu.spaced.controllers;

import com.esotericsoftware.kryonet.Connection;

import edu.spaced.net.GameServer;
import edu.spaced.net.listener.DisconnectedListener;
import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.listener.PartListener;
import edu.spaced.net.message.JoinMessage;
import edu.spaced.net.message.PartMessage;
import edu.spaced.simulation.Simulation;
import edu.spaced.simulation.entity.Player;

/**
 * Manages player access to the server
 * 
 * @author Logan Lowell
 *
 */
public class AccessController implements JoinListener, PartListener, DisconnectedListener {
	private Simulation sim;
	
	public AccessController(Simulation sim) {
		GameServer.getInstance().subscribe(JoinMessage.class, this);
		GameServer.getInstance().subscribe(PartMessage.class, this);
		
		this.sim = sim;
	}

	/**
	 * Received when a player makes a request to join the server. Assign the
	 * player an ID and set them observing. We then let everyone else know
	 * they've joined.
	 */
	@Override
	public void playerJoined(int playerID, Player player) {
		// Note: The sim should add it automatically.
		
		// TODO check player name
		player.setId(playerID);
		player.setState(Player.State.OBSERVING);

		// Notify everyone of the join
		JoinMessage msg = new JoinMessage();
		msg.player = player;
		GameServer.getInstance().sendToAllTCP(msg);
	}
	
	@Override
	public void playerParted(int playerId) {
		// Note: The sim should remove it automatically.
		
		// Reliably notify everyone of the part
		PartMessage msg = new PartMessage();
		msg.playerId = playerId;
		GameServer.getInstance().sendToAllTCP(msg);
	}

	@Override
	public void disconnected(Connection connection) {
		// Remove the player from the Sim. We keep it here so the shared code
		// doesn't clutter.
		Player player = sim.findPlayer(connection.getID());
		if (player != null) {
			sim.removeEntity(player);
		}
		// Part this player for everyone. The clients will kill any living
		// ship.
		PartMessage partMsg = new PartMessage();
		partMsg.playerId = connection.getID();
		GameServer.getInstance().sendToAllTCP(partMsg);
	}
}
