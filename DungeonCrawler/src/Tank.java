import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Tank extends Player {
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) getPos().getX(), (int) getPos().getY(), 32, 32);
	}

	@Override
	public void attack(Point p) {
		
	}

	@Override
	public void ability1(Point p) {
		
	}

	@Override
	public void ability2(Point p) {
		
	}
}