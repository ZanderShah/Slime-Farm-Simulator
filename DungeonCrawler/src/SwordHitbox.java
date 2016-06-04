public class SwordHitbox implements Hitbox {

	private Vector2D position;
	private int radius;
	private int startAngle;
	private int endAngle;
	
	@Override
	public boolean intersects(Hitbox other) {
		if (other instanceof AABB) {
			return other.intersects(this);
		} else if (other instanceof SwordHitbox) {
			// this technically won't ever be used (no sword-sword collisions)
			return false;
		}
		return false;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getStart() {
		return startAngle;
	}
	
	public int getEnd() {
		return endAngle;
	}

	@Override
	public Vector2D getPosition() {
		return position;
	}

	@Override
	public void updatePosition(Vector2D pos) {
		position = pos;
	}
}