package edu.spaced.edit.editor.actions;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import edu.spaced.edit.editor.EditorController;
import edu.spaced.edit.editor.EditorView;
import edu.spaced.edit.editor.EditorViewDelegate;
import edu.spaced.simulation.Level;

public class NewLevelAction extends AbstractAction {
	private EditorViewDelegate delegate;
	
	public NewLevelAction(String text, ImageIcon icon, String description, Integer mnemonic, EditorViewDelegate delegate) {
		super(text, icon);
		
		this.delegate = delegate;
		
		putValue(SHORT_DESCRIPTION, description);
		putValue(MNEMONIC_KEY, mnemonic);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		delegate.doNewLevel();
	}
}
