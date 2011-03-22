package edu.spaced.gui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MessageBox {
	protected ArrayList<String> messages;
	protected BitmapFont font;
	protected float x, y, width;
	protected int lines;
	protected BitmapFontCache cache;
	protected TextBounds metrics;
	protected SpriteBatch spriteBatch;
	
	/**
	 * Create a new MessageBox that will display text.
	 * 
	 * @param font the font to use
	 * @param x the x position of the box, from the upper left
	 * @param y the y position of the box, from the upper left
	 * @param width the width of the box in pixels
	 * @param lines the number of messages the box should display
	 */
	public MessageBox(BitmapFont font, float x, float y, float width, int lines) {
		messages = new ArrayList<String>();
		this.font = font;
		this.x = x;
		this.y = y;
		this.width = width;
		this.lines = lines;
		
		for (int i = 0; i < lines; i++)
			messages.add("");
		cache = new BitmapFontCache(font);
		spriteBatch = new SpriteBatch();
	}
	
	public void addString(String newString) {
		messages.remove(0);
		messages.add(newString);
		updateCache();
	}
	
	public void clear() {
		messages.clear();
		for (int i = 0; i < lines; i++)
			messages.add("");
	}
	
	protected void updateCache() {
		String buffer = new String();
		for (String line : messages)
			buffer += line + "\n";
		cache.setMultiLineText(buffer, x, y, width, HAlignment.LEFT);
	}
	
	public void draw() {
		spriteBatch.begin();
		cache.draw(spriteBatch);
		spriteBatch.end();
	}
}
