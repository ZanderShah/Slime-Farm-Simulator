import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Tank extends Player {
	
	public Tank() {
		super();
		setStats(new Stats(100, 60, 40, 1.0, 20.0));
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, getWidth(), getHeight());
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