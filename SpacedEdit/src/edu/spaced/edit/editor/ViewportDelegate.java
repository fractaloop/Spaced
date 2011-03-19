package edu.spaced.edit.editor;

import javax.vecmath.Vector2f;

public interface ViewportDelegate {
	public void updateStatus(String status);
	public void addWalls(Vector2f[] segments);
}
