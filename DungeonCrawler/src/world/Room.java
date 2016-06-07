package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import player.Player;
import utility.SpriteSheet;
import utility.Vector2D;
import app.Test;
import engine.AABB;
import engine.DamageSource;

public class Room // implements Drawable (There should be 2 Drawable, one with
// offset and one without
{
	// x y are used only for position relative to other rooms, when dealing with
	// the room individually,
	// bottom left is (0, 0) and top right is (width, height)
	private int x, y, width, height, difficulty, id;
	private boolean cleared, currentRoom;
	private Room up, down, left, right;
	private ArrayList<LevelObject> objects;
	private ArrayList<DamageSource> damageSources;
	// For now, players[0] will be the player that the user is controlling
	private ArrayList<Player> players;

	public Room(int x, int y, int width, int height, int difficulty, int id)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.difficulty = difficulty;
		this.id = id;

		cleared = currentRoom = false;
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
			if (damageSources.get(i).getDuration() == 0)
			{
				damageSources.remove(i);
				i--;
			}
			else
			{
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

	public boolean isCurrent()
	{
		return currentRoom;
	}

	public void setCurrent(boolean newState)
	{
		currentRoom = newState;
	}

	public void addLevelObject(LevelObject o)
	{
		objects.add(o);
	}

	public int randomX(Image img)
	{
		return (int) (Math.random() * (width - 1) * 64) + 64;
	}

	public int randomY(Image img)
	{
		return (int) (Math.random() * (height - 1) * 64) + 64;
	}

	public void draw(Graphics g)
	{
		if (currentRoom)
		{
			g.setColor(Color.CYAN);
			detailedDraw(g);
		}
		else
			g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}

	public void detailedDraw(Graphics g)
	{
		Vector2D offset = Test.middle.subtract(players
				.get(0)
				.getPos()
				.add(new Vector2D(players.get(0).getWidth() / 2, players.get(0)
						.getHeight() / 2)));
		
		Test.totalOffset.addToThis(offset);

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				g.drawImage(SpriteSheet.FLOORS[difficulty], i * 64
						+ (int) Test.totalOffset.getX(), j * 64
						+ (int) Test.totalOffset.getY(),
						null);

		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).setPos(objects.get(i).getPos().add(offset));
			objects.get(i).draw(g);
		}
		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).setPos(players.get(i).getPos().add(offset));
			players.get(i).draw(g);
		}
		for (int i = 0; i < damageSources.size(); i++)
		{
			damageSources.get(i).draw(g);
		}

	}

	public boolean hasCollisionWith(AABB hitbox)
	{
		for (LevelObject l : objects)
			if (l.blocksPlayer() && l.hitbox().intersects(hitbox))
				return true;

		return false;
	}

	public boolean hasSpaceFor(LevelObject n)
	{
		if (n.x() < 0 || n.x() + n.width() > width * 64 || n.y() < 0 || n.y() + n.height() > height * 64)
			return false;
		
		for (LevelObject o : objects)
			if (n.hitbox().intersects(o.hitbox()))
				return false;

		return true;
	}
}