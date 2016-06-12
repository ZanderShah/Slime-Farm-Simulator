package player;

import java.awt.Graphics;
import java.awt.Point;

import engine.AABB;
import engine.Stats;
import engine.damage.SwordDamageSource;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public class Thief extends Player {
	Vector2D dodgeDirection;

	public Thief() {
		super(1);
		setStats(new Stats(Constants.THIEF_HEALTH, Constants.THIEF_ATTACK_SPEED, Constants.THIEF_ATTACK_LENGTH, Constants.THIEF_SPEED, Constants.THIEF_DEFENCE));
		setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(), getHeight()));
		dodgeDirection = new Vector2D();
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		
		g.drawImage(SpriteSheet.THIEF_IMAGES[getDirection()][(getAbilityActive(1) == 0 ? 0 : 1)], (int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);
	}

	@Override
	public int getWidth() {
		return SpriteSheet.THIEF_IMAGES[getDirection()][0].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.THIEF_IMAGES[0][0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r) {
		setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(), getHeight()));
		if (getAbilityActive(2) > 0) {
			setSpeed(dodgeDirection.multiply(8.0));
		}
		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r) {
		boolean attacked = super.attack(p, r);
		if (attacked) {
			r.addDamageSource(new SwordDamageSource(getPos(), Constants.THIEF_SWORD_SIZE, (int) getAttackDir().getAngle() - Constants.THIEF_SWING_ANGLE / 2,
					Constants.THIEF_SWING_ANGLE, getStats().getAttackTime(), true, (getAbilityActive(1) != 0 ? Constants.THIEF_DAMAGE * 3 : Constants.THIEF_DAMAGE), Constants.THIEF_KNOCKBACK));
			if (getAbilityActive(1) != 0) {
				setAbilityActive(1, 0);
				setCooldown(1, Constants.AB_COOLDOWNS[1][0]);
			}
		}
		return attacked;
	}

	// Blink ability: turn invisible/invincible for 3 seconds, attack to
	// reappear and deal crit (3x) damage
	// Cooldown: 10 seconds from reappearing
	@Override
	public void ability1(Point p, Room r) {
		if (getAbilityActive(0) == 0 && getAbilityActive(1) == 0 && getCooldown(1) == 0) {
			setAbilityActive(1, 180);
		}
	}

	// Dodge ability: quickly move towards the cursor
	// Cooldown: 1.5 seconds
	@Override
	public void ability2(Point p, Room r) {
		if (getAbilityActive(0) == 0 && getAbilityActive(2) == 0 && getCooldown(2) == 0) {
			setAbilityActive(2, 15);
			setImmobile(true);
			dodgeDirection = (new Vector2D(p)).subtract(Constants.MIDDLE).getNormalized();
		}
	}

	// Throwing knives?
	// Cooldown 4 seconds
	@Override
	public void ability3(Point p, Room r) {

	}
}