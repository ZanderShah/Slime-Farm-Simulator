import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Mage extends Player {
	private static final int SIZE = 32;
	
	public Mage() {
		super();
		setStats(new Stats(100, 2, 1, 2.0, 20.0));
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
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
			r.addDamageSource(new Fireball(getPos().clone(), (new Vector2D(getAttackDir().getAngle() + ((int) (Math.random() * 21) - 10))), true));
		}
		return attacked;
	}
	
	@Override
	public void ability1(Point p, Room r) {
		
	}

	@Override
	public void ability2(Point p, Room r) {
		
	}
}