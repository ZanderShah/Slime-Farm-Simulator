package engine.damage;

import engine.CircleHitbox;
import engine.StatusEffect;
import utility.Vector2D;

public abstract class CircleDamageSource extends DamageSource {

	public CircleDamageSource(Vector2D pos, int rad, int f, int d, boolean single, boolean p, double dam, int kb) {
		super(new CircleHitbox(pos, rad), f, d, single, p, dam, kb);
	}
	
	public CircleDamageSource(Vector2D pos, int rad, int f, int d, boolean single, boolean p, double dam, StatusEffect e, int kb) {
		super(new CircleHitbox(pos, rad), f, d, single, p, dam, e, kb);
	}
	
	public CircleHitbox getHitbox() {
		return (CircleHitbox) super.getHitbox();
	}
}
