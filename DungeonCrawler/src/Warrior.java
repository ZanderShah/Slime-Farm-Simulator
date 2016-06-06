import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Warrior extends Player {
	private static final int SIZE = 32;
	private static final int SWORD_SIZE = 96;
	private static final int SWORD_SWING = 120;
	private Image img;
	
	public Warrior(){
		super();
		setStats(new Stats(100, 60, 40, 2.0, 20.0));
		
//		try{
//			img = ImageIO.read(new File(".png"));
//		}
//		catch(Exception IOException){
//			
//		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect((int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, getWidth(), getHeight());
		//g.drawImage(img, (int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, null);
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
	}
	
	@Override
	public boolean attack(Point p, Room r) {
		boolean attacked = super.attack(p, r);
		if (attacked) r.addDamageSource(new SwordDamageSource(getPos(), SWORD_SIZE, (int) getAttackDir().getAngle() - SWORD_SWING / 2, SWORD_SWING, getStats().getAttackTime(), true, 30));
		return attacked;
	}
	
	@Override
	public void ability1(Point p, Room r) {
		if (getAttacking() == 0 && getCooldown(1) == 0) {
			setAttacking(100);
			setCooldown(1, 5000);
			r.addDamageSource(new SwordDamageSource(getPos(), SWORD_SIZE, 0, 360, getStats().getAttackTime(), true, 30));
		}
	}

	@Override
	public void ability2(Point p, Room r) {
		
	}
	
	@Override
	public void ability3(Point p, Room r) {
		
	}
}