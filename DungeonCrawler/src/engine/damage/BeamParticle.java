package engine.damage;

import java.awt.Graphics;

import engine.AABB;
import utility.SpriteSheet;
import utility.Vector2D;

public class BeamParticle extends Projectile {
	
	public BeamParticle(int duration, Vector2D pos, Vector2D spd, boolean player) {
		super(new AABB(pos, 3, 3), 0, duration, pos, spd, true, player, 1, 1);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getHitbox().getPosition().add(offset);
		g.drawImage(SpriteSheet.PROJECTILES[3], (int) shifted.getX(), (int) shifted.getY(), null);
	}
}