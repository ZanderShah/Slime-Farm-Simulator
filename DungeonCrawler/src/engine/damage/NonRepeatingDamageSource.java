package engine.damage;

import java.util.ArrayList;

import engine.Hitbox;
import engine.LivingEntity;
import engine.StatusEffect;

/**
 * A damage source that can only hit each entity once (not ever used)
 * @author Callum
 *
 */
public abstract class NonRepeatingDamageSource extends DamageSource {
	
	private ArrayList<LivingEntity> alreadyHit;
	
	public NonRepeatingDamageSource(Hitbox h, int d, boolean p, int dam, int kb, long id) {
		super(h, 0, d, false, p, dam, kb, id);
		alreadyHit = new ArrayList<LivingEntity>();
	}
	
	public NonRepeatingDamageSource(Hitbox h, int d, boolean p, int dam, StatusEffect e, int kb, long id) {
		super(h, 0, d, false, p, dam, e, kb, id);
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
