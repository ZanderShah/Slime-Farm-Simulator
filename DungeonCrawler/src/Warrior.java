import java.awt.Color;
import java.awt.Graphics;

public class Warrior extends Player {
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
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