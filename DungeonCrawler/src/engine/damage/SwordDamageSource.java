package engine.damage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.SwordHitbox;
import utility.SpriteSheet;
import utility.Vector2D;

public class SwordDamageSource extends DamageSource
{

	public SwordDamageSource(Vector2D position, int radius, int startAngle,
			int angle, int d, boolean p, double dam, int kb, long id)
	{
		super(new SwordHitbox(position, radius, startAngle, angle), d, d,
				false, p, dam, kb, id);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		SwordHitbox sh = (SwordHitbox) getHitbox();
		Vector2D shifted = sh.getPosition().add(offset);
		g.setColor(Color.LIGHT_GRAY);
		/*g.fillArc((int) shifted.getX() - sh.getRadius(),
				(int) shifted.getY() - sh.getRadius(), sh.getRadius() * 2,
				sh.getRadius() * 2, sh.getStart(), sh.getAngle());*/
		//g.drawImage(SpriteSheet.MELEE_ATTACKS[0], )
		AffineTransform af = new AffineTransform();
		//af.translate(shifted.getX(), shifted.getY()-53);
		//af.translate(0,53);
		af.rotate(-Math.toRadians(sh.getStart() + sh.getAngle()/2));
		//af.translate(shifted.getX(), shifted.getY());
		((Graphics2D) g).drawImage(SpriteSheet.MELEE_ATTACKS[0], af, null);
	}
}