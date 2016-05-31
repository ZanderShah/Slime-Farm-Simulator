public class Stats {
	private int maxHealth;
	private int health;
	private int attackSpeed;
	private double speed;
	private double defense;
	
	public Stats(int mh, int as, double s, double d) {
		maxHealth = mh;
		health = mh;
		attackSpeed = as;
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
	
	public void setSpeed(double s) {
		speed = s;
	}
	
	public void setDefense(double d) {
		defense = d;
	}
}