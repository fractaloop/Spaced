package edu.spaced.edit.editor;

import com.badlogic.gdx.math.Vector2;

public interface ViewportDelegate {
	public void updateStatus(String status);
	public void addWalls(Vector2[] walls);
}
