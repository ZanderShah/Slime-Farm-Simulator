import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class LevelObject implements Drawable
{
	private Image img;
	private Vector2D position;
	private boolean destructable, blocksPlayer;
	private int width, height;
	private AABB hitbox;

	public LevelObject(Vector2D pos, boolean destructable,
			boolean blocksPlayer, int width, int height)
	{
		position = pos;
		this.destructable = destructable;
		this.blocksPlayer = blocksPlayer;
		this.width = width;
		this.height = height;
		if (blocksPlayer)
			hitbox = new AABB(
					pos.add(new Vector2D(width / 2, height / 2)), width,
					height);
	}

	public Vector2D getPos()
	{
		return position;
	}

	public int x()
	{
		return (int) position.getX();
	}

	public int y()
	{
		return (int) position.getY();
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
		return width;
	}

	public int height()
	{
		return height;
	}

	public AABB hitbox()
	{
		return hitbox;
	}

	@Override
	public void draw(Graphics g)
	{
		if (blocksPlayer)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.MAGENTA);
		g.fillRect(x(), y(), width, height);
	}
}