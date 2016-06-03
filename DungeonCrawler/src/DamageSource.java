import java.awt.Graphics;

public class DamageSource implements Drawable {
	private Hitbox hitbox;
	private int frequency;
	private int duration;
	
	private int hitCounter;
	private boolean singleHit;
	
	public DamageSource(Hitbox h, int f, int d, boolean single) {
		hitbox = h;
		frequency = f;
		duration = d;
		
		hitCounter = 0;
		
		singleHit = single;
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

	@Override
	public void draw(Graphics g) {
		
	}
}