package edu.spaced.net.listener;

import edu.spaced.simulation.entity.Player;

public interface JoinListener {
	public void playerJoined(int playerId, Player player);
}
