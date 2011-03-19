package edu.spaced.controllers;

import com.badlogic.gdx.math.Vector2;

import edu.spaced.net.listener.DeathListener;
import edu.spaced.net.listener.MoveListener;
import edu.spaced.net.listener.SpawnListener;

public class GameController implements SpawnListener, DeathListener, MoveListener{

	@Override
	public void playerMoved(long timestamp, int playerId, Vector2 position, float angle, Vector2 velocity) {
		// Convert player's timestamp into server timestamp
		// Rebroadcast movement info
		// A production game would require cheat-checking here.
	}

	@Override
	public void playerDied(int playerId) {
		// 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerSpawned(long timestamp, int playerId, Vector2 position,
			float angle) {
		// TODO Auto-generated method stub
		
	}

}
