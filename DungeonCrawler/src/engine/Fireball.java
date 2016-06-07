package engine;
import java.awt.Color;
import java.awt.Graphics;

import utility.Constants;
import utility.Vector2D;

public class Fireball extends Projectile {

	public Fireball(Vector2D pos, Vector2D spd, boolean player) {
		super(new AABB(pos, 4, 4), 80 + ((int) (Math.random() * 21) - 10), pos, spd.getNormalized().multiply(1.5), true, player, Constants.MAGE_DAMAGE);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) getPosition().getX() - 2, (int) getPosition().getY() - 2, 4, 4);
	}
}