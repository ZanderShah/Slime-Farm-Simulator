import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Hunter extends Player {
	private static final int SIZE = 32;
	
	public Hunter() {
		super();
		setStats(new Stats(100, 60, 2.0, 20.0));
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN.darker().darker());
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
	public void attack(Point p, Room r) {
		if (getAttackCooldown() == 0) {
			r.fireProjectile(new Arrow(getPos().clone(), (new Vector2D(p)).subtract(getPos())));
		}
		super.attack(p, r);
	}
	
	@Override
	public void ability1(Point p, Room r) {
		
	}

	@Override
	public void ability2(Point p, Room r) {
		
	}
}