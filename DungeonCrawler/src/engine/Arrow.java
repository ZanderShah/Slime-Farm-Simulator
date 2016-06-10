package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

public class Arrow extends Projectile {

	public Arrow(Vector2D pos, Vector2D dir, boolean piercing, boolean player) {
		super(new AABB(pos, 3, 3), (piercing ? 30 : 0), -1, pos, dir.getNormalized().multiply(Constants.ARROW_SPEED), !piercing, player, Constants.ARROW_DAMAGE);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPosition().add(offset).subtract(getSpeed().getNormalized().multiply(32));
		AffineTransform af = new AffineTransform();
		af.rotate(-Math.toRadians(getSpeed().getAngle()), shifted.getX(), shifted.getY());
		af.translate(shifted.getX(), shifted.getY());
		((Graphics2D) g).drawImage(SpriteSheet.PROJECTILES[4], af, null);
	}
}