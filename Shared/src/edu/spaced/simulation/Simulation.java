package edu.spaced.simulation;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.minlog.Log;

import edu.spaced.net.listener.DeathListener;
import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.listener.MoveListener;
import edu.spaced.net.listener.PartListener;
import edu.spaced.net.listener.SpawnListener;
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
public class Simulation implements JoinListener, PartListener, SpawnListener, DeathListener, MoveListener {
	ArrayList<Entity> entities;
	Level level = null;
	
	public Simulation(Level level) {
		entities = new ArrayList<Entity>();
		this.level = level;
	}
	
	public boolean update(float delta) {
		return level != null;
	}
	
	public boolean addEntity(Entity entity) {
		return entities.add(entity);
	}
	
	public boolean removeEntity(Entity entity) {
		return entities.remove(entity);
	}	

	public Player findPlayer(int playerId) {
		for (Entity entity : entities) {
			if (entity instanceof Player) {
				Player player = (Player)entity;
				if (player.getId() == playerId)
					return player;
			}
		}
		return null;
	}

	@Override
	public void playerJoined(int playerID, Player player) {
		Log.info("simulation", "Player joined: " + player.getName());
		entities.add(player);
	}
	
	@Override
	public void playerParted(int playerId) {
		Player player = findPlayer(playerId);
		entities.remove(player);
		Log.info("simulation", "Player parted: " + player.getName());
	}

	@Override
	public void playerMoved(long timestamp, int playerId, Vector2 position, float angle, Vector2 velocity) {
		// Update the sim with replay from the timestamp
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerDied(int playerId) {
		// Remove the player body from the sim
		// Create a nice explosion effect
		// Play a good BOOM!
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerSpawned(long timestamp, int playerId, Vector2 position, float angle) {
		// Add the player to the sim.
		// Add some nice warp-in particle effects.
		// Play a "warp in" type sound
		// TODO Auto-generated method stub
		
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

}
