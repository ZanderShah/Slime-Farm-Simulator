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
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect((int) getPosition().getX() - 4, (int) getPosition().getY() - 4, 8,
				8);
	}
}