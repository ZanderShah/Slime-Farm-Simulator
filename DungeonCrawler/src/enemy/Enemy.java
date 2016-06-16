package enemy;

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
			l.removeDamageSource(getID());
			l.dropExperience((l.getDifficulty() + 1) * (l.getDifficulty() + 1) * 3);
			onDeath(l);
		}
		else {
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
}
