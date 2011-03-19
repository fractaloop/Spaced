package edu.spaced.edit.editor;

import edu.spaced.simulation.Level;

public class EditorController implements EditorViewDelegate{
	EditorView view;
	Level currentLevel;

	
	public EditorController() {
		view = new EditorView();
		view.setDelegate(this);
		view.create();		
	}


	@Override
	public boolean doSaveIfModified() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public void changeLevel(Level level) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public EditorView getView() {
		return view;
	}

}
