package engine.damage;

import java.awt.Graphics;

import utility.Vector2D;
import world.Room;
import enemy.Slime;

public class SlimeDamageSource extends DamageSource
{
	private Slime slimey;

	public SlimeDamageSource(Slime s)
	{
		super(s.getHitbox(), 30, -1, false, false, 10);
		slimey = s;
	}

	@Override
	public void update(Room r)
	{
		super.update(r);
		setHitbox(slimey.getHitbox());
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
	}
}
