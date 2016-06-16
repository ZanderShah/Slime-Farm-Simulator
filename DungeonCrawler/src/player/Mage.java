package player;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import engine.AABB;
import engine.ParticleEmitter;
import engine.Stats;
import engine.StatusEffect;
import engine.damage.BeamParticle;
import engine.damage.FireCircle;
import engine.damage.Fireball;
import engine.damage.MageDebuff;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public class Mage extends Player {

	private Vector2D beamDirection;
	private MageDebuff currentDebuff;

	public Mage() {
		super(2);
		setStats(new Stats(Constants.MAGE_HEALTH, Constants.MAGE_ATTACK_SPEED,
				Constants.MAGE_ATTACK_LENGTH, Constants.MAGE_SPEED,
				Constants.MAGE_DEFENCE));
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
		beamDirection = new Vector2D();
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		
		g.drawImage(SpriteSheet.MAGE_IMAGES[getDirection()],
				(int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
						- getHeight() / 2,
				null);
	}

	@Override
	public int getWidth() {
		return SpriteSheet.MAGE_IMAGES[getDirection()].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.MAGE_IMAGES[0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r) {
		// setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2,
		// getHeight() / 2)), getWidth(), getHeight()));
		if (getAbilityActive(1) > 0) {
			// Spawn 10 beam particles per tick
			for (int i = 0; i < 10; i++) {
				// Calculate random offset
				Vector2D spray = (new Vector2D(beamDirection.getY(),
						-beamDirection.getX()))
								.multiply(Math.random() * 32 - 16);
				spray.addToThis(beamDirection.multiply(Math.random() * 10.0));

				// Create beam particles
				r.addDamageSource(new BeamParticle(60, getPos().add(spray),
						beamDirection.multiply(10.0), true, getID()), getStats()
								.getDamageMultiplier());
			}
		}

		super.update(cs, r);
		if (currentDebuff != null) {
			currentDebuff.getHitbox().updatePosition(getPos());
		}
	}

	@Override
	public boolean attack(Point p, Room r) {
		boolean attacked = super.attack(p, r);
		if (attacked) {
			r.addDamageSource(
					new Fireball(
							getPos().clone(),
							(new Vector2D(
									getAttackDir().getAngle()
											+ ((int) (Math.random()
													* (Constants.MAGE_SPRAY
															+ 1))
													- Constants.MAGE_SPRAY
															/ 2))),
							true, getID()),
					getStats().getDamageMultiplier());
		}
		return attacked;
	}

	// Beam ability: Fires a 2 second beam of small particles in a single
	// direction
	// Cooldown: 10 seconds
	@Override
	public void ability1(Point p, Room r) {
		if (getAbilityActive(0) == 0 && getAbilityActive(1) == 0
				&& getCooldown(1) == 0) {
			setAbilityActive(0, 120);
			setAbilityActive(1, 120);
			beamDirection = (new Vector2D(p)).subtract(Constants.MIDDLE)
					.getNormalized();
		}
	}

	// AoE enemy defence debuff: Reduces enemies defence in an area around the
	// mage, but also slows the mage
	// Cooldown: 15 seconds
	@Override
	public void ability2(Point p, Room r) {
		if (getAbilityActive(2) == 0 && getCooldown(2) == 0) {
			setAbilityActive(2, Constants.MAGE_DEBUFF_LENGTH);
			currentDebuff = new MageDebuff(getPos(),
					Constants.MAGE_DEBUFF_RANGE, Constants.MAGE_DEBUFF_LENGTH,
					true, getID());
			r.addDamageSource(currentDebuff, 1);
			giveStatusEffect(new StatusEffect(Constants.MAGE_DEBUFF_LENGTH, 0,
					0.85, StatusEffect.SPEED, true));
		}
	}

	// Sets a small area on fire
	// Cooldown 20 seconds
	@Override
	public void ability3(Point p, Room r) {
		if (getAbilityActive(3) == 0 && getCooldown(3) == 0) {
			setAbilityActive(3, Constants.MAGE_FIRE_LENGTH);
			Vector2D pos = (new Vector2D(p)).add(getPos()).subtract(
					Constants.MIDDLE);
			r.addDamageSource(new FireCircle(pos, Constants.MAGE_FIRE_RANGE,
					30, Constants.MAGE_FIRE_LENGTH, true, 10, getID()), getStats()
							.getDamageMultiplier());
			r.addEmitter(new ParticleEmitter(0, pos, new Vector2D(),
					Constants.MAGE_FIRE_LENGTH, 60, 5, 20, 20,
					Constants.MAGE_FIRE_RANGE, 0.1, 0));
		}
	}
}
