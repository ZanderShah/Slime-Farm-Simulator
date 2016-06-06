public interface Hitbox {
	public boolean intersects(Hitbox other);
	public Vector2D getPosition();
	public void updatePosition(Vector2D pos);
	public Hitbox clone();
}