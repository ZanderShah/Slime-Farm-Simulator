package player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import engine.Fireball;
import engine.Stats;
import utility.Constants;
import utility.ControlState;
import utility.Vector2D;
import world.Room;

public class Mage extends Player {
	private static final int SIZE = 32;
	
	public Mage() {
		super();
		setStats(new Stats(Constants.MAGE_HEALTH, Constants.MAGE_ATTACK_SPEED, Constants.MAGE_ATTACK_LENGTH, Constants.MAGE_SPEED, Constants.MAGE_DEFENCE));
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, getWidth(), getHeight());
	}
	
	@Override
	public int getWidth() {
		return SIZE;
	}
	
	@Override
	public int getHeight() {
		return SIZE;
	}
	
	@Override
	public void update(ControlState cs, Room r) {
		super.update(cs, r);
	}
	
	@Override
	public boolean attack(Point p, Room r) {
		boolean attacked = super.attack(p, r);
		if (attacked) {
			r.addDamageSource(new Fireball(getPos().clone(), (new Vector2D(getAttackDir().getAngle() + ((int) (Math.random() * (Constants.MAGE_SPRAY + 1)) - Constants.MAGE_SPRAY / 2))), true));
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