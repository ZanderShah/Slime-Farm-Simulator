public class Stats {
	private int maxHealth;
	private int health;
	private int attackSpeed;
	private int attackTime;
	private double speed;
	private double defense;
	
	public Stats(int mh, int as, int at, double s, double d) {
		maxHealth = mh;
		health = mh;
		attackSpeed = as;
		attackTime = at;
		speed = s;
		defense = d;
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
	
	public double getDefense() {
		return defense;
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
	
	public void setDefense(double d) {
		defense = d;
	}
}