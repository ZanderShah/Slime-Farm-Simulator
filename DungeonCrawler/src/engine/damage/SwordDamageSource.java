package engine.damage;

import java.awt.Color;
import java.awt.Graphics;

import engine.SwordHitbox;
import utility.Vector2D;

public class SwordDamageSource extends DamageSource
{

	public SwordDamageSource(Vector2D position, int radius, int startAngle,
			int angle, int d, boolean p, double dam, int kb)
	{
		super(new SwordHitbox(position, radius, startAngle, angle), d, d,
				false, p, dam, kb);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		SwordHitbox sh = (SwordHitbox) getHitbox();
		Vector2D shifted = sh.getPosition().add(offset);
		g.setColor(Color.LIGHT_GRAY);
		g.fillArc((int) shifted.getX() - sh.getRadius(),
				(int) shifted.getY() - sh.getRadius(), sh.getRadius() * 2,
				sh.getRadius() * 2, sh.getStart(), sh.getAngle());
	}
}