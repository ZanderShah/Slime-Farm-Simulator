import java.awt.Color;
import java.awt.Graphics;

public class Tank extends Player {
	
	@Override
	public double getMaxSpeed() {
		return 1;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) getPos().getX(), (int) getPos().getY(), 32, 32);
	}

	@Override
	public void attack() {
		
	}

	@Override
	public void ability1() {
		
	}

	@Override
	public void ability2() {
		
	}
}