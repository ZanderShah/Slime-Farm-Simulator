package player;

import java.io.Serializable;

import engine.Stats;
import utility.Vector2D;

public class PlayerState implements Serializable {
	private long id;
	private int pClass;
	
	private Stats stats;
	
	private double posX, posY;
	private double spdX, spdY;
	
	private int[] abilities;
	private int direction;
	
	public PlayerState(Player p) {
		posX = p.getPos().getX();
		posY = p.getPos().getY();
		spdX = p.getSpeed().getX();
		spdY = p.getSpeed().getY();
		abilities = new int[4];
		for (int i = 0; i < 4; i++)
			abilities[i] = p.getAbilityActive(i);
		direction = p.getDirection();
		stats = p.getStats();
		id = p.getID();
		pClass = p.getType();
	}
	
	public Stats getStats() {
		return stats;
	}
	
	public Vector2D getPosition() {
		return new Vector2D(posX, posY);
	}
	
	public Vector2D getSpeed() {
		return new Vector2D(spdX, spdY);
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
	
	public int getType() {
		return pClass;
	}
}
