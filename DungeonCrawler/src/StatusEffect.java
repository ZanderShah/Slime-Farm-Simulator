public class StatusEffect {
	
	public static final int HEALTH = 0;
	public static final int SPEED = 1;
	public static final int DEF = 2;
	
	private long timeLeft;
	private double strength;
	private int type;
	
	public StatusEffect(long time, double strength, int type) {
		timeLeft = time;
		this.strength = strength;
		this.type = type;
	}
	
	public long getTime() {
		return timeLeft;
	}

	public int getType() {
		return type;
	}
	
	public double getStrength() {
		return strength;
	}
	
	public void elapseTime() {
		if (timeLeft != 0) {
			timeLeft--;
		}
		if (timeLeft == 0) {
			strength = 0;
		}
	}
}