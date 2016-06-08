package player;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import javax.imageio.ImageIO;

import engine.Arrow;
import engine.Stats;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.Room;

public class Hunter extends Player {
	
	public Hunter() {
		super();
		setStats(new Stats(Constants.HUNTER_HEALTH, Constants.HUNTER_ATTACK_SPEED, Constants.HUNTER_ATTACK_LENGTH, Constants.HUNTER_SPEED,
				Constants.HUNTER_DEFENCE));
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		g.drawImage(SpriteSheet.HUNTER_IMAGES[0], (int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, null);
		
		// g.setColor(Color.GREEN.darker().darker());
		// g.fillRect((int) getPos().getX() - getWidth() / 2, (int)
		// getPos().getY() - getHeight() / 2, getWidth(), getHeight());
	}

	@Override
	public int getWidth() {
		return SpriteSheet.HUNTER_IMAGES[0].getWidth(null);
	}

	@Override
	public int getHeight() {
		return SpriteSheet.HUNTER_IMAGES[0].getHeight(null);
	}

	@Override
	public void update(ControlState cs, Room r) {
		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r) {
		boolean attacked = super.attack(p, r);
		if (attacked) {
			r.addDamageSource(new Arrow(getPos().clone(), (new Vector2D(p)).subtract(getPos()), false));
		}
		return attacked;
	}

	@Override
	public void ability1(Point p, Room r) {

	}

	@Override
	public void ability2(Point p, Room r) {

	}

	@Override
	public void ability3(Point p, Room r) {
		// TODO Auto-generated method stub

	}
}