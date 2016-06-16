package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public abstract class LivingEntity implements Drawable, Serializable
{
	private static long curID;
	private long id;

	private Vector2D position;
	private Vector2D speed;
	private Stats stats;
	private AABB hitbox;
	private boolean immobile;
	private boolean invincible;
	private int direction;
	private int knockingBack;
	private Vector2D knockback;

	private ArrayList<StatusEffect> effects;

	public LivingEntity()
	{
		position = new Vector2D();
		speed = new Vector2D();
		effects = new ArrayList<StatusEffect>();
		stats = new Stats(0, 0, 0, 0, 0);
		hitbox = new AABB(position.add(new Vector2D(16, 16)), 32, 32);
		immobile = false;
		invincible = false;
		knockingBack = 0;
		knockback = new Vector2D();
		id = curID;
		curID++;
	}

	public Vector2D getPos()
	{
		return position;
	}

	public Vector2D getSpeed()
	{
		return speed;
	}

	public void setID(long id)
	{
		this.id = id;
	}

	public long getID()
	{
		return id;
	}

	public void setPos(Vector2D v)
	{
		position = v;
		hitbox.updatePosition(position);
	}

	public void setSpeed(Vector2D v)
	{
		speed = v.clone();
	}

	public void setStats(Stats s)
	{
		stats = s;
	}

	public Stats getStats()
	{
		return stats;
	}

	public void setHitbox(AABB hitbox)
	{
		this.hitbox = hitbox;
	}

	public AABB getHitbox()
	{
		return hitbox;
	}

	public ArrayList<StatusEffect> getEffects()
	{
		return effects;
	}

	public void giveStatusEffect(StatusEffect s)
	{
		effects.add(s);
	}

	public abstract int getWidth();

	public abstract int getHeight();

	public boolean getImmobile()
	{
		return immobile;
	}

	public void setImmobile(boolean i)
	{
		immobile = i;
	}

	public boolean getInvincible()
	{
		return invincible;
	}

	public void setInvincible(boolean i)
	{
		invincible = i;
	}

	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int i)
	{
		direction = i;
	}

	public void onDeath(Room r)
	{

	}

	public void knockback(Vector2D source, int strength)
	{
		setImmobile(true);
		knockback.addToThis(position.subtract(source));
		knockingBack = strength;
	}

	public void damage(double amount)
	{
		if (!invincible)
		{
			double effectiveDefence = stats.getDefence();
			for (int i = 0; i < effects.size(); i++)
			{
				if (effects.get(i).getType() == StatusEffect.DEF)
				{
					effectiveDefence *= effects.get(i).getStrength();
				}
			}
			stats.setHealth(Math.max(
					0,
					stats.getHealth()
							- Math.max(0, amount * (1 - effectiveDefence / 100))));
		}
	}

	public void heal(double amount)
	{
		stats.setHealth(Math.min(stats.getMaxHealth(), stats.getHealth()
				+ amount));
	}

	public void update(Room l)
	{
		boolean stunned = false;
		for (int s = 0; s < effects.size(); s++)
		{
			effects.get(s).elapseTime();
			if (effects.get(s).getTime() == 0)
			{
				effects.remove(s);
				s--;
			}
			else if (effects.get(s).getType() == StatusEffect.HEALTH)
			{
				if (effects.get(s).getStrength() < 0)
					damage(-effects.get(s).getStrength());
				else
					heal(effects.get(s).getStrength());
			}
			else if (effects.get(s).getType() == StatusEffect.STUN)
			{
				stunned = true;
			}
		}

		if (knockingBack > 0)
		{
			knockingBack--;
			if (knockingBack == 0)
			{
				knockback = new Vector2D();
				speed = new Vector2D();
				setImmobile(false);
			}
			else
			{
				speed = knockback.getNormalized().multiply(4);
			}
		}

		if (!stunned)
		{
			AABB tempX = hitbox.clone();
			AABB tempY = hitbox.clone();

			tempX.updatePosition(position.add(new Vector2D(speed.getX()
					+ (speed.getX() < 0 ? -1 : 1), 0)));
			tempY.updatePosition(position.add(new Vector2D(0, speed.getY()
					+ (speed.getY() < 0 ? -1 : 1))));

			Vector2D newSpeed = new Vector2D(0, 0);

			if (!l.hasCollisionWith(tempX))
			{
				newSpeed.addToThis(new Vector2D(speed.getX(), 0));
			}

			if (!l.hasCollisionWith(tempY))
			{
				newSpeed.addToThis(new Vector2D(0, speed.getY()));
			}

			speed = newSpeed;
			setPos(position.add(speed));
		}
	}

	@Override
	public abstract void draw(Graphics g, Vector2D offset);

	public void drawEntityDetails(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);

		// Health bar
		g.setColor(Color.GRAY);
		g.fillRect((int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
				- getHeight() / 2 - 10, getWidth(), 5);

		g.setColor(Color.RED);
		g.fillRect((int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
				- getHeight() / 2 - 10, (int) (getStats().getHealth()
				/ getStats().getMaxHealth() * getWidth()), 5);
	}

	public void drawStatusEffects(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);

		for (int i = 0; i < effects.size(); i++)
			if (effects.get(i).getType() == StatusEffect.HEALTH)
				if (effects.get(i).getAbsStrength() > 0)
					g.drawImage(
							SpriteSheet.HEALING,
							(int) shifted.getX()
									- SpriteSheet.HEALING.getWidth(null) / 2,
							(int) shifted.getY()
									- SpriteSheet.HEALING.getHeight(null) / 2,
							null);
				else
					g.drawImage(SpriteSheet.POISON,
							(int) shifted.getX()
									- SpriteSheet.POISON.getWidth(null) / 2,
							(int) shifted.getY()
									- SpriteSheet.POISON.getHeight(null) / 2,
							null);
			else if (effects.get(i).getType() == StatusEffect.RAGE)
				g.drawImage(
						SpriteSheet.RAGE,
						(int) shifted.getX()
								- SpriteSheet.RAGE.getWidth(null) / 2,
						(int) shifted.getY()
								- SpriteSheet.RAGE.getHeight(null) / 2,
						null);
	}
}