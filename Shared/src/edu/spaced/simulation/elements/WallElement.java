package edu.spaced.simulation.elements;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.thoughtworks.xstream.annotations.*;

import edu.spaced.simulation.Renderer;

public class WallElement extends LevelElement {
	@XStreamImplicit
	private ArrayList<Vector2> points;
	
	@XStreamOmitField
	Body wallBody;
	
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
