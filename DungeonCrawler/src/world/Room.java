package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import player.Player;
import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

import enemy.Enemy;
import app.Test;
import engine.AABB;
import engine.DamageSource;
import engine.Projectile;

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
	private LevelObject[] doors;
	private ArrayList<DamageSource> damageSources;
	private ArrayList<Player> players;
	private BufferedImage nonMovingStuff;
	private ArrayList<Enemy> enemies;

	public Room(int x, int y, int width, int height, int difficulty, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.difficulty = difficulty;
		this.id = id;

		cleared = currentRoom = false;
		up = down = left = right = null;
		objects = new ArrayList<LevelObject>();
		doors = new LevelObject[5];
		damageSources = new ArrayList<DamageSource>();
		players = new ArrayList<Player>();
		enemies = new ArrayList<Enemy>();
	}

	public void update() {
		for (int i = 0; i < damageSources.size(); i++) {
			DamageSource d = damageSources.get(i);
			d.update(this);
			if (d.getDuration() == 0) {
				damageSources.remove(i);
				i--;
			} else {
				boolean destroyed = false;
				if (d instanceof Projectile) {
					for (int l = 0; l < objects.size(); l++) {
						if (objects.get(l).blocksPlayer() && objects.get(l).hitbox().intersects(d.getHitbox())) {
							damageSources.remove(i);
							i--;
							destroyed = true;
						}
					}
				}
				if (!destroyed && d.getHitCounter() == 0) {
					if (d.isPlayer()) {
						for (int e = 0; e < enemies.size(); e++) {
							if (enemies.get(e).getHitbox().intersects(d.getHitbox())) {
								enemies.get(e).damage(d.getDamage());
								if (d.isSingleHit()) {
									damageSources.remove(i);
									i--;
								}
							}
						}
					} else {
						for (int p = 0; p < players.size(); p++) {
							if (players.get(p).getHitbox().intersects(d.getHitbox())) {
								players.get(p).damage(d.getDamage());
								if (d.isSingleHit()) {
									damageSources.remove(i);
									i--;
								}
							}
						}
					}
				}
			}
		}
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public void addDamageSource(DamageSource ds) {
		damageSources.add(ds);
	}

	public void setUp(Room up) {
		this.up = up;
	}

	public void setDown(Room down) {
		this.down = down;
	}

	public void setLeft(Room left) {
		this.left = left;
	}

	public void setRight(Room right) {
		this.right = right;
	}

	public Room getUp() {
		return up;
	}

	public Room getDown() {
		return down;
	}

	public Room getLeft() {
		return left;
	}

	public Room getRight() {
		return right;
	}

	public boolean isCleared() {
		return cleared;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public int id() {
		return id;
	}

	public void clean() {
		damageSources = new ArrayList<DamageSource>();
		players = new ArrayList<Player>();
	}

	public boolean isCurrent() {
		return currentRoom;
	}

	public void setCurrent() {
		currentRoom = true;
	}

	public void addLevelObject(LevelObject o) {
		objects.add(o);
	}

	public LevelObject getDoor(int index) {
		return doors[index];
	}

	public void setDoor(LevelObject o, int index) {
		doors[index] = o;
	}

	public int randomX(Image img) {
		return (int) (Math.random() * (width - 1) * 64) + 64;
	}

	public int randomY(Image img) {
		return (int) (Math.random() * (height - 1) * 64) + 64;
	}

	public Room moveTo(Room r, int direction) {
		Vector2D newPos;
		if (direction == Constants.LEFT)
			newPos = new Vector2D(30, 30);
		else if (direction == Constants.RIGHT)
			newPos = new Vector2D(30, 30);
		else if (direction == Constants.UP)
			newPos = new Vector2D(30, 30);
		else
			newPos = new Vector2D(30, 30);

		for (int i = 0; i < players.size(); i++)
		{
			r.addPlayer(players.get(i));
			players.get(i).update(r);
			players.get(i).setPos(newPos);
		}

		for (int i = 0; i < damageSources.size(); i++)
		{
			damageSources.get(i).update(r);
			r.addDamageSource(damageSources.get(i));
		}

		currentRoom = false;
		r.setCurrent();
		clean();

		return r;
	}
	
	public void stopTearing2017()
	{
		nonMovingStuff = new BufferedImage(width * 64, height * 64, BufferedImage.TYPE_INT_RGB);
		
//		for (int i = 0; i < width; i++)
//			for (int j = 0; j < height; j++)
//				g.drawImage(SpriteSheet.FLOORS[difficulty], i * 64
//						+ (int) offset.getX(), j * 64
//						+ (int) offset.getY(),
//						null);
//
//		for (int i = 0; i < objects.size(); i++)
//			if (true || isRelevant(objects.get(i), p))
//			{
//				objects.get(i).draw(g, offset);
//			}
//
//		for (int i = 1; i < doors.length; i++)
//			if (doors[i] != null)
//				doors[i].draw(g, offset);
	}

	public void draw(Graphics g, Vector2D offset) {
		if (currentRoom)
			g.setColor(Color.RED);
		else
			g.setColor(Color.GRAY);

		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);

		if (getUp() != null) {
			g.fillOval(x + width / 2 - 4, y - 4, 8, 8);
		}
		if (getDown() != null) {
			g.fillOval(x + width / 2 - 4, y + height - 4, 8, 8);
		}
		if (getLeft() != null) {
			g.fillOval(x - 4, y + height / 2 - 4, 8, 8);
		}
		if (getRight() != null) {
			g.fillOval(x + width - 4, y + height / 2 - 4, 8, 8);
		}
	}

	public void detailedDraw(Graphics g, Vector2D offset, Player p)
	{
//		for (int i = 0; i < width + 2; i++)
//			for (int j = 0; j < height + 2 ; j++)
//				g.drawImage(SpriteSheet.WALLS[difficulty], i * 64
//						+ (int) offset.getX(), j * 64
//						+ (int) offset.getY(),
//						null);

		g.drawImage(nonMovingStuff, 0, 0, null);

		for (int i = 0; i < damageSources.size(); i++) {
			damageSources.get(i).draw(g, offset);
		}

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) == p) {
				p.draw(g, Test.middle.subtract(p.getPos()));
			} else {
				players.get(i).draw(g, offset);
			}
		}
	}

	private boolean isRelevant(LevelObject o, Player p)
	{
		AABB test = new AABB(p.getPos(),
				(int) (Test.middle.getX()), (int) (Test.middle.getY()));
		return o.hitbox().intersects(test);
	}

	public int atDoor(Player p)
	{
		for (int i = 1; i < doors.length; i++)
		{
			if (doors[i] != null && p.getHitbox().intersects(doors[i].hitbox()))
			{
				return i;
			}
		}
		return -1;
	}

	public boolean hasCollisionWith(AABB hitbox) {
		// Outside of map
		if ((hitbox.getPosition().getX() - hitbox.getWidth() / 2) * (hitbox.getPosition().getY() - hitbox.getHeight() / 2) < 0
				|| (hitbox.getPosition().getX() + hitbox.getWidth() / 2) > width * 64 || (hitbox.getPosition().getY() + hitbox.getHeight() / 2) > height * 64) {
			return true;
		}

		for (LevelObject l : objects)
			if (l.blocksPlayer() && l.hitbox().intersects(hitbox))
				return true;

		return false;
	}

	public boolean hasSpaceFor(LevelObject n) {
		if (n.x() < 64 || n.x() + n.width() > (width - 1) * 64 || n.y() < 64 || n.y() + n.height() > (height - 1) * 64)
			return false;

		for (LevelObject o : objects)
			if (n.hitbox().intersects(o.hitbox()))
				return false;
		for (int i = 1; i < doors.length; i++)
			if (doors[i] != null && n.hitbox().intersects(doors[i].hitbox()))
				return false;

		return true;
	}
}
