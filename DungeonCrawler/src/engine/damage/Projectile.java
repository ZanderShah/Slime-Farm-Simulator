package engine.damage;

import engine.Hitbox;
import engine.LivingEntity;
import engine.StatusEffect;
import player.Tank;
import utility.Vector2D;
import world.Room;

public abstract class Projectile extends DamageSource
{

	private Vector2D position;
	private Vector2D speed;

	public Projectile(Hitbox h, int frequency, int duration, Vector2D pos,
			Vector2D spd, boolean single, boolean player, double damage)
	{
		super(h, frequency, duration, single, player, damage);
		position = pos;
		speed = spd;
	}

	public Projectile(Hitbox h, int frequency, int duration, Vector2D pos,
			Vector2D spd, boolean single, boolean player, double damage,
			StatusEffect effect)
	{
		super(h, frequency, duration, single, player, damage, effect);
		position = pos;
		speed = spd;
	}

	@Override
	public void update(Room r)
	{
		super.update(r);
		position.addToThis(speed);
	}

	public Vector2D getPosition()
	{
		return position;
	}

	public Vector2D getSpeed()
	{
		return speed;
	}

	public boolean hit(LivingEntity le)
	{
		if (le.getHitbox().intersects(getHitbox()))
		{
			if (!isPlayer() && le instanceof Tank && ((Tank) le).isReflecting())
			{
				speed = position.subtract(le.getPos()).getNormalized()
						.multiply(speed.getLength());
				setPlayer(true);
				return false;
			}
			else
			{
				le.damage(getDamage());
				if (getEffect() != null)
				{
					le.giveStatusEffect(getEffect());
				}
			}
			return true;
		}
		return false;
	}
}