package engine.damage;

import java.awt.Color;
import java.awt.Graphics;

import engine.CircleHitbox;
import utility.Vector2D;

public class FireCircle extends CircleDamageSource {

	public FireCircle(Vector2D pos, int rad, int f, int d, boolean p, double dam) {
		super(pos, rad, f, d, false, p, dam);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		CircleHitbox h = getHitbox();
		Vector2D shifted = h.getPosition().add(offset);
		g.setColor(new Color(255, 0, 0, 128));
		g.fillOval((int) shifted.getX() - h.getRadius(), (int) shifted.getY() - h.getRadius(), h.getRadius() * 2, h.getRadius() * 2);
	}
}