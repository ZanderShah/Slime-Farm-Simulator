
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

// Not used anymore
public class ProceduralDungeonTest extends JFrame {

	private static final int MIN_ROOM_WIDTH = 10;
	private static final int MAX_ROOM_WIDTH = 20;
	private static final int MIN_ROOM_HEIGHT = 10;
	private static final int MAX_ROOM_HEIGHT = 20;
	private static final int NUM_ROOMS = 15;
	
	public ProceduralDungeonTest() {
		super("Procedural Generator Test");
		DungeonPanel dp = new DungeonPanel(MIN_ROOM_WIDTH, MAX_ROOM_WIDTH, MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT, NUM_ROOMS);
		setResizable(true);
		setContentPane(dp);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	public static void main(String[] args) {
		ProceduralDungeonTest pdt = new ProceduralDungeonTest();
		pdt.setVisible(true);
	}
	
	static class DungeonPanel extends JPanel implements MouseListener {
		
		private static ArrayList<Rectangle> rooms;
		private static boolean[][] map;
		private static int minW, maxW, minH, maxH, r;
		
		public DungeonPanel(int miw, int maw, int mih, int mah, int ra) {
			setPreferredSize(new Dimension(1000, 1000));
			addMouseListener(this);
			map = new boolean[1000][1000];
			rooms = new ArrayList<Rectangle>();
			
			minW = miw;
			maxW = maw;
			minH = mih;
			maxH = mah;
			r = ra;
		}
		
		public void generate() {
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 1000; j++) {
					map[i][j] = false;
				}
			}
			rooms = new ArrayList<Rectangle>();
			
			rooms.add(new Rectangle(500, 500, randW(), randH()));
			fillRect(rooms.get(0));
			new Thread() {
				public void run() {
					while (rooms.size() < r) {
						placeRoom(rooms.get((int) (Math.random() * rooms.size())), (int) (Math.random() * 4));
						repaint(0);
					}
				};
			}.start();
		}
		
		public void placeRoom(Rectangle prevRoom, int dir) {
			int w = randW();
			int h = randH();
			
			int x = 0;
			int y = 0;
			int pathX = 0;
			int pathY = 0;
			switch (dir) {
			case 0: // up
				x = prevRoom.x + prevRoom.width / 2 - w / 2;
				y = prevRoom.y - 1 - h;
				pathX = prevRoom.x + prevRoom.width / 2;
				pathY = y + h;
				break;
			case 1: // right
				x = prevRoom.x + prevRoom.width + 1;
				y = prevRoom.y + prevRoom.height / 2 - h / 2;
				pathX = x - 1;
				pathY = prevRoom.y + prevRoom.height / 2;
				break;
			case 2: // down
				x = prevRoom.x + prevRoom.width / 2 - w / 2;
				y = prevRoom.y + prevRoom.height + 1;
				pathX = prevRoom.x + prevRoom.width / 2;
				pathY = y - 1;
				break;
			case 3: // left
				x = prevRoom.x - 1 - w;
				y = prevRoom.y + prevRoom.height / 2 - h / 2;
				pathX = x + w;
				pathY = prevRoom.y + prevRoom.height / 2;
				break;
			}
			
			Rectangle newRoom = new Rectangle(x, y, w, h);
			if (valid(newRoom)) {
				fillRect(newRoom);
				rooms.add(newRoom);
				map[pathY][pathX] = true;
			}
		}
		
		public static int randRange(int a, int b) {
			return (int) (Math.random() * (b - a + 1)) + a;
		}
		
		public static int randW() {
			return randRange(minW, maxW);
		}
		
		public static int randH() {
			return randRange(minH, maxH);
		}
		
		public static boolean valid(Rectangle r) {
			for (int i = r.x - 1; i <= r.x + r.width; i++) {
				for (int j = r.y - 1; j <= r.y + r.height; j++) {
					if (i < 0 || i >= 1000 || j < 0 || j >= 1000) return false;
					if (map[j][i]) {
						return false;
					}
				}
			}
			
			return true;
		}
		
		public static void fillRect(Rectangle r) {
			for (int i = r.x; i < r.x + r.width; i++) {
				for (int j = r.y; j < r.y + r.height; j++) {
					map[j][i] = true;
				}
			}
		}
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.setColor(Color.WHITE);
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 1000; j++) {
					if (map[i][j]) {
						g.fillRect(i, j, 1, 1);
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			generate();
			repaint(0);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
	}
}