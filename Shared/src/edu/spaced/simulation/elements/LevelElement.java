package edu.spaced.simulation.elements;

import java.util.Collection;
import java.util.Map;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.annotations.*;
import com.thoughtworks.xstream.converters.Converter;

import edu.spaced.simulation.Level;
import edu.spaced.simulation.Renderer;

/**
 * Abstract superclass of all elements in a level. These are not guaranteed to
 * be static, but it is guaranteed that none are sent over the network. As far
 * as the network is concerned, all LevelElements are static objects (even if
 * they don't always appear to the player as such).
 * 
 * Heavily based off the libgdx-vectorpinball FieldElement.
 *  
 * @author Logan Lowell
 */
public abstract class LevelElement {
	Map parameters;
	String elementID;
	
	@XStreamOmitField
	World box2dWorld;
	
	//////////////
	// Accessors
	/**
	 * Returns this element's ID as specified in the JSON definition, or null if the ID is not specified.
	 */
	public String getElementID () {
		return elementID;
	}

	/**
	 * Returns the parameter map from which this element was created.
	 */
	public Map getParameters () {
		return parameters;
	}
	
	/////////////////////
	// Abstract methods
	/**
	 * Must be overridden by subclasses to create the Box2D physics bodies.
	 */
	public abstract void initializePhysics(World world);
	
	/**
	 * Must be overridden by subclasses to create the OpenGL items
	 */
	public void initializeGraphics() {};
	
	/**
	 * Must be overridden by subclasses to return a collection of all Box2D bodies which make up this element.
	 */
	public abstract Collection<Body> getBodies ();

	/**
	 * Must be overridden by subclasses to draw the element for gameplay.
	 */
	public abstract void draw();

	/**
	 * Called when a ball collides with a Body in this element. The default implementation does nothing (allowing objects to bounce
	 * off each other normally), subclasses can override (e.g. to apply extra force)
	 */
	public void handleCollision (Body ball, Body bodyHit, Level level) {
	}

}
