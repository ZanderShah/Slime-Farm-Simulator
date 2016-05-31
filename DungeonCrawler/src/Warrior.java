import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Warrior extends Player {
	private static final int SIZE = 32;
	private static final int SWORD_SIZE = 96;
	private static final int SWORD_SWING = 120;
	private static final int SWORD_TIME = 40;
	
	private int attacking;
	private Vector2D attackDirection;
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int) getPos().getX(), (int) getPos().getY(), SIZE, SIZE);
		
		g.setColor(Color.LIGHT_GRAY);
		if (attacking != 0) {
			g.fillArc((int) getPos().getX() + getWidth() / 2 - SWORD_SIZE / 2, (int) getPos().getY() + getHeight() / 2 - SWORD_SIZE / 2, SWORD_SIZE, SWORD_SIZE,
					(int) (attackDirection.getAngle() - SWORD_SWING / 2), SWORD_SWING);
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
		if (attacking > 0) attacking--;
	}

	@Override
	public void attack(Point p) {
		attacking = SWORD_TIME;
		Vector2D direction = (new Vector2D(p)).subtract(getPos().add(new Vector2D(getWidth() / 2, getHeight() / 2)));
		direction.normalize();
		attackDirection = direction;
	}

	@Override
	public void ability1(Point p) {
		
	}

	@Override
	public void ability2(Point p) {
		
	}
}