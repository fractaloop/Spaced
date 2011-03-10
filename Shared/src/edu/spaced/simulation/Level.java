package edu.spaced.simulation;

/**
 * Manages the data for a single level. This class is used to handle queries
 * to the level including collision, spawn points, and static objects.
 * 
 * @author Logan Lowell
 *
 */
public class Level {

	/**
	 * Loads a level from a given path. Null is returned if the level is not
	 * found or corrupted.
	 * 
	 * @param string path to level
	 * @return the loaded level, or null if not found 
	 */
	public static Level load(String string) {
		return new Level();
	}
}
