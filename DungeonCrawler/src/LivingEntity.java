import java.awt.Graphics;
import java.util.ArrayList;

public abstract class LivingEntity implements Drawable
{
	private Vector2D position;
	private Vector2D speed;
	private Stats stats;
	private AABB hitbox;

	private ArrayList<StatusEffect> effects;

	public LivingEntity()
	{
		position = new Vector2D();
		speed = new Vector2D();
		effects = new ArrayList<StatusEffect>();
		stats = new Stats(0, 0, 0, 0, 0);
		hitbox = new AABB(position.add(new Vector2D(16, 16)), 32, 32);
	}

	public Vector2D getPos()
	{
		return position;
	}

	public Vector2D getSpeed()
	{
		return speed;
	}

	public void setPos(Vector2D v)
	{
		position = v.clone();
	}

	public void setSpeed(Vector2D v)
	{
		speed = v.clone();
	}

	public void setStats(Stats s)
	{
		stats = s;
	}

	public Stats getStats()
	{
		return stats;
	}

	public ArrayList<StatusEffect> getEffects()
	{
		return effects;
	}

	public void giveStatusEffect(StatusEffect s)
	{
		effects.add(s);
	}

	public int getWidth()
	{
		return 0;
	}

	public int getHeight()
	{
		return 0;
	}

	public void update(Room l)
	{
		AABB temp = hitbox;
		temp.updatePosition(position.add(speed));
		if (!l.hasCollisionWith(temp))
			position.addToThis(speed);
	}

	@Override
	public abstract void draw(Graphics g);
}