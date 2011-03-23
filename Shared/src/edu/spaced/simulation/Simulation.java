package edu.spaced.simulation;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.minlog.Log;

import edu.spaced.net.listener.DeathListener;
import edu.spaced.net.listener.JoinListener;
import edu.spaced.net.listener.MoveListener;
import edu.spaced.net.listener.PartListener;
import edu.spaced.net.listener.SpawnListener;
import edu.spaced.simulation.elements.LevelElement;
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
public class Simulation implements JoinListener, PartListener, SpawnListener, DeathListener, MoveListener, ContactListener, ContactFilter {
	World world;
	
	ArrayList<Entity> entities;
	Level level = null;
	
	public Simulation(Level level) {
		entities = new ArrayList<Entity>();
		this.level = level;
		
		// Create a Box2D simulation
		world = new World(new Vector2(0, 0), false);
		world.setContactListener(this);
		world.setContactFilter(this);
		
		for (LevelElement element : level.getElements()) {
			
		}
	}
	
	public boolean update(float delta) {
		return level != null;
	}
	
	///////////////////////
	// Collision handlers
	
	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	/////////////////////
	// Network messages
	
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

	//////////////
	// Accessors
	
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
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}
