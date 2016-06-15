package engine.damage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.SwordHitbox;
import utility.SpriteSheet;
import utility.Vector2D;

public class SwordDamageSource extends DamageSource {

	public SwordDamageSource(Vector2D position, int radius, int startAngle, int angle, int d, boolean p, double dam, int kb, long id) {
		super(new SwordHitbox(position, radius, startAngle, angle), 0, d, false, p, dam, kb, id);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		SwordHitbox sh = (SwordHitbox) getHitbox();
		Vector2D shifted = sh.getPosition().add(offset);
		g.setColor(Color.LIGHT_GRAY);
		AffineTransform af = new AffineTransform();
		af.rotate(-Math.toRadians(sh.getStart() + sh.getAngle() / 2), shifted.getX(), shifted.getY());
		af.translate(shifted.getX(), shifted.getY() - 53);
		((Graphics2D) g).drawImage(SpriteSheet.MELEE_ATTACKS[0], af, null);
	}
}
