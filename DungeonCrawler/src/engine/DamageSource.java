package engine;
import world.Room;

public abstract class DamageSource implements Drawable {
	private Hitbox hitbox;
	private int frequency;
	private int duration;
	
	private int hitCounter;
	private boolean singleHit;
	
	private boolean player;
	private int damage;
	
	private StatusEffect effect;
	
	public DamageSource(Hitbox h, int f, int d, boolean single, boolean p, int dam) {
		hitbox = h;
		frequency = f;
		duration = d;
		hitCounter = 0;
		singleHit = single;
		player = p;
		damage = dam;
	}
	
	public DamageSource(Hitbox h, int f, int d, boolean single, boolean p, int dam, StatusEffect e) {
		hitbox = h;
		frequency = f;
		duration = d;
		hitCounter = 0;
		singleHit = single;
		player = p;
		damage = dam;
		effect = e;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public void update(Room r) {
		if (hitCounter == 0) hitCounter = frequency;
		if (duration > 0) duration--;
	}
	
	public int getHitCounter() {
		return hitCounter;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public boolean isSingleHit() {
		return singleHit;
	}
	
	public boolean isPlayer() {
		return player;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public StatusEffect getEffect() {
		return effect;
	}
}