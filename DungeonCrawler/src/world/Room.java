package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import enemy.Enemy;
import engine.AABB;
import engine.Particle;
import engine.ParticleEmitter;
import engine.damage.DamageSource;
import engine.damage.Projectile;
import player.Player;
import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;

public class Room // implements Drawable (There should be 2 Drawable, one with
// offset and one without
{
	// x y are used only for position relative to other rooms, when dealing with
	// the room individually,
	// bottom left is (0, 0) and top right is (width, height)
	private int x, y, width, height, difficulty, id;
	private boolean currentRoom, bossRoom;
	private Room up, down, left, right, nextLevel;
	private ArrayList<LevelObject> objects;
	private LevelObject[] doors;
	private ArrayList<DamageSource> damageSources;
	private ArrayList<Player> players;
	private BufferedImage nonMovingStuff;
	private LevelObject nonMovingStuffLevelObject;
	private ArrayList<Enemy> enemies;

	private ArrayList<Particle> particles;
	private ArrayList<ParticleEmitter> emitters;

	public Room(int x, int y, int width, int height, int difficulty, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.difficulty = difficulty;
		this.id = id;

		currentRoom = bossRoom = false;
		up = down = left = right = nextLevel = null;
		objects = new ArrayList<LevelObject>();
		doors = new LevelObject[5];
		damageSources = new ArrayList<DamageSource>();
		players = new ArrayList<Player>();
		enemies = new ArrayList<Enemy>();
		emitters = new ArrayList<ParticleEmitter>();
		particles = new ArrayList<Particle>();
	}

	public void update() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(this);
		}

		for (int i = 0; i < emitters.size(); i++) {
			emitters.get(i).update(this);
			if (emitters.get(i).isDead()) {
				emitters.remove(i);
				i--;
			}
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
			if (particles.get(i).isDead()) {
				particles.remove(i);
				i--;
			}
		}

		for (int i = 0; i < damageSources.size(); i++) {
			DamageSource d = damageSources.get(i);
			d.update(this);
			if (d.getDuration() == 0) {
				damageSources.remove(i);
				i--;
			}
			else {
				boolean destroyed = false;
				if (d instanceof Projectile) {
					for (int l = 0; l < objects.size(); l++) {
						if (objects.get(l).blocksPlayer()
								&& objects.get(l).hitbox()
										.intersects(d.getHitbox())
								&& i >= 0) {
							damageSources.remove(i);
							i--;
							destroyed = true;
						}
					}
				}
				if (!destroyed && d.getHitCounter() == 0) {
					if (d.isPlayer()) {
						for (int e = 0; e < enemies.size(); e++) {
							if (d.hit(enemies.get(e)) && d.isSingleHit()
									&& i >= 0) {
								damageSources.remove(i);
								i--;
							}
						}
					}
					else {
						for (int p = 0; p < players.size(); p++) {
							if (d.hit(players.get(p)) && d.isSingleHit()
									&& i >= 0) {
								damageSources.remove(i);
								i--;
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

	public void addDamageSource(DamageSource ds, double damageMultiplier) {
		ds.setDamage(ds.getDamage() * damageMultiplier);
		damageSources.add(ds);
	}

	public void removeDamageSource(long id) {
		for (int i = 0; i < damageSources.size(); i++)
			if (damageSources.get(i).getSourceID() == id) {
				damageSources.remove(i);
				i--;
			}
	}

	public void addParticle(Particle p) {
		particles.add(p);
	}

	public void addEmitter(ParticleEmitter e) {
		emitters.add(e);
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
		return enemies.size() == 0;
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

	public int getDifficulty() {
		return difficulty;
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

	public boolean isBossRoom() {
		return bossRoom;
	}

	public void setBossRoom(Room nextLevel) {
		bossRoom = true;
		this.nextLevel = nextLevel;
	}

	public Room getNextLevel() {
		return nextLevel;
	}

	public void addLevelObject(LevelObject o) {
		objects.add(o);
	}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public void removeEnemy(Enemy e) {
		enemies.remove(e);
	}

	public LevelObject getDoor(int index) {
		return doors[index];
	}

	public void setDoor(LevelObject o, int index) {
		doors[index] = o;
	}

	public int randomX(Image img, Random rng) {
		return Math.max((int) (rng.nextDouble()
				* ((width - 2) * 64 - img.getWidth(null))) + 128, 0);
	}

	public int randomY(Image img, Random rng) {
		return Math.max((int) (rng.nextDouble()
				* ((height - 2) * 64 - img.getHeight(null))) + 128, 0);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<DamageSource> getDamageSources() {
		return damageSources;
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

		for (int i = 0; i < players.size(); i++) {
			r.addPlayer(players.get(i));
			players.get(i).update(r);
			players.get(i).setPos(newPos);
		}

		for (int i = 0; i < damageSources.size(); i++) {
			damageSources.get(i).update(r);
			r.addDamageSource(damageSources.get(i), 1);
		}

		currentRoom = false;
		r.setCurrent();
		clean();

		return r;
	}

	public void dropExperience(int num) {
		for (int i = 0; i < players.size(); i++)
			players.get(i).addExperience(num);
	}

	public void stopTearing2017() {
		nonMovingStuff = new BufferedImage(width * 64, height * 64,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = nonMovingStuff.getGraphics();

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				g.drawImage(SpriteSheet.FLOORS[difficulty], i * 64, j * 64,
						null);

		for (int i = 0; i < objects.size(); i++) {
			g.drawImage(objects.get(i).image(), objects.get(i).x(), objects
					.get(i).y(), null);
		}

		nonMovingStuffLevelObject = new LevelObject(new Vector2D(0, 0), false,
				false, nonMovingStuff);
	}

	public void draw(Graphics g, Vector2D offset) {
		if (bossRoom && !isCleared())
			g.setColor(Color.RED);
		else if (currentRoom)
			g.setColor(Color.GREEN);
		else if (isCleared())
			g.setColor(Color.GRAY.brighter());
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

	public void detailedDraw(Graphics g, Vector2D offset, Player p) {
		if (nonMovingStuffLevelObject == null)
			stopTearing2017();
		nonMovingStuffLevelObject.draw(g, offset);

		for (int i = 0; i < damageSources.size(); i++) {
			if (i < damageSources.size() && damageSources.get(i) != null) {
				damageSources.get(i).draw(g, offset);
			}
		}

		if (isCleared())
			for (int i = 1; i < doors.length; i++)
				if (doors[i] != null)
					doors[i].draw(g, offset);

		for (int i = 0; i < particles.size(); i++) {
			if (i < particles.size() && particles.get(i) != null) {
				try {
					particles.get(i).draw(g, offset);
				}
				catch (Exception e) {

				}
			}
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g, offset);
		}

		for (int i = 0; i < players.size(); i++) {
			if (i >= 0 && i < players.size()) { // this shouldn't be necessary
												// but it keeps saying that it's
												// out
												// of bounds with index: 0,
												// size: 1
				if (p != null && players.get(i) != null
						&& players.get(i).getID() == p.getID()) {
					p.draw(g, Constants.MIDDLE.subtract(p.getPos()));
				}
				else {
					if (players.get(i) != null) {
						players.get(i).draw(g, offset);
					}
				}
			}
		}
	}

	public int atDoor(Player p) {
		if (!isCleared() || p == null)
			return -1;

		for (int i = 1; i < doors.length; i++) {
			if (doors[i] != null
					&& p.getHitbox().intersects(doors[i].hitbox())) {
				return i;
			}
		}
		return -1;
	}

	public boolean hasCollisionWith(AABB hitbox) {
		// Outside of map
		if ((hitbox.getPosition().getX() - hitbox.getWidth() / 2)
				* (hitbox.getPosition().getY() - hitbox.getHeight() / 2) < 0
				|| (hitbox.getPosition().getX() + hitbox.getWidth() / 2) > width
						* 64
				|| (hitbox.getPosition().getY()
						+ hitbox.getHeight() / 2) > height * 64) {
			return true;
		}

		for (LevelObject l : objects)
			if (l.blocksPlayer() && l.hitbox().intersects(hitbox))
				return true;

		return false;
	}

	public boolean hasSpaceFor(AABB n, boolean onTop) {
		if (n.getPosition().getX() < 64
				|| n.getPosition().getX() + n.getWidth() > (width - 1) * 64
				|| n.getPosition().getY() < 64
				|| n.getPosition().getY() + n.getHeight() > (height - 1) * 64)
			return false;

		for (LevelObject o : objects)
			if (n.intersects(o.hitbox()) && (!onTop || o.blocksPlayer()))
				return false;

		for (int i = 1; i < doors.length; i++)
			if (doors[i] != null && n.intersects(doors[i].hitbox()))
				return false;

		return true;
	}
}
