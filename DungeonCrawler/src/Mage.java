import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Mage extends Player {
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int) getPos().getX(), (int) getPos().getY(), 32, 32);
	}

	@Override
	public void attack(Point p, Room r)
	{
		super.attack(p, r);
		
	}

	@Override
	public void ability1(Point p, Room r)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ability2(Point p, Room r)
	{
		// TODO Auto-generated method stub
		
	}
}