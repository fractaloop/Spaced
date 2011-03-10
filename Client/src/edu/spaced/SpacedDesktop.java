package edu.spaced;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.esotericsoftware.minlog.Log;

/**
 * Program entry point.
 * 
 * @author Logan Lowell
 */
public class SpacedDesktop {

	public static void main(String[] args) {
		Log.set(Log.LEVEL_DEBUG);
		new JoglApplication(new Spaced(), "Spaced", 640, 360, false);
	}

}
