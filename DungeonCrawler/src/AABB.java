import java.awt.Rectangle;

public class AABB implements Hitbox {
	
	private Vector2D position;
	private int width, height;
	
	public AABB(Vector2D pos, int w, int h) {
		position = pos;
		width = w;
		height = h;
	}
	
	public Rectangle getRect() {
		return new Rectangle((int) position.getX(), (int) position.getY(), width, height);
	}
	
	@Override
	public void updatePosition(Vector2D pos) {
		position = pos;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	private int getWidth() {
		return width;
	}
	
	private int getHeight() {
		return height;
	}

	@Override
	public boolean intersects(Hitbox other) {
		if (other instanceof AABB) {
			AABB o = (AABB) other;
			
			return this.getRect().intersects(o.getRect());
		}
		
		return false;
	}
}