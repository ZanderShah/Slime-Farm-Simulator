package engine.damage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import engine.SwordHitbox;
import utility.SpriteSheet;
import utility.Vector2D;

/**
 * Basic sword/swinging attack. Used by warrior, thief, tank
 * @author Callum
 *
 */
public class SwordDamageSource extends DamageSource
{
	private int type;

	public SwordDamageSource(Vector2D position, int radius, int startAngle,
			int angle, int d, boolean p, double dam, int kb, long id, int type)
	{
		super(new SwordHitbox(position, radius, startAngle, angle), 20, d,
				false, p, dam, kb, id);
		this.type = type;
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		SwordHitbox sh = (SwordHitbox) getHitbox();
		Vector2D shifted = sh.getPosition().add(offset);
		g.setColor(Color.LIGHT_GRAY);

		if (type == 2)
		{
			g.drawImage(
					SpriteSheet.MELEE_ATTACKS[type],
					(int) shifted.getX()
							- SpriteSheet.MELEE_ATTACKS[type].getWidth(null)
							/ 2,
					(int) shifted.getY()
							- SpriteSheet.MELEE_ATTACKS[type].getHeight(null)
							/ 2, null);
		}
		else
		{
			AffineTransform af = new AffineTransform();
			af.rotate(-Math.toRadians(sh.getStart() + sh.getAngle() / 2),
					shifted.getX(), shifted.getY());
			af.translate(shifted.getX(), shifted.getY()
					- SpriteSheet.MELEE_ATTACKS[type].getHeight(null) / 2);
			((Graphics2D) g).drawImage(SpriteSheet.MELEE_ATTACKS[type], af,
					null);
		}
	}
}
