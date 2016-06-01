import java.awt.Color;
import java.awt.Graphics;

public class Projectile implements Drawable {

	private Vector2D position;
	private Vector2D speed;
	
	public Projectile(Vector2D pos, Vector2D spd) {
		position = pos;
		speed = spd;
	}
	
	public void update(Room r) {
		position.addToThis(speed);
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getSpeed() {
		return speed;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) position.getX() - 4, (int) position.getY() - 4, 8, 8);
	}
}