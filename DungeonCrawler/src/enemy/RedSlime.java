package enemy;

import java.awt.Graphics;

import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import engine.AABB;
import engine.Stats;
import engine.damage.MeleeEnemyDamageSource;
import engine.damage.SlimeBall;

public class RedSlime extends Slime
{
	private int movementCounter;
	private Vector2D slideDir;

	public RedSlime(int x, int y)
	{
		super(x, y);

		setStats(new Stats(100, 100, 100, 100, 10.0));
		movementCounter = (int) (Math.random() * 150);
		slideDir = new Vector2D();
	}

	public void addDamage(Room r)
	{
		setDamageSource(new MeleeEnemyDamageSource(getHitbox(), 10, 15, getID()));
		super.addDamage(r);
	}

	@Override
	public void update(Room l)
	{
		if (movementCounter == 0)
		{
			movementCounter = (int) (Math.random() * 150 + 250);
			setSpeed(new Vector2D());
		}
		else
		{
			movementCounter--;
			if (movementCounter == 30)
			{
				slideDir = EnemyAttackPatterns.random();
				slideDir.multiplyBy(2);
				l.addDamageSource(
						new SlimeBall(getPos(), EnemyAttackPatterns
								.runTowardsPlayer(l, getPos()), 1, getID()), 1);
			}
			if (movementCounter < 30)
			{
				setSpeed(slideDir);
			}
		}

		super.update(l);
	}

	@Override
	public int getWidth()
	{
		return SpriteSheet.ENEMIES[0].getWidth(null);
	}

	@Override
	public int getHeight()
	{
		return SpriteSheet.ENEMIES[0].getHeight(null);
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.ENEMIES[2], (int) shifted.getX()
				- getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);

		drawHealth(g, offset);
	}

}
