package engine;

public abstract class NonRepeatingDamageSource extends DamageSource {
	
	public NonRepeatingDamageSource(Hitbox h, int d, boolean single, boolean p, int dam) {
		super(h, 0, d, single, p, dam);
	}
}
