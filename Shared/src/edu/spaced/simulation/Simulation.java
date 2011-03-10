package edu.spaced.simulation;

import java.util.ArrayList;

import com.esotericsoftware.minlog.Log;

import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.listener.PartListener;
import edu.spaced.simulation.entity.Entity;
import edu.spaced.simulation.entity.Player;

/**
 * Manages a running game simulation. This class only handles the logic of
 * it's interaction and does not handle visualization. A simulation has only
 * one active level and a set of active entities.
 * 
 * @see Level
 * 
 * @author Logan Lowell
 *
 */
public class Simulation implements JoinListener, PartListener {
	ArrayList<Entity> entities;
	Level level = null;
	
	public Simulation(Level level) {
		entities = new ArrayList<Entity>();
		this.level = level;
	}
	
	public boolean update(float delta) {
		return level != null;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	@Override
	public void playerJoined(int playerID, Player player) {
		Log.info("simulation", "Player joined: " + player.getName());
		entities.add(player);
	}
	
	@Override
	public void playerParted(Player player) {
		Log.info("simulation", "Player parted: " + player.getName());
		entities.remove(player);
	}	
}
