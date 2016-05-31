import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Hunter extends Player {
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) getPos().getX(), (int) getPos().getY(), 32, 32);
	}

	@Override
	public void attack(Point p)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ability1(Point p)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ability2(Point p)
	{
		// TODO Auto-generated method stub
		
	}
}