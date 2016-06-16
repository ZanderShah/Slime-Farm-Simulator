package player;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import engine.AABB;
import engine.Stats;
import engine.StatusEffect;
import engine.damage.Bolt;

public class Cleric extends Player
{
	public Cleric()
	{
		super(5);
		setStats(new Stats(Constants.CLERIC_HEALTH,
				Constants.CLERIC_ATTACK_SPEED, Constants.CLERIC_ATTACK_LENGTH,
				Constants.CLERIC_SPEED,
				Constants.CLERIC_DEFENCE));
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.CLERIC_IMAGES[getDirection()],
				(int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
						- getHeight() / 2, null);
	}

	@Override
	public int getWidth()
	{
		return SpriteSheet.CLERIC_IMAGES[getDirection()].getWidth(null);
	}

	@Override
	public int getHeight()
	{
		return SpriteSheet.CLERIC_IMAGES[0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r)
	{
		// setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2,
		// getHeight() / 2)), getWidth(), getHeight()));

		if (getAbilityActive(3) <= 0)
		{
			ArrayList<Player> friends = r.getPlayers();
			for (int i = 0; i < friends.size(); i++)
				friends.get(i).setInvincible(false);
		}

		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r)
	{
		boolean attacked = super.attack(p, r);

		if (attacked)
			r.addDamageSource(new Bolt(getPos().add(
					getAttackDir().multiply(30)), getAttackDir(),
					true, getID()), getStats().getDamageMultiplier());

		return attacked;
	}

	// Gives a small heal-over-time buff to all players
	// Cooldown: 20 seconds
	@Override
	public void ability1(Point p, Room r)
	{
		if (getAbilityActive(1) == 0 && getCooldown(1) == 0)
		{
			for (int i = 0; i < r.getPlayers().size(); i++)
			{
				r.getPlayers()
						.get(i)
						.giveStatusEffect(
								new StatusEffect(Constants.CLERIC_HEAL_LENGTH,
										60, Constants.CLERIC_HEAL_STRENGTH,
										StatusEffect.HEALTH, false));
			}
			setAbilityActive(1, Constants.CLERIC_HEAL_LENGTH);
		}
	}

	// Gives a burst heal to the player selected
	// Cooldown: 30 seconds
	@Override
	public void ability2(Point p, Room r)
	{
		if (getAbilityActive(2) == 0 && getCooldown(2) == 0)
		{
			Vector2D location = (new Vector2D(p)).add(getPos()).subtract(
					Constants.MIDDLE);
			Player closest = r.getPlayers().get(0);
			double minDist = location.subtract(r.getPlayers().get(0).getPos())
					.getLength();
			for (int i = 1; i < r.getPlayers().size(); i++)
			{
				double dist = location.subtract(r.getPlayers().get(i).getPos())
						.getLength();
				if (dist < minDist)
				{
					closest = r.getPlayers().get(i);
					minDist = dist;
				}
			}
			closest.giveStatusEffect(new StatusEffect(
					Constants.CLERIC_BURST_LENGTH, 5,
					Constants.CLERIC_BURST_STRENGTH, StatusEffect.HEALTH, false));
			setAbilityActive(2, Constants.CLERIC_BURST_LENGTH);
		}
	}

	@Override
	public void ability3(Point p, Room r)
	{
		if (getAbilityActive(3) == 0 && getCooldown(3) == 0)
		{
			ArrayList<Player> friends = r.getPlayers();
			for (int i = 0; i < friends.size(); i++)
				friends.get(i).setInvincible(true);
			setAbilityActive(3, Constants.CLERIC_INVULNERABLE_LENGTH);
		}
	}
}