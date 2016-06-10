package player;

import java.awt.Graphics;
import java.awt.Point;

import app.Test;
import engine.Arrow;
import engine.PoisonArrow;
import engine.Stats;
import engine.StatusEffect;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public class Hunter extends Player {
	
	private boolean poisonLoaded;
	private boolean piercingLoaded;
	
	private int frenzy;
	
	public Hunter() {
		super();
		setStats(new Stats(Constants.HUNTER_HEALTH, Constants.HUNTER_ATTACK_SPEED, Constants.HUNTER_ATTACK_LENGTH, Constants.HUNTER_SPEED,
				Constants.HUNTER_DEFENCE));
		poisonLoaded = false;
		piercingLoaded = false;
		frenzy = 0;
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
		if (frenzy > 0) {
			frenzy--;
			if (frenzy % 20 == 0) {
				Vector2D dir = new Vector2D(cs.getMouse()).subtract(Test.middle).getNormalized();
				r.addDamageSource(new Arrow(getPos().add(dir.multiply(30)), dir, false, false));
			}
			if (frenzy == 0) {
				setCooldown(3, Constants.HUNTER_AB3_COOLDOWN);
			}
		}
		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r) {
		if (frenzy == 0) {
			boolean attacked = super.attack(p, r);
			if (attacked) {
				if (poisonLoaded) {
					r.addDamageSource(new PoisonArrow(getPos().add(getAttackDir().multiply(30)), getAttackDir(), true));
					poisonLoaded = false;
					setCooldown(1, Constants.HUNTER_AB1_COOLDOWN);
				} else if (piercingLoaded) {
					r.addDamageSource(new Arrow(getPos().add(getAttackDir().multiply(30)), getAttackDir(), true, true));
					piercingLoaded = false;
					setCooldown(2, Constants.HUNTER_AB2_COOLDOWN);
				} else {
					r.addDamageSource(new Arrow(getPos().add(getAttackDir().multiply(30)), getAttackDir(), false, false));
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
			poisonLoaded = true;
			piercingLoaded = false;
		} else if (poisonLoaded) {
			poisonLoaded = false;
		}
	}

	// Piercing arrow: while active, the next arrow fired will pierce through multiple enemies
	// Cooldown: 10 seconds
	@Override
	public void ability2(Point p, Room r) {
		if (getCooldown(2) == 0) {
			piercingLoaded = true;
			poisonLoaded = false;
		} else {
			piercingLoaded = false;
		}
	}

	// Frenzy: rapid fire arrows for a short time
	// Cooldown: 10 seconds
	@Override
	public void ability3(Point p, Room r) {
		if (frenzy == 0 && getCooldown(3) == 0) {
			frenzy = 240;
			giveStatusEffect(new StatusEffect(240, 0, 0.6, StatusEffect.SPEED, true));
		}
	}
}