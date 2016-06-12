package engine;

import utility.Vector2D;

public class SwordHitbox implements Hitbox
{

	private Vector2D position;
	private int radius;
	private int startAngle;
	private int angle;

	public SwordHitbox(Vector2D pos, int r, int sa, int a)
	{
		position = pos;
		radius = r;
		startAngle = sa;
		angle = a;
	}

	@Override
	public boolean intersects(Hitbox other)
	{
		if (other instanceof AABB)
		{
			return other.intersects(this);
		}
		else if (other instanceof SwordHitbox)
		{
			// this technically won't ever be used (no sword-sword collisions)
			return false;
		}
		return false;
	}

	public int getRadius()
	{
		return radius;
	}

	public int getStart()
	{
		return startAngle;
	}

	public int getAngle()
	{
		return angle;
	}

	public boolean isInside(Vector2D p)
	{
		double theta = p.subtract(position).getAngle();
		if (theta < 0)
			theta += 360;

		return position.subtract(p).getLength() <= radius
				&& theta >= startAngle
				&& theta <= startAngle + angle;
	}

	@Override
	public Vector2D getPosition()
	{
		return position;
	}

	@Override
	public void updatePosition(Vector2D pos)
	{
		position = pos;
	}

	@Override
	public SwordHitbox clone()
	{
		return new SwordHitbox(position.clone(), radius, startAngle, angle);
	}
}