package enemy;

import utility.Vector2D;
import engine.AABB;

/**
 * General class for slime enemies
 *
 * @author Alexander Shah
 * @version Jun 15, 2016
 */
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
