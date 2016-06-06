import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Thief extends Player {
	private static final int SIZE = 32;
	private static final int SWORD_SIZE = 64;
	private static final int SWORD_SWING = 30;
	
	private int blinking;
	private int dodging;
	
	Vector2D dodgeDirection;
	
	public Thief() {
		super();
		setStats(new Stats(100, 30, 10, 3.0, 20.0));
		blinking = 0;
		dodging = 0;
		dodgeDirection = new Vector2D();
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		if (blinking != 0) {
			g.setColor(new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 128));
		}
		g.fillRect((int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, getWidth(), getHeight());
		
//		g.setColor(Color.LIGHT_GRAY);
//		if (getAttacking() > 0) {
//			g.fillArc((int) getPos().getX() - SWORD_SIZE / 2, (int) getPos().getY() - SWORD_SIZE / 2, SWORD_SIZE, SWORD_SIZE,
//					(int) (getAttackDir().getAngle() - SWORD_SWING / 2), SWORD_SWING);
//		}
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
		if (blinking > 0) {
			blinking--;
			if (blinking == 0) setCooldown(1, 300);
		}
		super.update(cs, r);
		if (dodging > 0) {
			dodging--;
			setSpeed(dodgeDirection.multiply(6));
		}
	}
	
	@Override
	public boolean attack(Point p, Room r) {
		boolean attacked = super.attack(p, r);
		if (attacked) {
			r.addDamageSource(new SwordDamageSource(getPos(), SWORD_SIZE, (int) getAttackDir().getAngle() - SWORD_SWING / 2, SWORD_SWING, getStats().getAttackTime(), true, (blinking != 0 ? 60 : 20)));
			blinking = 0;
			setCooldown(1, 300);
		}
		return attacked;
	}
	
	// Blink ability: turn invisible/invincible for 3 seconds, attack to reappear and deal crit (3x) damage
	// Cooldown: 5 seconds from reappearing
	@Override
	public void ability1(Point p, Room r) {
		if (getAttacking() == 0 && getCooldown(1) == 0) {
			blinking = 180;
		}
	}

	// Dodge ability: quickly move towards the cursor
	// Cooldown: 1.5 seconds
	@Override
	public void ability2(Point p, Room r) {
		if (getAttacking() == 0 && getCooldown(2) == 0) {
			dodging = 30;
			dodgeDirection = (new Vector2D(p)).subtract(getPos());
			setCooldown(2, 90);
		}
	}

	@Override
	public void ability3(Point p, Room r) {
		// TODO Auto-generated method stub
		
	}
}