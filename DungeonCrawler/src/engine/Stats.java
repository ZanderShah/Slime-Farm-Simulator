package engine;
public class Stats {
	private int maxHealth;
	private int health;
	private int attackSpeed;
	private int attackTime;
	private double speed;
	private double defence;
	
	public Stats(int mh, int as, int at, double s, double d) {
		maxHealth = mh;
		health = mh;
		attackSpeed = as;
		attackTime = at;
		speed = s;
		defence = d;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}

	public int getAttackTime() {
		return attackTime;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getDefence() {
		return defence;
	}
	
	public void setMaxHealth(int mh) {
		maxHealth = mh;
	}
	
	public void setHealth(int h) {
		health = h;
	}
	
	public void setAttackSpeed(int as) {
		attackSpeed = as;
	}
	
	public void setAttackTime(int at) {
		attackTime = at;
	}
	
	public void setSpeed(double s) {
		speed = s;
	}
	
	public void setDefence(double d) {
		defence = d;
	}
}