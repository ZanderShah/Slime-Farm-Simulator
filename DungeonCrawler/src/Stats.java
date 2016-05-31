public class Stats {
	private int maxHealth;
	private int health;
	private double speed;
	private double defense;
	
	public Stats(int mh, double s, double d) {
		maxHealth = mh;
		health = mh;
		speed = s;
		defense = d;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getHealth() {
		return health;
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
	
	public void setSpeed(double s) {
		speed = s;
	}
	
	public void setDefense(double d) {
		defense = d;
	}
}