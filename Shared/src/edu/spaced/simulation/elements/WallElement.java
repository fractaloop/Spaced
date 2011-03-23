package edu.spaced.simulation.elements;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.BufferUtils;

import edu.spaced.simulation.Box2DFactory;
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
	
	transient private FloatBuffer vertexBuffer;
	transient private List<Body> wallBodies;
	
	public WallElement(Vector2 start, Vector2 end) {
		this(new Vector2[] {start, end});
	}
	
	public WallElement(Vector2[] points) {
		this.points = new ArrayList<Vector2>(Arrays.asList(points));
	}


	public void initializePhysics(World world) {
		wallBodies = new ArrayList<Body>();
		for (int i = 0; i < points.size() - 1; i++) {
			float x1, y1, x2, y2, restitution;
			x1 = points.get(i).x; 
			y1 = points.get(i).y; 
			x2 = points.get(i+1).x; 
			y2 = points.get(i+1).y;
			restitution = 0.75f;
			
			wallBodies.add(Box2DFactory.createThinWall(world, x1, y1, x2, y2, restitution));			
		}		
	}
	
	@Override
	public void initializeGraphics() {
		// Init the OpenGL stuff
		GL10 gl = Gdx.graphics.getGL10();
		vertexBuffer = BufferUtils.newFloatBuffer(points.size() * 2);
		for (int i = 0; i < points.size(); i++) {
			vertexBuffer.put(points.get(i).x);
			vertexBuffer.put(points.get(i).y);
		}
		System.out.println();
		vertexBuffer.rewind();
	}
	
	public ArrayList<Vector2> getPoints() {
		return points;
	}
	
	@Override
	public Collection<Body> getBodies() {
		return wallBodies;
	}

	@Override
	public void draw() {
		GL10 gl = Gdx.graphics.getGL10();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, points.size());
	}
}
