package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import engine.LivingEntity;
import engine.Stats;
import engine.StatusEffect;
import utility.Constants;
import utility.ControlState;
import utility.Vector2D;
import world.Room;

public abstract class Player extends LivingEntity {
	private Vector2D attackDirection;
	private int[] cooldowns;
	private int[] abilitiesActive;
	private int pClass, experience, level;

	public Player(int c) {
		super();
		setStats(new Stats(100, 40, 40, 3, 0));
		abilitiesActive = new int[4];
		cooldowns = new int[4];
		pClass = c;
		experience = level = 0;
	}

	public void addExperience(int exp) {
		experience = Math.min(experience + exp, Constants.EXPERIENCE_REQUIRED[level]);

		if (experience >= Constants.EXPERIENCE_REQUIRED[level] && level + 1 < Constants.EXPERIENCE_REQUIRED.length) {
			experience = 0;
			level++;

			getStats().setMaxHealth(getStats().getMaxHealth() * Constants.LEVEL_HEALTH[pClass]);
			getStats().setHealth(getStats().getMaxHealth());
			getStats().setDamageMultiplier(getStats().getDamageMultiplier() * Constants.LEVEL_DAMAGE[pClass]);
			getStats().setDefence(getStats().getDefence() * Constants.LEVEL_DEFENCE[pClass]);
			getStats().setSpeed(getStats().getSpeed() * Constants.LEVEL_SPEED[pClass]);
		}
	}

	public int getLevel() {
		return level;
	}

	public int getExperience() {
		return experience;
	}

	public void update(ControlState cs, Room r) {
		// send control state to server
		for (int i = 0; i < cooldowns.length; i++) {
			if (cooldowns[i] > 0)
				cooldowns[i]--;
			if (abilitiesActive[i] > 0) {
				abilitiesActive[i]--;
				if (abilitiesActive[i] == 0 && i > 0) {
					setCooldown(i, Constants.AB_COOLDOWNS[pClass][i - 1]);
					// lol super sketch
					if (this instanceof Thief && i == 2) {
						setImmobile(false);
					}
				}
			}
		}

		boolean stunned = false;
		for (int i = 0; i < getEffects().size(); i++) {
			if (getEffects().get(i).getType() == StatusEffect.STUN) {
				stunned = true;
			}
		}

		if (!getImmobile() && !stunned) {
			Vector2D speed = new Vector2D();

			if (cs.getPressed(ControlState.KEY_UP)) {
				speed.addToThis(new Vector2D(0, -1));
				setDirection(2);
			}
			if (cs.getPressed(ControlState.KEY_LEFT)) {
				speed.addToThis(new Vector2D(-1, 0));
				setDirection(1);
			}
			if (cs.getPressed(ControlState.KEY_DOWN)) {
				speed.addToThis(new Vector2D(0, 1));
				setDirection(0);
			}
			if (cs.getPressed(ControlState.KEY_RIGHT)) {
				speed.addToThis(new Vector2D(1, 0));
				setDirection(3);
			}
			if (cs.getPressed(ControlState.KEY_ATTACK)) {
				attack(cs.getMouse(), r);
			}
			if (cs.getPressed(ControlState.KEY_AB1)) {
				ability1(cs.getMouse(), r);
			}
			if (cs.getPressed(ControlState.KEY_AB2)) {
				ability2(cs.getMouse(), r);
			}
			if (cs.getPressed(ControlState.KEY_AB3)) {
				ability3(cs.getMouse(), r);
			}

			speed.normalize();
			speed.multiplyBy(getStats().getSpeed());
			for (StatusEffect e : getEffects()) {
				if (e.getType() == StatusEffect.SPEED) {
					speed.multiplyBy(e.getStrength());
				}
			}

			if (abilitiesActive[0] != 0) {
				speed.multiplyBy(0);
			}

			setSpeed(speed);
		}

		super.update(r);
	}

	public int getCooldown(int c) {
		return cooldowns[c];
	}

	public void setCooldown(int c, int v) {
		cooldowns[c] = v;
	}

	public Vector2D getAttackDir() {
		return attackDirection;
	}

	public int getAbilityActive(int a) {
		return abilitiesActive[a];
	}

	public void setAbilityActive(int a, int b) {
		abilitiesActive[a] = b;
	}

	@Override
	public abstract void draw(Graphics g, Vector2D offset);

	public void draw(Graphics g, Vector2D offset, boolean local) {
		draw(g, offset);
		if (!local) {
			drawHealth(g, offset);
		}
	}

	public boolean attack(Point p, Room r) {
		if (cooldowns[0] == 0 && abilitiesActive[0] == 0) {
			abilitiesActive[0] = getStats().getAttackTime();
			cooldowns[0] = getStats().getAttackSpeed();
			Vector2D direction = (new Vector2D(p).subtract(Constants.MIDDLE));
			direction.normalize();
			attackDirection = direction;
			return true;
		}
		return false;
	}

	public abstract void ability1(Point p, Room r);

	public abstract void ability2(Point p, Room r);

	public abstract void ability3(Point p, Room r);

	public static Player makePlayer(long id, int c) {
		Player p = makePlayer(c);
		p.setID(id);
		return p;
	}

	public static Player makePlayer(int c) {
		switch (c) {
		case 0:
			return new Warrior();
		case 1:
			return new Thief();
		case 2:
			return new Mage();
		case 3:
			return new Tank();
		case 4:
			return new Hunter();
		case 5:
			return new Cleric();
		}
		return null;
	}

	public int getType() {
		return pClass;
	}
}
