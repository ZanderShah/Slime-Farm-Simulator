import java.awt.Color;
import java.awt.Graphics;

public class SwordDamageSource extends DamageSource {
	
	public SwordDamageSource(Vector2D position, int radius, int startAngle, int angle, int d, boolean p, int dam) {
		super(new SwordHitbox(position, radius, startAngle, angle), 0, d, false, p, dam);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		SwordHitbox sh = (SwordHitbox) getHitbox();
		g.fillArc((int) sh.getPosition().getX() - sh.getRadius(), (int) sh.getPosition().getY() - sh.getRadius(), sh.getRadius() * 2, sh.getRadius() * 2, sh.getStart(), sh.getAngle());
	}
}