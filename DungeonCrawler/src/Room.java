import java.awt.Graphics;
import java.util.ArrayList;

public class Room implements Drawable {
	private int x, y, width, height, id;
	private boolean cleared;
	private Room up, down, left, right;
	private ArrayList<LevelObject> objects;
	
	
	private ArrayList<Projectile> projectiles;
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
		
		projectiles = new ArrayList<Projectile>();
		players = new ArrayList<Player>();
	}
	
	public void update() {
		for (Projectile p : projectiles) {
			p.update(this);
		}
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public void fireProjectile(Projectile p) {
		projectiles.add(p);
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

	@Override
	public void draw(Graphics g) {
		for (int p = 0; p < players.size(); p++) {
			players.get(p).draw(g);
		}
		for (int p = 0; p < projectiles.size(); p++) {
			projectiles.get(p).draw(g);
		}
	}
}