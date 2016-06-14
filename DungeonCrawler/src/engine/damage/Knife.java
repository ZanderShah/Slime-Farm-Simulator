package engine.damage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.AABB;
import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

public class Knife extends Projectile {

	public Knife(Vector2D pos, Vector2D dir, boolean player, double dam, long id) {
		super(new AABB(pos.clone(), 5, 5), 0, -1, pos, dir.getNormalized().multiply(Constants.KNIFE_SPEED), true, player, dam, Constants.KNIFE_KNOCKBACK, id);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPosition().add(offset);
		AffineTransform af = new AffineTransform();
		af.rotate(-Math.toRadians(getSpeed().getAngle()), shifted.getX(), shifted.getY());
		af.translate(shifted.getX(), shifted.getY());
		((Graphics2D) g).drawImage(SpriteSheet.PROJECTILES[7], af, null);
	}
}