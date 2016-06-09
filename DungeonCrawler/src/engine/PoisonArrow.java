package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

public class PoisonArrow extends Projectile {

	public PoisonArrow(Vector2D pos, Vector2D dir, boolean player) {
		super(new AABB(pos, 8, 8), -1, pos,
				dir.getNormalized().multiply(Constants.ARROW_SPEED), true,
				player, Constants.ARROW_DAMAGE,
				new StatusEffect(120, 20, -4, StatusEffect.HEALTH, false));
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPosition().add(offset);

		AffineTransform af = new AffineTransform();
		af.rotate(-Math.toRadians(getSpeed().getAngle()), shifted.getX(), shifted.getY());
		af.translate(shifted.getX(), shifted.getY());
		((Graphics2D) g).drawImage(SpriteSheet.PROJECTILES[5], af, null);
	}
}