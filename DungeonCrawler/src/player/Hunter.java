package player;

import java.awt.Graphics;
import java.awt.Point;

import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import app.Test;
import engine.AABB;
import engine.Stats;
import engine.StatusEffect;
import engine.damage.Arrow;
import engine.damage.PiercingArrow;
import engine.damage.PoisonArrow;

public class Hunter extends Player {

	public Hunter() {
		super(4);
		setStats(new Stats(Constants.HUNTER_HEALTH, Constants.HUNTER_ATTACK_SPEED, Constants.HUNTER_ATTACK_LENGTH, Constants.HUNTER_SPEED,
				Constants.HUNTER_DEFENCE));
		setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(), getHeight()));
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.HUNTER_IMAGES[getDirection()], (int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);
	}

	@Override
	public int getWidth() {
		return SpriteSheet.HUNTER_IMAGES[getDirection()].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.HUNTER_IMAGES[0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r) {
		setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(), getHeight()));
		if (getAbilityActive(3) > 0 && getAbilityActive(3) % 20 == 0) {
			Vector2D dir = new Vector2D(cs.getMouse()).subtract(Test.middle).getNormalized();
			r.addDamageSource(new Arrow(getPos().add(dir.multiply(30)), dir, true));
		}
		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r) {
		if (getAbilityActive(3) == 0) {
			boolean attacked = super.attack(p, r);
			if (attacked) {
				if (getAbilityActive(1) != 0) {
					r.addDamageSource(new PoisonArrow(getPos().add(getAttackDir().multiply(30)), getAttackDir(), true));
					setAbilityActive(1, 0);
					setCooldown(1, Constants.AB_COOLDOWNS[4][0]);
				} else if (getAbilityActive(2) != 0) {
					r.addDamageSource(new PiercingArrow(getPos().add(getAttackDir().multiply(30)), getAttackDir(), true));
					setAbilityActive(2, 0);
					setCooldown(2, Constants.AB_COOLDOWNS[4][1]);
				} else {
					r.addDamageSource(new Arrow(getPos().add(getAttackDir().multiply(30)), getAttackDir(), true));
				}
			}
			return attacked;
		}
		return false;
	}

	// Poison arrow: while active, the next arrow fired inflicts poison
	// Cooldown: 10 seconds
	@Override
	public void ability1(Point p, Room r) {
		if (getCooldown(1) == 0) {
			setAbilityActive(1, -1);
			setAbilityActive(2, 0);
		} else if (getAbilityActive(1) != 0) {
			setAbilityActive(1, 0);
		}
	}

	// Piercing arrow: while active, the next arrow fired will pierce through
	// multiple enemies
	// Cooldown: 10 seconds
	@Override
	public void ability2(Point p, Room r) {
		if (getCooldown(2) == 0) {
			setAbilityActive(2, -1);
			setAbilityActive(1, 0);
		} else if (getAbilityActive(1) != 0) {
			setAbilityActive(2, 0);
		}
	}

	// Frenzy: rapid fire arrows for a short time
	// Cooldown: 10 seconds
	@Override
	public void ability3(Point p, Room r) {
		if (getAbilityActive(3) == 0 && getCooldown(3) == 0) {
			setAbilityActive(3, 240);
			giveStatusEffect(new StatusEffect(240, 0, 0.6, StatusEffect.SPEED, true));
		}
	}
}
