import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Warrior extends Player {
	private static final int SIZE = 32;
	private static final int SWORD_SIZE = 96;
	private static final int SWORD_SWING = 120;
	private Image img;
	
	public Warrior(){
		super();
		setStats(new Stats(100, 60, 2.0, 20.0));
		
//		try{
//			img = ImageIO.read(new File(".png"));
//		}
//		catch(Exception IOException){
//			
//		}
		
	}
	
	@Override
	public void draw(Graphics g) {
		//g.setColor(Color.GRAY);
		//g.fillRect((int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, getWidth(), getHeight());
		//g.drawImage(img, (int) getPos().getX() - getWidth() / 2, (int) getPos().getY() - getHeight() / 2, null);
		
		g.setColor(Color.LIGHT_GRAY);
		if (getAttacking() > 0) {
			g.fillArc((int) getPos().getX() - SWORD_SIZE / 2, (int) getPos().getY() - SWORD_SIZE / 2, SWORD_SIZE, SWORD_SIZE,
					(int) (getAttackDir().getAngle() - SWORD_SWING / 2), SWORD_SWING);
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
	}
	
	@Override
	public void attack(Point p, Room r) {
		super.attack(p, r);
		// r.addHitbox(hitbox);
	}
	
	@Override
	public void ability1(Point p, Room r) {
		
	}

	@Override
	public void ability2(Point p, Room r) {
		
	}
}