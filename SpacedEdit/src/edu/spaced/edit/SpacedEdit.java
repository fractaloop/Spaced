package edu.spaced.edit;

import edu.spaced.edit.editor.EditorController;

public class SpacedEdit {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EditorController();
            }
        });
	}
}
