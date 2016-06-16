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
 * Poison slimeball projectile
 *
 * @author Alexander Shah
 * @version Jun 15, 2016
 */
public class PoisonSlimeBall extends Projectile
{

	public PoisonSlimeBall(Vector2D pos, Vector2D dir, boolean player, long id)
	{
		super(new AABB(pos, 3, 3), 0, -1, pos, dir.getNormalized().multiply(
				Constants.POISON_SLIMEBALL_SPEED), true, player,
				Constants.SLIMEBALL_DAMAGE * 0.5, new StatusEffect(1200, 60,
						-1, StatusEffect.HEALTH, false),
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