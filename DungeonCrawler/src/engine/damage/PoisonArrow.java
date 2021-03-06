package engine.damage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.AABB;
import engine.StatusEffect;
import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

/**
 * An arrow that applies poison on hit
 * @author Callum
 *
 */
public class PoisonArrow extends Projectile
{

	public PoisonArrow(Vector2D pos, Vector2D dir, boolean player, long id)
	{
		super(new AABB(pos, SpriteSheet.PROJECTILES[5].getWidth(null),
				SpriteSheet.PROJECTILES[5].getHeight(null)), 0, -1, pos, dir
				.getNormalized().multiply(Constants.ARROW_SPEED), true, player,
				Constants.POISON_ARROW_DAMAGE, new StatusEffect(1200, 40, -5,
						StatusEffect.HEALTH, false),
				Constants.ARROW_KNOCKBACK, id);
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