package engine;

import java.awt.Color;
import java.awt.Graphics;

import utility.Vector2D;

public class MageDebuff extends CircleDamageSource {

	public MageDebuff(Vector2D pos, int rad, int d, boolean p) {
		super(pos, rad, 0, d, false, p, 0, new StatusEffect(1, 0, 0.75, StatusEffect.DEF, true));
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		g.setColor(new Color(0, 0, 0, 64));
		Vector2D pos = getHitbox().getPosition().add(offset);
		int radius = getHitbox().getRadius();
		g.fillOval((int) pos.getX() - radius, (int) pos.getY() - radius, radius * 2, radius * 2);
	}
}
