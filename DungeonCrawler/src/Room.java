import java.awt.Rectangle;
import java.util.ArrayList;

public class Room
{
	private ArrayList<Player> players;
	// private ArrayList<Enemy> enemies;
	private ArrayList<LevelObject> objects;
	private int x, y, width, height;
	private boolean cleared;

	public Room(int xx, int yy, int w, int h)
	{
		x = xx;
		y = yy;
		width = w;
		height = h;
	}

	public int width()
	{
		return width;
	}

	public int height()
	{
		return height;
	}

	public int x()
	{
		return x;
	}

	public int y()
	{
		return y;
	}
}