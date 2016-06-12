package enemy;

import java.awt.Color;
import java.awt.Graphics;

import engine.LivingEntity;
import utility.Vector2D;
import world.Room;

public abstract class Enemy extends LivingEntity {

	public Enemy() {
		super();
	}

	@Override
	public void update(Room l) {
		super.update(l);
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