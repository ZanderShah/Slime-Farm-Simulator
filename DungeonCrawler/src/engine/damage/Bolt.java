package engine.damage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.AABB;
import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

public class Bolt extends Projectile
{
	public Bolt(Vector2D pos, Vector2D dir, boolean player, long id)
	{
		super(new AABB(pos, SpriteSheet.PROJECTILES[8].getWidth(null),
				SpriteSheet.PROJECTILES[8].getHeight(null)), 5, -1, pos, dir
				.getNormalized().multiply(
						Constants.BOLT_SPEED), false, player,
				Constants.BOLT_DAMAGE,
				Constants.BOLT_KNOCKBACK, id);
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
		((Graphics2D) g).drawImage(SpriteSheet.PROJECTILES[8], af, null);
	}
}