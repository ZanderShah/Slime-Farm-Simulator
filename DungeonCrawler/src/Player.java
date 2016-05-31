import java.awt.Graphics;
import java.awt.Point;

public abstract class Player extends LivingEntity {
	
	public Player() {
		super();
		setStats(new Stats(100, 3, 0));
	}

	public void update(ControlState cs, Room l) {
		Vector2D speed = new Vector2D();
		
		if (cs.getPressed(ControlState.KEY_UP)) {
			speed.addToThis(new Vector2D(0, -1));
		}
		if (cs.getPressed(ControlState.KEY_LEFT)) {
			speed.addToThis(new Vector2D(-1, 0));
		}
		if (cs.getPressed(ControlState.KEY_DOWN)) {
			speed.addToThis(new Vector2D(0, 1));
		}
		if (cs.getPressed(ControlState.KEY_RIGHT)) {
			speed.addToThis(new Vector2D(1, 0));
		}
		if (cs.getPressed(ControlState.KEY_ATTACK)) {
			attack(cs.getMouse());
			// also send attack to server
		}
		
		speed.normalize();
		speed.multiplyBy(getStats().getSpeed()); // Adjusted based on which class it is
		
		setSpeed(speed);
		
		
		super.update(l);
	}

	@Override
	public abstract void draw(Graphics g);
	
	public abstract void attack(Point p);
	public abstract void ability1(Point p);
	public abstract void ability2(Point p);
}
