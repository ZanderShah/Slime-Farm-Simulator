package engine;
public class StatusEffect {
	
	public static final int HEALTH = 0;
	public static final int SPEED = 1;
	public static final int DEF = 2;
	
	private int timeLeft;
	private double strength;
	private int type;
	
	public StatusEffect(int time, double strength, int type) {
		timeLeft = time;
		this.strength = strength;
		this.type = type;
	}
	
	public int getTime() {
		return timeLeft;
	}

	public int getType() {
		return type;
	}
	
	public double getStrength() {
		return strength;
	}
	
	public void elapseTime() {
		if (timeLeft > 0) {
			timeLeft--;
		}
	}
}