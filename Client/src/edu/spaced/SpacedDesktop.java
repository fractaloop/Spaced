package edu.spaced;

import com.badlogic.gdx.backends.jogl.JoglApplication;

/**
 * Program entry point.
 * 
 * @author Logan Lowell
 */
public class SpacedDesktop {

	public static void main(String[] args) {
		new JoglApplication(new Spaced(), "My First Triangle", 640, 360, false);
	}

}
