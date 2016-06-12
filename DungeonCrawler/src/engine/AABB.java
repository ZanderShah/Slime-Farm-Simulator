package engine;

import java.awt.Rectangle;

import utility.Vector2D;

public class AABB implements Hitbox
{

	private Vector2D position;
	private int width, height;

	public AABB(Vector2D pos, int w, int h)
	{
		position = pos;
		width = w;
		height = h;
	}

	public Rectangle getRect()
	{
		return new Rectangle((int) position.getX() - width / 2,
				(int) position.getY() - height / 2, width, height);
	}

	@Override
	public void updatePosition(Vector2D pos)
	{
		position = pos;
	}

	@Override
	public Vector2D getPosition()
	{
		return position;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public boolean isInside(Vector2D p)
	{
		double x = p.getX(), y = p.getY();

		return (x >= position.getX() - width / 2
				&& x <= position.getX() + width / 2
				&& y >= position.getY() - height / 2
				&& y <= position.getY() + height / 2);
	}

	@Override
	public boolean intersects(Hitbox other)
	{
		if (other instanceof AABB)
		{
			AABB o = (AABB) other;
			return this.getRect().intersects(o.getRect());
		}
		else if (other instanceof SwordHitbox)
		{
			SwordHitbox o = (SwordHitbox) other;

			for (int angle = o.getStart(); angle <= o.getAngle(); angle += (o
					.getAngle() - o.getStart()) / 4)
			{
				if (isInside(o.getPosition().add(new Vector2D(o.getRadius()
						* Math.cos(angle),
						o.getRadius() * Math.sin(angle)))))
					return true;
			}
		}
		else if (other instanceof CircleHitbox)
		{
			CircleHitbox o = (CircleHitbox) other;

			if (isInside(o.getPosition()) || o.isInside(position))
				return true;

			// Not done yet
			// Left wall
//			if (o.getPosition()
//					.subtract(
//							new Vector2D(position.getX() - width / 2, o
//									.getRadius() * Math.sin(0)))
//					.getLength() <= o.getRadius())
//				return true;
//			// Right wall
//			if (o.getPosition()
//					.subtract(
//							new Vector2D(position.getX() + width / 2, o
//									.getRadius() * Math.sin(0)))
//					.getLength() <= o.getRadius())
//				return true;
//			// Top wall
//			if (o.getPosition()
//					.subtract(
//							new Vector2D(o.getRadius() * Math.cos(0), position
//									.getY() - height / 2))
//					.getLength() <= o.getRadius())
//				return true;
//			// Bottom wall
//			if (o.getPosition()
//					.subtract(
//							new Vector2D(o.getRadius() * Math.cos(0), position
//									.getY() - height / 2))
//					.getLength() <= o.getRadius())
//				return true;
		}

		return false;
	}

	@Override
	public AABB clone()
	{
		return new AABB(position.clone(), width, height);
	}
}