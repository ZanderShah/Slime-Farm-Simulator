package engine.damage;

import java.awt.Graphics;

import utility.Vector2D;
import engine.Hitbox;

public class MeleeEnemyDamageSource extends DamageSource
{
	public MeleeEnemyDamageSource(Hitbox h)
	{
		super(h, 30, -1, false, false, 10);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
	}
}
