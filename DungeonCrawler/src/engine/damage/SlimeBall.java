package engine.damage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.AABB;
import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

/**
 * Red slime projectile
 *
 * @author Alexander Shah
 * @version Jun 15, 2016
 */
public class SlimeBall extends Projectile
{

	public SlimeBall(Vector2D pos, Vector2D dir, int damage,
			long id)
	{
		super(new AABB(pos, 3, 3), 0, -1, pos, dir.getNormalized().multiply(
				Constants.SLIMEBALL_SPEED), true, false, Constants.SLIMEBALL_DAMAGE,
				damage, id);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPosition().add(offset).subtract(
				getSpeed().getNormalized().multiply(32));
		AffineTransform af = new AffineTransform();
		af.rotate(-Math.toRadians(getSpeed().getAngle()), shifted.getX(),
				shifted.getY());
		af.translate(shifted.getX(), shifted.getY());
		((Graphics2D) g).drawImage(SpriteSheet.PROJECTILES[5], af, null);
	}
}