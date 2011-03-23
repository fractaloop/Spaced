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
public class Simulation implements ContactListener, ContactFilter {
	World world;
	
	ArrayList<Entity> entities;
	Level level = null;
	
	public Simulation(Level level, boolean initGraphics) {
		entities = new ArrayList<Entity>();
		this.level = level;
		
		// Create a Box2D simulation
		world = new World(new Vector2(0, 0), false);
		world.setContactListener(this);
		world.setContactFilter(this);
		
		level.initializePhysics(world);
		if (initGraphics) {
			level.initializeGraphics();
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

	//////////////
	// Accessors
	
	public boolean addEntity(Entity entity) {
		return entities.add(entity);
	}
	
	public boolean removeEntity(Entity entity) {
		return entities.remove(entity);
	}	
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}
