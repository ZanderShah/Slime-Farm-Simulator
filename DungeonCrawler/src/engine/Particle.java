package engine;

import java.awt.Graphics;

import utility.SpriteSheet;
import utility.Vector2D;

public class Particle implements Drawable {
	private int type;
	private int life;
	private int period;
	private int time;
	private int frame;
	private Vector2D position;
	private Vector2D speed;
	
	public Particle(int t, int p, int l, Vector2D pos, Vector2D spd) {
		type = t;
		life = l;
		period = p;
		time = p;
		position = pos;
		speed = spd;
	}
	
	public boolean isDead() {
		return life <= 0;
	}
	
	public void update() {
		if (life > 0) life--;
		if (time > 0) time--;
		if (time == 0) {
			time = period;
			frame++;
			frame %= SpriteSheet.PARTICLES[type].length;
		}
		position.addToThis(speed);
	}
	
	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = position.add(offset);
		g.drawImage(SpriteSheet.PARTICLES[type][frame], (int) shifted.getX(), (int) shifted.getY(), null);
	}
}
