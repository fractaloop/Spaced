package edu.spaced.simulation;

import java.io.File;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import edu.spaced.simulation.elements.WallElement;

/**
 * Manages the data for a single level. This class is used to handle queries
 * to the level including collision, spawn points, and static objects.
 * 
 * @author Logan Lowell
 *
 */
public class Level {
	private ArrayList<WallElement> walls;
	private String name;
	
	public Level() {
		name = "untitled";
	}
	/**
	 * Loads a level from a given path. Null is returned if the level is not
	 * found or corrupted.
	 * 
	 * @param string path to level
	 * @return the loaded level, or null if not found 
	 */
	public static Level parse(String xml) {
		XStream xstream = new XStream();
		
		// Alias classes Level uses so it doesn't bulk up the file
//		xstream.alias("region", Region.class);
		
		return (Level)xstream.fromXML(xml);
	}
	
	public static Level loadFile(File selectedFile) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public void findNearestPointWithin(float x, float y, float f) {
		// TODO Auto-generated method stub
		
	}
	public static Level loadFile(String string) {
		return new Level();
	}

}
