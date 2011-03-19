package edu.spaced.edit.editor.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.FileFilter;

import edu.spaced.edit.editor.EditorView;
import edu.spaced.edit.editor.EditorViewDelegate;
import edu.spaced.simulation.Level;

public class OpenLevelAction extends AbstractAction {
	final JFileChooser fileChooser;
	private EditorViewDelegate delegate;
	
	public OpenLevelAction(String text, ImageIcon icon, String description, Integer mnemonic, EditorViewDelegate delegate) {
		super(text, icon);
		this.delegate = delegate;
		
		putValue(SHORT_DESCRIPTION, description);
		putValue(MNEMONIC_KEY, mnemonic);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new LevelFilter());
	}
	
	private class LevelFilter extends FileFilter {

		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return false;
			}
			
			return file.getName().endsWith(".spaced");
		}

		@Override
		public String getDescription() {
			return "Spaced levels (*.spaced)";
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (delegate.doSaveIfModified()) {
			int result = fileChooser.showOpenDialog((Component)delegate.getView());
			
			if (result == JFileChooser.APPROVE_OPTION) {
				Level level = Level.loadFile(fileChooser.getSelectedFile());
				delegate.changeLevel(level);
			}			
		}
	}
}
