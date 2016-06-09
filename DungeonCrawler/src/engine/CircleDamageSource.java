package engine;

import utility.Vector2D;

public abstract class CircleDamageSource extends DamageSource {

	public CircleDamageSource(Vector2D pos, int rad, int f, int d, boolean single, boolean p, int dam) {
		super(new CircleHitbox(pos, rad), f, d, single, p, dam);
	}
	
	public CircleDamageSource(Vector2D pos, int rad, int f, int d, boolean single, boolean p, int dam, StatusEffect e) {
		super(new CircleHitbox(pos, rad), f, d, single, p, dam, e);
	}
	
	public CircleHitbox getHitbox() {
		return (CircleHitbox) super.getHitbox();
	}
}
