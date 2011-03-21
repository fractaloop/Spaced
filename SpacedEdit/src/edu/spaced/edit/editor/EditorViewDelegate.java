package edu.spaced.edit.editor;

import edu.spaced.simulation.Level;

public interface EditorViewDelegate {
	public EditorView getView();
	public boolean doSaveIfModified();
	public boolean doOpen();
	public void changeLevel(Level level);
	
	public void makeDirty();
}
