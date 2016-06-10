package player;

import java.awt.Graphics;
import java.awt.Point;

import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;
import engine.AABB;
import engine.Stats;

public class Cleric extends Player {
	public Cleric() {
		super();
		setStats(new Stats(Constants.CLERIC_HEALTH, Constants.CLERIC_ATTACK_SPEED, Constants.CLERIC_ATTACK_LENGTH, Constants.CLERIC_SPEED,
				Constants.CLERIC_DEFENCE));
		setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(), getHeight()));
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.CLERIC_IMAGES[getDirection()], (int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);

		// g.setColor(Color.BLUE.brighter().brighter());
		// g.fillRect((int) shifted.getX() - getWidth() / 2,
		// (int) shifted.getY() - getHeight() / 2, getWidth(),
		// getHeight());
	}

	@Override
	public int getWidth() {
		return SpriteSheet.CLERIC_IMAGES[getDirection()].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.CLERIC_IMAGES[0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r) {
		setHitbox(new AABB(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)), getWidth(), getHeight()));
		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r) {
		return super.attack(p, r);
	}

	@Override
	public void ability1(Point p, Room r) {

	}

	@Override
	public void ability2(Point p, Room r) {

	}

	@Override
	public void ability3(Point p, Room r) {

	}
}