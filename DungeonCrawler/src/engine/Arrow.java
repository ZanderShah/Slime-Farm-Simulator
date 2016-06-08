package engine;

import java.awt.Color;
import java.awt.Graphics;

import utility.Constants;
import utility.Vector2D;

public class Arrow extends Projectile
{

	public Arrow(Vector2D pos, Vector2D dir, boolean player)
	{
		super(new AABB(pos, 8, 8), -1, pos, dir.getNormalized().multiply(
				Constants.ARROW_SPEED), true, player, Constants.ARROW_DAMAGE);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPosition().add(offset);
		g.setColor(Color.BLACK);
		g.fillRect((int) shifted.getX() - 4, (int) shifted.getY() - 4, 8,
				8);
	}
}