package edu.spaced.edit.editor;

import javax.vecmath.Vector2f;

import com.badlogic.gdx.math.Vector2;

public interface ViewportDelegate {
	public void updateStatus(String status);
	public void addWalls(Vector2[] walls);
}
