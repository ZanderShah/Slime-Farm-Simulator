package player;

import java.awt.Graphics;
import java.awt.Point;

import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import engine.AABB;
import engine.Stats;
import engine.StatusEffect;
import engine.damage.SwordDamageSource;

public class Warrior extends Player
{
	private SwordDamageSource ds;
	private boolean usedRage;

	public Warrior()
	{
		super(0);
		setStats(new Stats(Constants.WARRIOR_HEALTH,
				Constants.WARRIOR_ATTACK_SPEED,
				Constants.WARRIOR_ATTACK_LENGTH, Constants.WARRIOR_SPEED,
				Constants.WARRIOR_DEFENCE));
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.WARRIOR_IMAGES[getDirection()],
				(int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
						- getHeight() / 2, null);
	}

	@Override
	public int getWidth()
	{
		return SpriteSheet.WARRIOR_IMAGES[getDirection()].getWidth(null);
	}

	@Override
	public int getHeight()
	{
		return SpriteSheet.WARRIOR_IMAGES[0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r)
	{
		if (ds != null)
		{
			ds.getHitbox().updatePosition(getPos());
		}

		if (getAbilityActive(2) <= 0 && usedRage)
		{
			getStats().setDefence(getStats().getDefence() / 10);
			getStats().setSpeed(getStats().getSpeed() / 2);
			getStats()
					.setDamageMultiplier(getStats().getDamageMultiplier() / 4);
			usedRage = false;
		}

		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r)
	{
		boolean attacked = super.attack(p, r);
		if (attacked)
			ds = new SwordDamageSource(getPos(), Constants.WARRIOR_SWORD_SIZE,
					(int) getAttackDir().getAngle()
							- Constants.WARRIOR_SWING_ANGLE / 2,
					Constants.WARRIOR_SWING_ANGLE, getStats().getAttackTime(),
					true,
					Constants.WARRIOR_DAMAGE, Constants.WARRIOR_KNOCKBACK,
					getID(), 0);
		r.addDamageSource(ds, getStats().getDamageMultiplier());
		return attacked;
	}

	// Spin attack ability: Do a giant spinning sword attack, hitting everything
	// around you
	// Cooldown: 5 seconds
	@Override
	public void ability1(Point p, Room r)
	{
		if (getAbilityActive(0) == 0 && getCooldown(1) == 0)
		{
			setAbilityActive(0, getStats().getAttackTime());
			setAbilityActive(1, getStats().getAttackTime());
			r.addDamageSource(new SwordDamageSource(getPos(),
					(int) (Constants.WARRIOR_SWORD_SIZE * 1.5), 0, 360,
					getStats().getAttackTime(), true,
					Constants.WARRIOR_DAMAGE, Constants.WARRIOR_KNOCKBACK,
					getID(), 0), getStats().getDamageMultiplier());
		}
	}

	@Override
	public void ability2(Point p, Room r)
	{
		if (getAbilityActive(0) == 0 && getAbilityActive(2) == 0
				&& getCooldown(2) == 0)
		{
			setAbilityActive(2, Constants.WARRIOR_RAGE_LENGTH);
			getStats().setDefence(getStats().getDefence() * 10);
			getStats().setSpeed(getStats().getSpeed() * 2);
			getStats()
					.setDamageMultiplier(getStats().getDamageMultiplier() * 4);
			usedRage = true;
			giveStatusEffect(new StatusEffect(Constants.WARRIOR_RAGE_LENGTH, 0,
					0,
					StatusEffect.RAGE, false));
		}
	}

	@Override
	public void ability3(Point p, Room r)
	{
		if (getAbilityActive(0) == 0 && getAbilityActive(3) == 0
				&& getCooldown(3) == 0)
		{

		}
	}
}