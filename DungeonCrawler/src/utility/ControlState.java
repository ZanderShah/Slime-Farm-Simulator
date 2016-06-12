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
	
	public ControlState(byte[] bytes) {
		pressed = new boolean[8];
		for (int i = 0; i < 7; i++) {
			if (((bytes[0] >> i) & 1) == 1) {
				pressed[i] = true;
			}
		}
		pressed[7] = (bytes[1] == 1);
		int x = (bytes[2] << 8) | bytes[3];
		int y = (bytes[4] << 8) | bytes[5];
		mouse = new Point(x, y);
	}
	
	public byte[] getBytes() {
		byte[] bytes = new byte[6];
		for (int i = 6; i >= 0; i--) {
			bytes[0] <<= 1;
			bytes[0] |= (pressed[i] ? 1 : 0);
		}
		bytes[1] = (byte) (pressed[7] ? 1 : 0);
		bytes[2] = (byte) ((int) mouse.getX() >> 8);
		bytes[3] = (byte) ((int) mouse.getX() & 0xFF);
		bytes[4] = (byte) ((int) mouse.getY() >> 8);
		bytes[5] = (byte) ((int) mouse.getY() & 0xFF);
		return bytes;
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