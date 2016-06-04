import java.awt.Color;
import java.awt.Graphics;

public class Fireball extends Projectile {

	public Fireball(Vector2D pos, Vector2D spd, boolean player) {
		super(new AABB(pos, 4, 4), 80, pos, spd.getNormalized().multiply(1.5), true, player, 3);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) getPosition().getX() - 2, (int) getPosition().getY() - 2, 4, 4);
	}
}