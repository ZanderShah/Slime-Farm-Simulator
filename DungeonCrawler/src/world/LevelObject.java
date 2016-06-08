package world;

import java.awt.Graphics;
import java.awt.Image;

import engine.AABB;
import engine.Drawable;
import utility.Vector2D;

public class LevelObject implements Drawable
{
	private Image img;
	private Vector2D position;
	private boolean destructable, blocksPlayer;
	private AABB hitbox;

	public LevelObject(Vector2D pos, boolean destructable,
			boolean blocksPlayer, Image img)
	{
		position = pos;
		this.destructable = destructable;
		this.blocksPlayer = blocksPlayer;
		this.img = img;
		setHitbox();
	}

	public Vector2D getPos()
	{
		return position;
	}

	public void setPos(Vector2D pos)
	{
		position = pos;
		setHitbox();
	}

	public int x()
	{
		return (int) position.getX();
	}

	public int y()
	{
		return (int) position.getY();
	}
	
	public Image image()
	{
		return img;
	}

	public void setHitbox()
	{
		hitbox = new AABB(position.add(new Vector2D(width() / 2, height() / 2)),
				width(),
				height());
	}

	public boolean blocksPlayer()
	{
		return blocksPlayer;
	}

	public boolean destructable()
	{
		return destructable;
	}

	public int width()
	{
		return img.getWidth(null);
	}

	public int height()
	{
		return img.getHeight(null);
	}

	public AABB hitbox()
	{
		return hitbox;
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = position.add(offset);
		g.drawImage(img, (int) shifted.getX(), (int) shifted.getY(), null);
	}
}