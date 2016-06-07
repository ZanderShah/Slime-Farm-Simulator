package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import utility.Constants;
import utility.ControlState;
import utility.Vector2D;
import world.Room;
import engine.Stats;

public class Cleric extends Player
{
	private static final int SIZE = 32;

	public Cleric()
	{
		super();
		setStats(new Stats(Constants.CLERIC_HEALTH,
				Constants.CLERIC_ATTACK_SPEED, Constants.CLERIC_ATTACK_LENGTH,
				Constants.CLERIC_SPEED, Constants.CLERIC_DEFENCE));
	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(Color.BLUE.brighter().brighter());
		g.fillRect((int) getPos().getX() - getWidth() / 2,
				(int) getPos().getY() - getHeight() / 2, getWidth(),
				getHeight());
	}

	@Override
	public int getWidth()
	{
		return SIZE;
	}

	@Override
	public int getHeight()
	{
		return SIZE;
	}

	@Override
	public void update(ControlState cs, Room r)
	{
		super.update(cs, r);
	}

	@Override
	public boolean attack(Point p, Room r)
	{
		return super.attack(p, r);
	}

	@Override
	public void ability1(Point p, Room r)
	{

	}

	@Override
	public void ability2(Point p, Room r)
	{

	}

	@Override
	public void ability3(Point p, Room r)
	{

	}
}