package enemy;

import java.awt.Color;
import java.awt.Graphics;

import utility.Vector2D;
import world.Room;
import engine.LivingEntity;
import engine.damage.MeleeEnemyDamageSource;

public abstract class Enemy extends LivingEntity {

	private MeleeEnemyDamageSource ds;

	public Enemy() {
		super();
	}

	@Override
	public void update(Room l) {
		if (getStats().getHealth() <= 0) {
			l.removeEnemy(this);
			l.removeDamageSource(ds);
			l.dropExperience((l.getDifficulty() + 1) * (l.getDifficulty() + 1)
					* 5);
			onDeath(l);
		}
		else
		{
			ds.setHitbox(getHitbox());
			super.update(l);
		}
	}

	public void addDamage(Room r) {
		r.addDamageSource(ds, 1);
	}

	public void setDamageSource(MeleeEnemyDamageSource d) {
		ds = d;
	}

	public MeleeEnemyDamageSource getDamageSource() {
		return ds;
	}

	@Override
	public abstract void draw(Graphics g, Vector2D offset);

	public void drawHealth(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);

		g.setColor(Color.GRAY);
		g.fillRect((int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
				- getHeight() / 2 - 10, getWidth(), 5);

		g.setColor(Color.RED);
		g.fillRect((int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
				- getHeight() / 2 - 10,
				(int) (getStats()
						.getHealth() / getStats().getMaxHealth() * getWidth()),
				5);
	}
}
