package engine.damage;

import java.awt.Graphics;
import java.io.Serializable;

import engine.AABB;
import engine.CircleHitbox;
import engine.Drawable;
import engine.Hitbox;
import engine.LivingEntity;
import engine.StatusEffect;
import engine.SwordHitbox;
import utility.Constants;
import utility.Vector2D;
import world.Room;

/**
 * Represents anything in the game that can deal damage
 * @author Callum
 *
 */
public abstract class DamageSource implements Drawable, Serializable {
	private long source;

	private Hitbox hitbox;
	private int frequency;
	private int duration;

	private int hitCounter;
	private boolean singleHit;

	private boolean player;
	private double damage;
	private int knockback;
	private StatusEffect effect;

	public DamageSource(Hitbox h, int f, int d, boolean single, boolean p,
			double dam, int kb, long s) {
		hitbox = h;
		frequency = f;
		duration = d;
		hitCounter = (f == 0 ? 0 : 1);
		singleHit = single;
		player = p;
		damage = dam;
		knockback = kb;
		source = s;
	}

	public DamageSource(Hitbox h, int f, int d, boolean single, boolean p,
			double dam, StatusEffect e, int kb, long s) {
		hitbox = h;
		frequency = f;
		duration = d;
		hitCounter = (f == 0 ? 0 : 1);
		singleHit = single;
		player = p;
		damage = dam;
		effect = e;
		knockback = kb;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	/**
	 * Updates the damage source within a room
	 * @param r the room that the damage source is in
	 */
	public void update(Room r) {
		if (hitCounter == 0)
			hitCounter = frequency;
		if (hitCounter > 0)
			hitCounter--;
		if (duration > 0)
			duration--;
	}

	public void setHitbox(Hitbox hitbox) {
		this.hitbox = hitbox;
	}

	public long getSourceID() {
		return source;
	}

	public int getHitCounter() {
		return hitCounter;
	}

	public int getDuration() {
		return duration;
	}

	public double getDamage() {
		return damage;
	}

	public int getKnockback() {
		return knockback;
	}

	public StatusEffect getEffect() {
		return effect;
	}

	public boolean isSingleHit() {
		return singleHit;
	}

	public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean b) {
		player = b;
	}

	public void setDamage(double d) {
		damage = d;
	}

	/**
	 * Attempts to hit a living entity
	 * @param le the entity to try hitting
	 * @return whether or not the entity was successfully hit
	 */
	public boolean hit(LivingEntity le) {
		if (le.getHitbox().intersects(hitbox)) {
			le.damage(damage);
			le.knockback(hitbox.getPosition(), knockback);
			if (effect != null) {
				le.giveStatusEffect(effect);
			}
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		if (Constants.DEBUG) {
			Vector2D shift = getHitbox().getPosition().add(offset);
			if (hitbox instanceof AABB) {
				g.drawRect(
						(int) shift.getX() - ((AABB) hitbox).getWidth() / 2,
						(int) shift.getY() - ((AABB) hitbox).getHeight() / 2,
						((AABB) hitbox).getWidth(),
						((AABB) hitbox).getHeight());
			}
			else if (hitbox instanceof CircleHitbox) {
				g.drawOval(
						(int) shift.getX()
								- ((CircleHitbox) hitbox).getRadius(),
						(int) shift.getY()
								- ((CircleHitbox) hitbox).getRadius(),
						((CircleHitbox) hitbox).getRadius() * 2,
						((CircleHitbox) hitbox).getRadius() * 2);
			}
			else if (hitbox instanceof SwordHitbox) {
			}
		}
	}
}