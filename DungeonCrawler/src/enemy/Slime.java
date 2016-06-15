package enemy;

import utility.Vector2D;
import engine.AABB;

public abstract class Slime extends Enemy
{
	public Slime(int x, int y)
	{
		super();
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
		setPos(new Vector2D(x, y));
	}
}
