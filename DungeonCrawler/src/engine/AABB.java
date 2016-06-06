package engine;
import java.awt.Rectangle;

import utility.Vector2D;

public class AABB implements Hitbox {
	
	private Vector2D position;
	private int width, height;
	
	public AABB(Vector2D pos, int w, int h) {
		position = pos;
		width = w;
		height = h;
	}
	
	public Rectangle getRect() {
		return new Rectangle((int) position.getX() - width / 2, (int) position.getY() - height / 2, width, height);
	}
	
	@Override
	public void updatePosition(Vector2D pos) {
		position = pos;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public boolean intersects(Hitbox other) {
		if (other instanceof AABB) {
			AABB o = (AABB) other;
			
			return this.getRect().intersects(o.getRect());
		} else if (other instanceof SwordHitbox) {
			
		}
		
		return false;
	}
	
	@Override
	public AABB clone() {
		return new AABB(position.clone(), width, height);
	}
}