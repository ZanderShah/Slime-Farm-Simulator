import java.awt.Graphics;

public abstract class Player extends LivingEntity {

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
		
		speed.normalize();
		speed.multiplyBy(); // Adjusted based on which class it is
		
		setSpeed(speed);
		
		super.update(l);
	}
	
	@Override
	public abstract void draw(Graphics g);
	
	public abstract void attack();
	public abstract void ability1();
	public abstract void ability2();
}
