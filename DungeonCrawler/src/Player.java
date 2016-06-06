import java.awt.Graphics;
import java.awt.Point;

public abstract class Player extends LivingEntity {
	
	private static final int ATTACK_TIME = 40;
	private static final int WIDTH = 48;
	private static final int HEIGHT = 54;
	private static final int DEPTH = 18;
	
	private Vector2D attackDirection;
	
	private int attacking;
	private int attackCooldown;
	
	public Player() {
		super();
		setStats(new Stats(100, 40, 40, 3, 0));
		attacking = 0;
	}

	public void update(ControlState cs, Room r) {
		// send control state to server
		if (attacking > 0) attacking--;
		if (attackCooldown > 0) attackCooldown--;
		
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
			attack(cs.getMouse(), r);
		}
		
		speed.normalize();
		speed.multiplyBy(getStats().getSpeed());
		for (StatusEffect e : getEffects()) {
			if (e.getType() == StatusEffect.SPEED) {
				speed.multiplyBy(e.getStrength());
			}
		}
		
		if (attacking != 0) {
			speed.multiplyBy(0);
		}
		
		setSpeed(speed);
		
		super.update(r);
	}
	
	public int getAttacking() {
		return attacking;
	}
	
	public int getAttackCooldown() {
		return attackCooldown;
	}
	
	public Vector2D getAttackDir() {
		return attackDirection;
	}

	public int getWidth() {
		return WIDTH;
	}
	

	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public abstract void draw(Graphics g);
	
	public boolean attack(Point p, Room r) {
		if (attackCooldown == 0) {
			attacking = getStats().getAttackTime();
			attackCooldown = getStats().getAttackSpeed();
			Vector2D direction = (new Vector2D(p)).subtract(getPos());
			direction.normalize();
			attackDirection = direction;
			return true;
		}
		return false;
	}
	public abstract void ability1(Point p, Room r);
	public abstract void ability2(Point p, Room r);

	public void attack(Point p)
	{
		// TODO Auto-generated method stub
		
	}
}
