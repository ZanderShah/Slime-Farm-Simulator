package engine.damage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.AABB;
import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

/**
 * An arrow that pierces through enemies, hitting multiple times
 * @author Callum
 *
 */
public class PiercingArrow extends Projectile {

	public PiercingArrow(Vector2D pos, Vector2D dir, boolean player, long id) {
		super(new AABB(pos, 3, 3), 5, -1, pos, dir.getNormalized().multiply(Constants.ARROW_SPEED), false, player, Constants.PIERCING_ARROW_DAMAGE, 0, id);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPosition().add(offset).subtract(getSpeed().getNormalized().multiply(32));
		AffineTransform af = new AffineTransform();
		af.rotate(-Math.toRadians(getSpeed().getAngle()), shifted.getX(), shifted.getY());
		af.translate(shifted.getX(), shifted.getY());
		((Graphics2D) g).drawImage(SpriteSheet.PROJECTILES[6], af, null);
	}
}