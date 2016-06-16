package engine.damage;

import java.awt.Graphics;

import engine.AABB;
import engine.Hitbox;
import utility.Constants;
import utility.Vector2D;

/**
 * Main enemy hitbox which is applied to every enemy
 *
 * @author Alexander Shah
 * @version Jun 15, 2016
 */
public class MeleeEnemyDamageSource extends DamageSource
{
	public MeleeEnemyDamageSource(Hitbox h, double dam, int kb, long id)
	{
		super(h, 30, -1, false, false, dam, kb, id);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		if (Constants.DEBUG)
		{
			Vector2D shifted = getHitbox().getPosition().add(offset);
			g.drawRect(
					(int) shifted.getX() - ((AABB) getHitbox()).getWidth() / 2,
					(int) shifted.getY() - ((AABB) getHitbox()).getHeight() / 2,
					((AABB) getHitbox()).getWidth(),
					((AABB) getHitbox()).getHeight());
		}
	}
}
