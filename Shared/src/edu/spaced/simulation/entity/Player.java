package edu.spaced.simulation.entity;

public class Player extends Entity {
	public enum State {
		OBSERVING,
		ALIVE,
		DEAD,
		WAITING_TO_RESPAWN
	}
	private int id; // Connection ID
	private String name;
	private State state = State.OBSERVING;
	
	public boolean equals(Player other) {
		return other.getId() == id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

}
