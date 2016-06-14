package engine;

import java.io.Serializable;

public class Stats implements Serializable {
	private double maxHealth;
	private double health;
	private double damageMultiplier;
	private int attackSpeed;
	private int attackTime;
	private double speed;
	private double defence;
	
	public Stats(double mh, int as, int at, double s, double d) {
		maxHealth = mh;
		health = mh;
		damageMultiplier = 1;
		attackSpeed = as;
		attackTime = at;
		speed = s;
		defence = d;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getDamageMultiplier()
	{
		return damageMultiplier;
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
	
	public void setMaxHealth(double mh) {
		maxHealth = mh;
	}
	
	public void setHealth(double h) {
		health = h;
	}
	
	public void setDamageMultipler(double dm)
	{
		damageMultiplier = dm;
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