package edu.spaced.controllers;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.minlog.Log;

import edu.spaced.net.GameServer;
import edu.spaced.net.listener.DeathListener;
import edu.spaced.net.listener.MoveListener;
import edu.spaced.net.listener.SpawnListener;
import edu.spaced.net.message.DeathMessage;
import edu.spaced.net.message.MoveMessage;
import edu.spaced.net.message.SpawnMessage;
import edu.spaced.simulation.Simulation;
import edu.spaced.simulation.entity.Player;

public class GameController implements SpawnListener, DeathListener, MoveListener {

	private Simulation sim;

	public GameController(Simulation sim) {
		GameServer.getInstance().subscribe(SpawnMessage.class, this);
		GameServer.getInstance().subscribe(DeathMessage.class, this);
		GameServer.getInstance().subscribe(MoveMessage.class, this);
		
		this.sim = sim;
	}
	
	@Override
	public void playerMoved(long timestamp, int playerId, Vector2 position, float angle, Vector2 velocity) {
		// Convert player's timestamp into server timestamp
		// Rebroadcast movement info
		// A production game would require cheat-checking here.
	}

	@Override
	public void playerDied(int playerId) {
		// 
		// TODO Auto-generated method stub
		
	}

	/**
	 * Process a spawn request
	 */
	@Override
	public void playerSpawned(long timestamp, int playerId, Vector2 position, float angle) {
		Player player = sim.findPlayer(playerId); 
		if (player != null) {
			switch(player.getState()) {
			case DEAD:
			case OBSERVING:
				player.setState(Player.State.ALIVE);
				// Spawn the player
				SpawnMessage spawnMsg = new SpawnMessage();
				spawnMsg.playerId = playerId;
				spawnMsg.position = new Vector2(0,0);
				spawnMsg.angle = (float)(Math.random() * Math.PI * 2);
				GameServer.getInstance().sendToAllUDP(spawnMsg);
			}
		} else {
//			Log.debug("GameController", "Spawn request for unknown player " + playerId);
		}
	}

}
