package edu.spaced.net.listener;

import com.badlogic.gdx.math.Vector2;

public interface SpawnListener {
	public void playerSpawned(long timestamp, int playerId, Vector2 position, float angle);
}
