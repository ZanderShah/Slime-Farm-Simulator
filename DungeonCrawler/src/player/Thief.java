package player;

import java.awt.Graphics;
import java.awt.Point;

import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import app.Test;
import engine.Stats;
import engine.SwordDamageSource;

public class Thief extends Player {
	private int blinking;
	private int dodging;

	Vector2D dodgeDirection;

	public Thief() {
		super();
		setStats(new Stats(Constants.THIEF_HEALTH, Constants.THIEF_ATTACK_SPEED, Constants.THIEF_ATTACK_LENGTH, Constants.THIEF_SPEED, Constants.THIEF_DEFENCE));
		blinking = 0;
		dodging = 0;
		dodgeDirection = new Vector2D();
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		
		g.drawImage(SpriteSheet.THIEF_IMAGES[getDirection()][(blinking == 0 ? 0 : 1)], (int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);
		
//		g.setColor(Color.GRAY);
//		if (blinking != 0) {
//			g.setColor(new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 128));
//		}
//		g.fillRect((int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, getWidth(), getHeight());
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
		if (blinking > 0) {
			blinking--;
			if (blinking == 0)
				setCooldown(1, Constants.THIEF_AB1_COOLDOWN);
		}
		if (dodging > 0) {
			dodging--;
			if (dodging == 0)
				setImmobile(false);
			setSpeed(dodgeDirection.multiply(8.0));
		}
		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r) {
		boolean attacked = super.attack(p, r);
		if (attacked) {
			r.addDamageSource(new SwordDamageSource(getPos(), Constants.THIEF_SWORD_SIZE, (int) getAttackDir().getAngle() - Constants.THIEF_SWING_ANGLE / 2,
					Constants.THIEF_SWING_ANGLE, getStats().getAttackTime(), true, (blinking != 0 ? Constants.THIEF_DAMAGE * 3 : Constants.THIEF_DAMAGE)));
			if (blinking != 0) {
				blinking = 0;
				setCooldown(1, 600);
			}
		}
		return attacked;
	}

	// Blink ability: turn invisible/invincible for 3 seconds, attack to
	// reappear and deal crit (3x) damage
	// Cooldown: 10 seconds from reappearing
	@Override
	public void ability1(Point p, Room r) {
		if (getAttacking() == 0 && getCooldown(1) == 0) {
			blinking = 180;
		}
	}

	// Dodge ability: quickly move towards the cursor
	// Cooldown: 1.5 seconds
	@Override
	public void ability2(Point p, Room r) {
		if (getAttacking() == 0 && getCooldown(2) == 0) {
			dodging = 15;
			setImmobile(true);
			dodgeDirection = (new Vector2D(p)).subtract(Test.middle).getNormalized();
			setCooldown(2, Constants.THIEF_AB2_COOLDOWN);
		}
	}

	// Throwing knives?
	// Cooldown 4 seconds
	@Override
	public void ability3(Point p, Room r) {

	}
}