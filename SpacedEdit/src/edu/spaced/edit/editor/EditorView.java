package edu.spaced.edit.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import com.badlogic.gdx.math.Vector2;

import edu.spaced.edit.editor.actions.*;
import edu.spaced.simulation.Level;
import edu.spaced.simulation.elements.WallElement;

public class EditorView extends JFrame implements ViewportDelegate {
	private Level level;
	private EditorViewDelegate delegate;
	private Viewport viewport;
	private JLabel statusBar;
	
	// Actions
	NewLevelAction newLevelAction;
	OpenLevelAction openLevelAction;
	SaveLevelAction saveLevelAction;
	
	public EditorView(Level theLevel) {
		level = theLevel;
	}
	
	public void create() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());
		
		// First lets make the actions we'll want to use
		createActions();
		// Menus
		createMenu();
		// Lets add a toolbar
		createToolbar();
		// Add another toolbar for the inspector
		createInspector();
		// Add a statusbar
		statusBar = new JLabel("Initialized.");
		statusBar.setPreferredSize(new Dimension(100, 16));
		this.add(statusBar, BorderLayout.SOUTH);
		
		// Add a viewport
		viewport = new Viewport(level);
		viewport.setDelegate(this);
		this.add(viewport);
		
		this.pack();
        this.setTitle("Editing " + level.getName());
        this.setVisible(true);
	}
	
	private void createActions() {
		newLevelAction = new NewLevelAction("New",
											new ImageIcon("images/new.png"),
											"Create an empty level.",
											new Integer(KeyEvent.VK_N),
											delegate);
		openLevelAction = new OpenLevelAction("Open",
											new ImageIcon("images/open.png"),
											"Open an existing level.",
											new Integer(KeyEvent.VK_O),
											delegate);
		saveLevelAction = new SaveLevelAction("Save",
											new ImageIcon("images/save.png"),
											"Save the current level.",
											new Integer(KeyEvent.VK_S),
											delegate);
		
	}
	
	private void createMenu() {
		// TODO Auto-generated method stub
		
	}
	
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);

		// And add some buttons
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		buttons.add(new JButton(newLevelAction));
		buttons.add(new JButton(openLevelAction));
		buttons.add(new JButton(saveLevelAction));
		
		// Icons only if there is one
		for (JButton button : buttons) {
			if (button.getIcon() != null) {
				button.setText("");
			}

			toolbar.add(button);
		}

		this.add(toolbar, BorderLayout.NORTH);
	}

	private void createInspector() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);
		// And add some buttons
		JTextField textField = new JTextField(8);
		toolbar.add(textField);
		this.add(toolbar, BorderLayout.EAST);
	}

	public void setDelegate(EditorViewDelegate delegate) {
		this.delegate = delegate;
	}

	////////////////
	// Accessors
	public synchronized Level getLevel() {
		return level;
	}

	public synchronized void setLevel(Level level) {
		// TODO update the viewport's level!
		this.level = level;
		viewport.setLevel(level);
	}

	//////////////////////
	// Viewport delegate
	@Override
	public void updateStatus(String status) {
		statusBar.setText(status);		
	}

	@Override
	public void addWalls(Vector2[] segments) {
		WallElement walls = new WallElement(segments);
		level.addElement(walls);
		
		delegate.makeDirty();
	}

}
