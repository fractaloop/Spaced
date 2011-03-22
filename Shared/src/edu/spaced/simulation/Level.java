package edu.spaced.simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.math.Vector2;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.annotations.*;
import com.thoughtworks.xstream.io.path.Path;

import edu.spaced.simulation.elements.LevelElement;
import edu.spaced.simulation.elements.WallElement;

/**
 * Manages the data for a single level. This class is used to handle queries
 * to the level including collision, spawn points, and static objects.
 * 
 * @author Logan Lowell
 *
 */
@XStreamAlias("level")
public class Level {
	@XStreamImplicit(itemFieldName="walls")
	private ArrayList<LevelElement> elements;
	private String name;
	private String path;
	
	public Level() {
		elements = new ArrayList<LevelElement>();
		name = "untitled";
	}
	
	/////////////////
	// I/O handling
	
	protected static XStream initXStream() {
		XStream xstream = new XStream();
		
		// Alias classes Level uses so it doesn't bulk up the file
		xstream.alias("level", Level.class);
		xstream.alias("wallElement", WallElement.class);
		xstream.alias("point", Vector2.class);
		
		return xstream;
	}
	
	public boolean save() {
		return Level.saveToPath(this, path);
	}
	
	/**
	 * Serialize this level to the given path
	 * 
	 * @param path The path to the file to be written to
	 * @return save was successful
	 */
	public static boolean saveToPath(Level level, String path) {
		level.path = path;
		if (level.path == null) {
			return false;
		}
		
		XStream xstream = initXStream();

		FileWriter writer;
		try {
			writer = new FileWriter(level.path);
			xstream.toXML(level, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Level loadFromPath(String path) throws FileNotFoundException {
		XStream xstream = initXStream();
		FileReader reader;
		
		try {
			reader = new FileReader(path);
			
			Level newLevel = (Level)xstream.fromXML(reader);
			reader.close();
			
			return newLevel;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	///////////////////////
	// Level modification
	public void addElement(LevelElement element) {
		elements.add(element);
	}
	
	//////////////////////////
	// Geometry and querying
	
	public Vector2 findNearestPointWithin(float x, float y, float radius) {
		Vector2 result = null;
		// TODO search level geometry!
		
		return result;
	}
	
	//////////////
	// Accessors
	
	public String getName() {
		return name;
	}
	
	public Collection<LevelElement> getElements() {
		return elements;
	}

	public String getFilename() {
		File handle = new File(path);
		return handle.getName().toLowerCase();
	}

	public Object getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
