import java.awt.Color;
import java.awt.Graphics;

public class Arrow extends Projectile {
	
	public Arrow(Vector2D pos, Vector2D dir) {
		super(new AABB(pos, 8, 8), pos, dir.getNormalized().multiply(4.0), true);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) getPosition().getX() - 4, (int) getPosition().getY() - 4, 8, 8);
	}
}