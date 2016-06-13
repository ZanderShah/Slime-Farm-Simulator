package enemy;

import java.awt.Graphics;

import engine.AABB;
import engine.Stats;
import engine.damage.MeleeEnemyDamageSource;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public class Slime extends Enemy {
	private MeleeEnemyDamageSource slimeDamageSource;
	
	private int movementCounter;
	private Vector2D slideDir;

	public Slime(int x, int y) {
		super();

		setStats(new Stats(100, 100, 100, 100, 10.0));
		setHitbox(new AABB(getPos().add(
				new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(),
				getHeight()));
		setPos(new Vector2D(x, y));
		movementCounter = (int) (Math.random() * 150);
		slideDir = new Vector2D();
	}

	public void addDamage(Room r) {
		setDamageSource(new MeleeEnemyDamageSource(getHitbox(), 10, 15));
		super.addDamage(r);
	}

	@Override
	public void update(Room l) {
		if (getStats().getHealth() <= 0) {
			l.removeEnemy(this);
			l.removeDamageSource(slimeDamageSource);
		}
		
		if (movementCounter == 0) {
			movementCounter = (int) (Math.random() * 60 + 150);
			setSpeed(new Vector2D());
		} else {
			movementCounter--;
			if (movementCounter == 30) {
				slideDir = new Vector2D((int) (Math.random() * 360));
				slideDir.multiplyBy(2);
			}
			if (movementCounter < 30) {
				setSpeed(slideDir);
			}
		}

		slimeDamageSource.setHitbox(getHitbox());
		
		super.update(l);
	}

	@Override
	public int getWidth() {
		return SpriteSheet.ENEMIES[0].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.ENEMIES[0].getHeight(null);
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.random(SpriteSheet.ENEMIES), (int) shifted.getX()
				- getWidth()
						/ 2,
				(int) shifted.getY() - getHeight() / 2, null);

		drawHealth(g, offset);
	}

}
