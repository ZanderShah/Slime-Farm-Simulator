package engine;

import java.util.ArrayList;

public abstract class NonRepeatingDamageSource extends DamageSource {
	
	private ArrayList<LivingEntity> alreadyHit;
	
	public NonRepeatingDamageSource(Hitbox h, int d, boolean p, int dam) {
		super(h, 0, d, false, p, dam);
		alreadyHit = new ArrayList<LivingEntity>();
	}
	
	public NonRepeatingDamageSource(Hitbox h, int d, boolean p, int dam, StatusEffect e) {
		super(h, 0, d, false, p, dam, e);
		alreadyHit = new ArrayList<LivingEntity>();
	}
	
	@Override
	public boolean hit(LivingEntity le) {
		if (!alreadyHit.contains(le)) {
			if (super.hit(le)) {
				alreadyHit.add(le);
				return true;
			}
		}
		return false;
	}
}
