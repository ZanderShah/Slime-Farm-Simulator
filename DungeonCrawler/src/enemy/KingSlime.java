package enemy;

import java.awt.Graphics;

import engine.AABB;
import engine.Stats;
import engine.damage.MeleeEnemyDamageSource;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public class KingSlime extends Enemy {
	private int movementCounter;
	private Vector2D slideDir;

	private int level;
	private int waitTime;
	private int slideTime;

	public KingSlime(int x, int y, int l) {
		super();

		setStats(new Stats(1000 / (int) (Math.pow(2, l)), 100, 100, 3 * Math.pow(1.2, l), 30.0));
		level = l;
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
		setPos(new Vector2D(x, y));
		movementCounter = (int) (Math.random() * 150);
		slideDir = new Vector2D();
		slideTime = (int) (60 / Math.pow(1.2, l));
		waitTime = 240;
	}

	@Override
	public void addDamage(Room r) {
		setDamageSource(new MeleeEnemyDamageSource(getHitbox(),
				50 / (int) (Math.pow(2, level)), 30));
		super.addDamage(r);
	}

	@Override
	public void onDeath(Room r) {
		if (level < 3) {
			KingSlime left = new KingSlime((int) getPos().getX(),
					(int) getPos().getY(), level + 1);
			r.addEnemy(left);
			left.addDamage(r);
			
			KingSlime right = new KingSlime((int) getPos().getX(),
					(int) getPos().getY(), level + 1);
			r.addEnemy(right);		
			right.addDamage(r);
			
			KingSlime up = new KingSlime((int) getPos().getX(),
					(int) getPos().getY(), level + 1);
			r.addEnemy(up);		
			up.addDamage(r);
			
			KingSlime down = new KingSlime((int) getPos().getX(),
					(int) getPos().getY(), level + 1);
			r.addEnemy(down);		
			down.addDamage(r);
		}
	}

	@Override
	public void update(Room l) {
		if (movementCounter == 0) {
			movementCounter = waitTime;
			setSpeed(new Vector2D());
		}
		else {
			movementCounter--;

			if (movementCounter == 60)
			{
				slideDir = EnemyAttackPatterns.runTowardsPlayer(l, getPos());
				slideDir.multiplyBy(getStats().getSpeed());
			}
			if (movementCounter < slideTime) {
				setSpeed(slideDir);
			}
		}

		super.update(l);
	}

	@Override
	public int getWidth() {
		return SpriteSheet.KING_SLIME[level].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.KING_SLIME[level].getHeight(null);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.KING_SLIME[level], (int) shifted.getX()
				- getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);

		drawHealth(g, offset);
	}

}
