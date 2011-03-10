package edu.spaced.controllers;

import edu.spaced.net.GameServer;
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
public class AccessController implements JoinListener, PartListener {
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
		// TODO check player name
		player.setId(playerID);
		player.setState(Player.State.OBSERVING);
		sim.addEntity(player);
		
		// Notify everyone of the join
		JoinMessage msg = new JoinMessage();
		msg.player = player;
		GameServer.getInstance().sendToAllTCP(msg);
	}
	
	@Override
	public void playerParted(Player player) {
		
	}
}
