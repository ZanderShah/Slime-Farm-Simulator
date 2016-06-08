package engine;

import java.awt.Color;
import java.awt.Graphics;

import utility.Vector2D;

public class BeamParticle extends Projectile {

	public static final int SIZE = 2;
	
	public BeamParticle(int duration, Vector2D pos, Vector2D spd, boolean player) {
		super(new AABB(pos, SIZE, SIZE), duration, pos, spd, true, player, 1);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		g.setColor(Color.BLUE);
		Vector2D shifted = getHitbox().getPosition().add(offset);
		g.fillRect((int) shifted.getX() - SIZE / 2, (int) shifted.getY() - SIZE / 2, SIZE, SIZE);
	}
}