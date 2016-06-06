package utility;
import java.awt.Point;

public class ControlState {
	public static final int KEY_UP = 0;
	public static final int KEY_LEFT = 1;
	public static final int KEY_DOWN = 2;
	public static final int KEY_RIGHT = 3;
	public static final int KEY_AB1 = 4;
	public static final int KEY_AB2 = 5;
	public static final int KEY_ATTACK = 6;
	public static final int KEY_AB3 = 7;
	
	private Point mouse;
	private boolean[] pressed; // w, a, s, d, q, e, m1, m2 in that order
	
	public ControlState() {
		mouse = new Point();
		pressed = new boolean[8];
	}
	
	public boolean getPressed(int p) {
		return pressed[p];
	}
	
	public Point getMouse() {
		return mouse;
	}
	
	public void press(int p) {
		pressed[p] = true;
	}
	
	public void release(int r) {
		pressed[r] = false;
	}
	
	public void updateMouse(Point p) {
		mouse = p;
	}
	
	public String toString() {
		return "Mouse: [" + mouse.x + ", " + mouse.y + "], Pressed: w: " + pressed[0] + " a: " + pressed[1] + " s: " + pressed[2] + " d: " + pressed[3] + " q: " + pressed[4] + " e: " + pressed[5] + " m1: " + pressed[6] + " m2: " + pressed[7];
	}
}