import java.awt.Graphics;
import java.util.ArrayList;

public class Room implements Drawable
{
	// x y are used only for position relative to other rooms, when dealing with
	// the room individually,
	// bottom left is (0, 0) and top right is (width, height)
	private int x, y, width, height, id;
	private boolean cleared;
	private Room up, down, left, right;
	private ArrayList<LevelObject> objects;
	private ArrayList<DamageSource> damageSources;
	private ArrayList<Player> players;

	public Room(int x, int y, int width, int height, int id)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;

		cleared = false;
		up = down = left = right = null;
		objects = new ArrayList<LevelObject>();
		damageSources = new ArrayList<DamageSource>();
		players = new ArrayList<Player>();
	}

	public void update()
	{
		for (int i = 0; i < damageSources.size(); i++)
		{
			damageSources.get(i).update(this);
			if (damageSources.get(i).getDuration() == 0) {
				damageSources.remove(i);
				i--;
			} else {
				// check for collisions
			}
		}
	}

	public void addPlayer(Player p)
	{
		players.add(p);
	}

	public void addDamageSource(DamageSource ds)
	{
		damageSources.add(ds);
	}

	public void setUp(Room up)
	{
		this.up = up;
	}

	public void setDown(Room down)
	{
		this.down = down;
	}

	public void setLeft(Room left)
	{
		this.left = left;
	}

	public void setRight(Room right)
	{
		this.right = right;
	}

	public Room getUp()
	{
		return up;
	}

	public Room getDown()
	{
		return down;
	}

	public Room getLeft()
	{
		return left;
	}

	public Room getRight()
	{
		return right;
	}

	public boolean isCleared()
	{
		return cleared;
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

	public int id()
	{
		return id;
	}

	public void addLevelObject(LevelObject o)
	{
		objects.add(o);
	}

	@Override
	public void draw(Graphics g)
	{
		for (LevelObject o : objects)
		{
			o.draw(g);
		}
		for (DamageSource ds : damageSources)
		{
			ds.draw(g);
		}
		for (Player p : players)
		{
			p.draw(g);
		}
	}

	public boolean hasSpaceFor(LevelObject n)
	{
		for (LevelObject o : objects)
		{
			if (o.x() >= n.x() + n.width() || o.x() + o.width() <= n.x()
					|| o.y() >= n.y() + n.height()
					|| o.y() + o.height() <= n.y())
			{
				continue;
			}
			else
			{
				System.out.println("false");
				return false;
			}
		}

		return true;
	}
}