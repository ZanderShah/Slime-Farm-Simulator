package engine;

import utility.Vector2D;

public class CircleHitbox implements Hitbox {
	
	private Vector2D position;
	private int radius;
	
	public CircleHitbox(Vector2D pos, int rad) {
		position = pos;
		radius = rad;
	}
	
	@Override
	public boolean intersects(Hitbox other) {
		if (other instanceof CircleHitbox) {
			CircleHitbox o = (CircleHitbox) other;
			double dist2 = Math.pow(position.getX() - o.position.getX(), 2) + Math.pow(position.getY() - o.position.getY(), 2);
			return dist2 <= (radius + o.radius) * (radius + o.radius);
		} else if (other instanceof AABB) {
			return other.intersects(this);
		}
		return false;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}
	
	public int getRadius() {
		return radius;
	}

	@Override
	public void updatePosition(Vector2D pos) {
		position = pos;
	}

	@Override
	public Hitbox clone() {
		
		return null;
	}

}
