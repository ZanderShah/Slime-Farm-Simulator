import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Thief extends Player {
	private static final int SIZE = 32;
	private static final int SWORD_SIZE = 64;
	private static final int SWORD_SWING = 30;
	
	public Thief() {
		super();
		setStats(new Stats(100, 30, 10, 3.0, 20.0));
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, getWidth(), getHeight());
		
		g.setColor(Color.LIGHT_GRAY);
		if (getAttacking() > 0) {
			g.fillArc((int) getPos().getX() - SWORD_SIZE / 2, (int) getPos().getY() - SWORD_SIZE / 2, SWORD_SIZE, SWORD_SIZE,
					(int) (getAttackDir().getAngle() - SWORD_SWING / 2), SWORD_SWING);
		}
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
		return super.attack(p, r);
	}
	
	@Override
	public void ability1(Point p, Room r) {
		
	}

	@Override
	public void ability2(Point p, Room r) {
		
	}
}