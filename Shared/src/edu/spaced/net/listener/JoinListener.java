package edu.spaced.net.listener;

import edu.spaced.simulation.entity.Player;

public interface JoinListener {
	public void playerJoined(int playerID, Player player);
}
//static public class UpdateNames extends NetMessage implements PlayerListener {
//public String[] names;
//
//public void dispatch(List listeners) {
//	List<PlayerListeners> players = (List<PlayerListener>)listeners;
//	Player player = new Player(); //find the player somehow
//	//set the value on the player, do whatever
//	listeners.playerChanged(player);
//}
//}