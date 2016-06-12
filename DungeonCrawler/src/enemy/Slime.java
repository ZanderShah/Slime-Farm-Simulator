package enemy;

import java.awt.Graphics;

import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import engine.AABB;
import engine.ParticleEmitter;
import engine.Stats;
import engine.damage.CircleDamageSource;
import engine.damage.FireCircle;

public class Slime extends Enemy
{
	public Slime(int x, int y)
	{
		super();

		setStats(new Stats(100, 100, 100, 100, 10.0));
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
		setPos(new Vector2D(x, y));
	}

	@Override
	public void update(Room l)
	{
		super.update(l);

		if (getStats().getHealth() <= 0)
			l.removeEnemy(this);

		l.addDamageSource(new FireCircle(getPos(), Constants.MAGE_FIRE_RANGE,
				30, Constants.MAGE_FIRE_LENGTH - 265, false, 10));

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
