package edu.spaced.edit.editor;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.vecmath.Vector2f;

import edu.spaced.simulation.Level;

public class Viewport extends JPanel {
	private ViewportDelegate delegate;
	
	private float scrollSpeed;
	private float zoom;
	private Vector2f levelPosition; // Note: Measured from center of viewport!
	
	private Level currentLevel;
	private Font font;
	
	// Drawing variables
	private boolean isDrawing;
	private ArrayList<Vector2f> drawingPoints;
	private Point mouseLocation;
	
	public Viewport(Level level) {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		enableEvents( AWTEvent.KEY_EVENT_MASK |
					  AWTEvent.MOUSE_EVENT_MASK |
					  AWTEvent.MOUSE_MOTION_EVENT_MASK |
					  AWTEvent.MOUSE_WHEEL_EVENT_MASK );
		
		font = new Font("SansSerif", Font.PLAIN, 10);
		
		// Since 1 "unit" is the size of a spaceship, lets make it so that
		// the default zoom has a 32pixel ship size
		currentLevel = level;
		zoom = 32;
		scrollSpeed = 1/10.f;	
		levelPosition = new Vector2f(0,0);
		
		requestFocus();
	}
	
	public void setDelegate(ViewportDelegate delegate) {
		this.delegate = delegate;
	}

	public ViewportDelegate getDelegate() {
		return delegate;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Clear out background
		super.paintComponent(g);

		// Draw the origin
		Graphics2D g2d = (Graphics2D)g;
		Vector2f origin = new Vector2f(0,0);
		Vector2f origin_x1 = new Vector2f(origin);
		Vector2f origin_x2 = new Vector2f(origin);
		Vector2f origin_y1 = new Vector2f(origin);
		Vector2f origin_y2 = new Vector2f(origin);
		origin_x1.add(new Vector2f( 0.5f, 0.0f));
		origin_x2.add(new Vector2f(-0.5f, 0.0f));
		origin_y1.add(new Vector2f( 0.0f, 0.5f));
		origin_y2.add(new Vector2f( 0.0f,-0.5f));
		
		g2d.setColor(Color.GRAY);
		Line2D line = new Line2D.Float(levelToView(origin_x1), levelToView(origin_x2));
		g2d.draw(line);
		line = new Line2D.Float(levelToView(origin_y1), levelToView(origin_y2));
		g2d.draw(line);
		
		// Render the level
		
		// Render any line we are drawing
		g2d.setColor(Color.LIGHT_GRAY);
		if (isDrawing) {
			for (int i = 0; i < drawingPoints.size() - 1; i++) {
				line = new Line2D.Float(levelToView(drawingPoints.get(i)), levelToView(drawingPoints.get(i+1)));
				g2d.draw(line);
			}
			// Draw the line from the last point to the current mouse position
			line = new Line2D.Float(levelToView(drawingPoints.get(drawingPoints.size()-1)), mouseLocation);
			g2d.draw(line);
		}
		
		// Render the cursor's location
		
		// Give some coordinates in the upper right
				
	}
	
	/////////////////////////
	// Coordinate transform
	public Vector2f viewToLevel(Point viewCoords) {
		Vector2f result = new Vector2f(viewCoords.x, viewCoords.y);
		result.sub(new Vector2f(getWidth() / 2, getHeight() / 2));
		result.scale(1/zoom);
		result.add(levelPosition);
		return result;		
	}
	
	public Point2D levelToView(Vector2f worldCoords) {
		Vector2f result = new Vector2f(worldCoords);
		result.sub(levelPosition);
		result.scale(zoom);
		result.add(new Vector2f(getWidth() / 2, getHeight() / 2));
		
		return new Point2D.Float(result.x, result.y);
	}

	///////////////////
	// Drawing points
	
	protected void addDrawingPoint(Point point) {
		if (drawingPoints == null)
			drawingPoints = new ArrayList<Vector2f>();
		
		drawingPoints.add(viewToLevel(point));
		delegate.updateStatus("Added point at (" + viewToLevel(point).x + ", " + viewToLevel(point).y + ")");
		
		repaint();
	}
	
	protected void commitDrawing() {
		if (drawingPoints == null)
			return;
		
		delegate.addWalls(drawingPoints.toArray(new Vector2f[drawingPoints.size()]));
		delegate.updateStatus("Added " + drawingPoints.size() + " new walls.");
		
		drawingPoints = null;
		
		repaint();
	}
	
	private void cancelDrawing() {
		isDrawing = false;
		drawingPoints = null;
		
		delegate.updateStatus("Canceled drawing.");
		
		repaint();
	}

	///////////////////
	// Input handling
		
	@Override
	protected void processKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_KP_UP:
			case KeyEvent.VK_UP:
				levelPosition.add(new Vector2f(0, -scrollSpeed * zoom));
				repaint();
				break;
			case KeyEvent.VK_KP_DOWN:
			case KeyEvent.VK_DOWN:
				levelPosition.add(new Vector2f(0, scrollSpeed * zoom));
				repaint();
				break;
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_LEFT:
				levelPosition.add(new Vector2f(-scrollSpeed * zoom, 0));
				repaint();
				break;
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_RIGHT:
				levelPosition.add(new Vector2f(scrollSpeed * zoom, 0));
				repaint();
				break;
			case KeyEvent.VK_SPACE:
				if (isDrawing && mouseLocation != null) {
					addDrawingPoint(mouseLocation);
				}
				break;
			case KeyEvent.VK_ESCAPE:
				if (isDrawing) {
					cancelDrawing();
				}
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (isDrawing) {
					drawingPoints.remove(drawingPoints.size() - 1);
					if (drawingPoints.size() == 0) {
						cancelDrawing();
					} else {
						repaint();
					}
				}
			}
		}
			
		super.processKeyEvent(e);
	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		requestFocus();
		mouseLocation = e.getPoint();
		Vector2f levelLocation = viewToLevel(e.getPoint());
		
		if (e.isPopupTrigger()) {
			JMenuItem item = new JMenuItem("Not implemented yet...");
			JPopupMenu popup = new JPopupMenu();
			popup.add(item);
			popup.show(e.getComponent(), e.getX(), e.getY());
		} else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
			// If we're currently drawing, continue to do so.
			if (isDrawing) {
				addDrawingPoint(e.getPoint());
			} else {
	 			// Check if we're selecting a nearby point
				
				currentLevel.findNearestPointWithin(levelLocation.x, levelLocation.y, 5 / zoom);
				
				// Nope, lets start drawing a segment then
				isDrawing = true;
				addDrawingPoint(e.getPoint());
			}
		} else if (e.getID() == MouseEvent.MOUSE_ENTERED) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		} else if (e.getID() == MouseEvent.MOUSE_EXITED) {
			this.setCursor(Cursor.getDefaultCursor());
		}

		e.consume();
	}	

	@Override
	protected void processMouseMotionEvent(MouseEvent e) {
		if (isDrawing) {
			mouseLocation = e.getPoint();
			repaint();
		}
		e.consume();
	}

	@Override
	protected void processMouseWheelEvent(MouseWheelEvent e) {
		zoom -= e.getWheelRotation();
		if (zoom < 1)
			zoom = 1;
		
		repaint();
		
		e.consume();
	}
	
	
}
