package edu.spaced.simulation.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import edu.spaced.simulation.Renderer;

/**
 * A wall element is defined as a series of straight line segments between n
 * points. Given n points, there are n-1 walls created. A wall can be created,
 * but to use the physics, the Box2D physics bodies must be initialized with
 * {@code initialize(World world)}.
 * 
 * @author Logan Lowell
 */
public class WallElement extends LevelElement {
	private ArrayList<Vector2> points;
	
	transient Body wallBody;
	
	public WallElement(Vector2 start, Vector2 end) {
		this(new Vector2[] {start, end});
	}
	
	public WallElement(Vector2[] points) {
		this.points = new ArrayList<Vector2>(Arrays.asList(points));
	}


	public void initialize(World world) {
		// TODO Create Box2D bodies
	}
	
	public ArrayList<Vector2> getPoints() {
		return points;
	}
	
	@Override
	public Collection<Body> getBodies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Renderer renderer) {
		// TODO Auto-generated method stub
		
	}
}
