package edu.spaced.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.minlog.Log;

import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.listener.PartListener;
import edu.spaced.simulation.entity.Entity;
import edu.spaced.simulation.entity.Player;

public class Simulation implements JoinListener, PartListener {
	Map<Long, Player> players = Collections.synchronizedMap(new HashMap<Long, Player>());
	ArrayList<Entity> entities;
	Level level = null;
	
	public Simulation(Level level) {
		 this.level = level;
	}
	
	public boolean update(float delta) {
		return level != null;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	@Override
	public void playerParted(Player player) {
		Log.info("[GAME] Player parted: " + player.getName());
		players.put(player.getId(), player);
	}

	@Override
	public void playerJoined(Player player) {
		Log.info("[GAME] Player joined: " + player.getName());
		players.remove(player.getId());
	}
	
	
}
