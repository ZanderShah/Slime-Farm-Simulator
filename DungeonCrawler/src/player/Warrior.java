package player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import engine.Stats;
import engine.SwordDamageSource;
import utility.Constants;
import utility.ControlState;
import world.Room;

public class Warrior extends Player {
	private static final int SIZE = 32;
	
	public Warrior(){
		super();
		setStats(new Stats(Constants.WARRIOR_HEALTH, Constants.WARRIOR_ATTACK_SPEED, Constants.WARRIOR_ATTACK_LENGTH, Constants.WARRIOR_SPEED, Constants.WARRIOR_DEFENSE));
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
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
		if (attacked) r.addDamageSource(new SwordDamageSource(getPos(), Constants.WARRIOR_SWORD_SIZE, (int) getAttackDir().getAngle() - Constants.WARRIOR_SWING_ANGLE / 2, Constants.WARRIOR_SWING_ANGLE, getStats().getAttackTime(), true, Constants.WARRIOR_DAMAGE));
		return attacked;
	}
	
	// Spin attack ability: Do a giant spinning sword attack, hitting everything around you
	// Cooldown: 5 seconds
	@Override
	public void ability1(Point p, Room r) {
		if (getAttacking() == 0 && getCooldown(1) == 0) {
			setAttacking(100);
			setCooldown(1, Constants.WARRIOR_AB1_COOLDOWN);
			r.addDamageSource(new SwordDamageSource(getPos(), (int) (Constants.WARRIOR_SWORD_SIZE * 1.5), 0, 360, getStats().getAttackTime(), true, Constants.WARRIOR_DAMAGE));
		}
	}

	@Override
	public void ability2(Point p, Room r) {
		
	}
	
	@Override
	public void ability3(Point p, Room r) {
		
	}
}