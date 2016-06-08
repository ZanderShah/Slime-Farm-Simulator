package engine;

import java.awt.Graphics;

import utility.Vector2D;

public class DefenceDebuff extends DamageSource {
	
	public DefenceDebuff(Hitbox h, int f, int d, boolean single, boolean p) {
		super(h, f, d, single, p, 0);
		
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {}
}