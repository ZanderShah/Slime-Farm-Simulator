package engine.damage;

import engine.Hitbox;
import engine.LivingEntity;
import engine.StatusEffect;
import player.Player;
import player.Tank;
import utility.Vector2D;
import world.Room;

/**
 * Represents a projectile, a moving damage source
 * @author Callum
 *
 */
public abstract class Projectile extends DamageSource {
	private Vector2D position;
	private Vector2D speed;

	public Projectile(Hitbox h, int frequency, int duration, Vector2D pos,
			Vector2D spd, boolean single, boolean player, double damage,
			int kb, long id) {
		super(h, frequency, duration, single, player, damage, kb, id);
		position = pos.clone();
		speed = spd.clone();
	}

	public Projectile(Hitbox h, int frequency, int duration, Vector2D pos,
			Vector2D spd, boolean single, boolean player, double damage,
			StatusEffect effect, int kb, long id) {
		super(h, frequency, duration, single, player, damage, effect, kb, id);
		position = pos;
		speed = spd;
	}

	@Override
	public void update(Room r) {
		super.update(r);
		position.addToThis(speed);
		getHitbox().updatePosition(position);
	}

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getSpeed() {
		return speed;
	}

	public boolean hit(LivingEntity le) {
		if (le == null) return false;
		if (le.getHitbox().intersects(getHitbox())) {
			if (!isPlayer() && le instanceof Tank
					&& ((Player) le).getAbilityActive(3) != 0) {
				speed = position.subtract(le.getPos()).getNormalized()
						.multiply(speed.getLength());
				setPlayer(true);
				return false;
			}
			else {
				le.damage(getDamage());
				le.knockback(position, getKnockback());
				if (getEffect() != null) {
					le.giveStatusEffect(getEffect());
				}
			}
			return true;
		}
		return false;
	}
}