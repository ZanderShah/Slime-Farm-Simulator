package engine;
import java.awt.Graphics;

import utility.Vector2D;

public interface Drawable {
	public void draw(Graphics g, Vector2D offset);
}