package engine;

import java.awt.Color;
import java.awt.Graphics;

import utility.SpriteSheet;
import utility.Vector2D;

public class BeamParticle extends Projectile {
	
	public BeamParticle(int duration, Vector2D pos, Vector2D spd, boolean player) {
		super(new AABB(pos, 3, 3), duration, pos, spd, true, player, 1);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getHitbox().getPosition().add(offset);
		g.drawImage(SpriteSheet.PROJECTILES[3], (int) shifted.getX(), (int) shifted.getY(), null);
//		g.setColor(Color.BLUE);
//		g.fillRect((int) shifted.getX() - SIZE / 2, (int) shifted.getY() - SIZE / 2, SIZE, SIZE);
	}
}