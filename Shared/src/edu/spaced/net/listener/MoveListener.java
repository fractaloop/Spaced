package edu.spaced.net.listener;

import com.badlogic.gdx.math.Vector2;

public interface MoveListener {
	public void playerMoved(long timestamp, int playerId, Vector2 position, float angle, Vector2 velocity);
}
