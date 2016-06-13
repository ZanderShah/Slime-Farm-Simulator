package engine;
import java.io.Serializable;

import utility.Vector2D;

public interface Hitbox extends Serializable {
	public boolean intersects(Hitbox other);
	public Vector2D getPosition();
	public void updatePosition(Vector2D pos);
	public Hitbox clone();
}