package player;

import java.io.Serializable;

import engine.Stats;
import utility.Vector2D;

public class PlayerState implements Serializable {
	private long id;
	
	private Stats stats;
	private boolean immobile;
	
	private Vector2D position;
	private Vector2D speed;
	
	private int[] abilities;
	private int direction;
	
	public PlayerState(Player p) {
		position = p.getPos().clone();
		speed = p.getSpeed().clone();
		for (int i = 0; i < 4; i++)
			abilities[i] = p.getAbilityActive(i);
		direction = p.getDirection();
		immobile = p.getImmobile();
		stats = p.getStats();
		id = p.getID();
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public boolean getImmobile() {
		return immobile;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getSpeed() {
		return speed;
	}
	
	public int getAbility(int a) {
		return abilities[a];
	}
	
	public int getDirection() {
		return direction;
	}

	public long getID() {
		return id;
	}
}
