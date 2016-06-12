package enemy;

import java.awt.Graphics;

import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import engine.AABB;
import engine.Stats;
import engine.damage.MeleeEnemyDamageSource;

public class Slime extends Enemy
{
	private MeleeEnemyDamageSource slimeDamageSource;

	public Slime(int x, int y)
	{
		super();

		setStats(new Stats(100, 100, 100, 100, 10.0));
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
		setPos(new Vector2D(x, y));
	}

	public void addDamage(Room r)
	{
		slimeDamageSource = new MeleeEnemyDamageSource(getHitbox());
		r.addDamageSource(slimeDamageSource);
	}

	@Override
	public void update(Room l)
	{
		super.update(l);

		if (getStats().getHealth() <= 0)
		{
			l.removeEnemy(this);
			l.removeDamageSource(slimeDamageSource);
		}

		slimeDamageSource.setHitbox(getHitbox());
	}

	@Override
	public int getWidth()
	{
		return SpriteSheet.ENEMIES[0].getWidth(null);
	}

	@Override
	public int getHeight()
	{
		return SpriteSheet.ENEMIES[0].getWidth(null);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.ENEMIES[0], (int) shifted.getX()
				- getWidth()
				/ 2, (int) shifted.getY() - getHeight() / 2, null);

		drawHealth(g, offset);
	}

}
