package player;

import java.awt.Graphics;
import java.awt.Point;

import app.Test;
import engine.AABB;
import engine.BeamParticle;
import engine.FireCircle;
import engine.Fireball;
import engine.MageDebuff;
import engine.Stats;
import engine.StatusEffect;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public class Mage extends Player {
	
	private int beaming;
	private Vector2D beamDirection;
	private MageDebuff currentDebuff;
	
	public Mage() {
		super();
		setStats(new Stats(Constants.MAGE_HEALTH, Constants.MAGE_ATTACK_SPEED, Constants.MAGE_ATTACK_LENGTH, Constants.MAGE_SPEED, Constants.MAGE_DEFENCE));
		setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(), getHeight()));
		beaming = 0;
		beamDirection = new Vector2D();
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.MAGE_IMAGES[0], (int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);
	}

	@Override
	public int getWidth() {
		return SpriteSheet.MAGE_IMAGES[0].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.MAGE_IMAGES[0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r) {
		if (beaming > 0) {
			beaming--;
			if (beaming == 0) 
				setCooldown(1, Constants.MAGE_AB1_COOLDOWN);
			
			// Spawn 10 beam particles per tick
			for (int i = 0; i < 10; i++) {
				// Calculate random offset
				Vector2D spray = (new Vector2D(beamDirection.getY(), -beamDirection.getX())).multiply(Math.random() * 32 - 16);
				spray.addToThis(beamDirection.multiply(Math.random() * 10.0));
				
				// Create beam particles
				r.addDamageSource(new BeamParticle(60, getPos().add(spray), beamDirection.multiply(10.0), true));
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
			r.addDamageSource(new Fireball(getPos().clone(), (new Vector2D(getAttackDir().getAngle()
					+ ((int) (Math.random() * (Constants.MAGE_SPRAY + 1)) - Constants.MAGE_SPRAY / 2))), true));
		}
		return attacked;
	}

	// Beam ability: Fires a 2 second beam of small particles in a single direction
	// Cooldown: 10 seconds
	@Override
	public void ability1(Point p, Room r) {
		if (getAttacking() == 0 && getCooldown(1) == 0) {
			setAttacking(120);
			beaming = 120;
			beamDirection = (new Vector2D(p)).subtract(Test.middle).getNormalized();
		}
	}

	// AoE enemy defence debuff: Reduces enemies defence in an area around the mage, but also slows the mage
	// Cooldown: 15 seconds
	@Override
	public void ability2(Point p, Room r) {
		if (getCooldown(2) == 0) {
			currentDebuff = new MageDebuff(getPos(), Constants.MAGE_DEBUFF_RANGE, Constants.MAGE_DEBUFF_LENGTH, true);
			r.addDamageSource(currentDebuff);
			setCooldown(2, Constants.MAGE_AB2_COOLDOWN + Constants.MAGE_DEBUFF_LENGTH);
			giveStatusEffect(new StatusEffect(Constants.MAGE_DEBUFF_LENGTH, 0, 0.6, StatusEffect.SPEED, true));
		}
	}

	// Sets a small area on fire
	// Cooldown 20 seconds
	@Override
	public void ability3(Point p, Room r) {
		if (getCooldown(3) == 0) {
			r.addDamageSource(new FireCircle((new Vector2D(p)).add(getPos()).subtract(Test.middle), 100, 30, 300, false, 3));
			setCooldown(3, Constants.MAGE_AB3_COOLDOWN);
		}
	}
}