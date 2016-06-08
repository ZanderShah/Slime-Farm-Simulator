package app;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import player.Mage;
import player.Player;
import player.Thief;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.DungeonFactory;
import world.Room;

public class Test extends JFrame {
	public static Room current;
	public static Vector2D middle, totalOffset;
	static Image floor;

	public Test() {
		super("Procedural Generator Test");
		GameCanvas gc = new GameCanvas();
		getContentPane().add(gc);
		pack();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		gc.startGame();
	}

	public static void main(String[] args) {
		// Seems to lose a lot of rooms due to rounding errors lmao
		SpriteSheet.initializeImages();
		current = DungeonFactory.generateMap(Constants.NUMBER_OF_ROOMS, 0);
		current.setCurrent();
		// ******* Will need to reset totalOffset when switching rooms *******
		totalOffset = new Vector2D(0, 0);
		Test pdt = new Test();
		pdt.setVisible(true);
	}

	static void drawRooms(Room t, Graphics g, boolean[] vis) {
		if (t == null || vis[t.id()])
			return;

		t.draw(g);
		vis[t.id()] = true;

		drawRooms(t.getUp(), g, vis);
		drawRooms(t.getDown(), g, vis);
		drawRooms(t.getRight(), g, vis);
		drawRooms(t.getLeft(), g, vis);
	}

	static class GameCanvas extends Canvas implements MouseListener,
			MouseMotionListener, KeyListener {
		private ControlState cs;

		// private Tank tankTest = new Tank();
		// private Warrior warriorTest = new Warrior();
		private Thief thiefTest = new Thief();

		// private Hunter hunterTest;
		private Mage mageTest = new Mage();

		public GameCanvas() {
			setPreferredSize(new Dimension(1000, 1000));
			setFocusable(true);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);

			cs = new ControlState();

			// r.addPlayer(tankTest);
			// entry.addPlayer(warriorTest);
			current.addPlayer(thiefTest);
			// entry.addPlayer(warriorTest);
			// entry.addPlayer(thiefTest);
			// r.addPlayer(hunterTest);
			current.addPlayer(mageTest);
		}

		public void startGame() {
			(new Thread() {
				long lastUpdate;

				public void run() {
					lastUpdate = System.currentTimeMillis();
					while (true) {
						// tankTest.update(cs, r);
						// warriorTest.update(cs, entry);
						thiefTest.update(cs, current);
						// warriorTest.update(cs, entry);
						// thiefTest.update(cs, entry);
						// hunterTest.update(cs, r);
						// mageTest.update(cs, current);
						current.update();
						long time = System.currentTimeMillis();
						long diff = time - lastUpdate;
						lastUpdate = time;
						try {
							Thread.sleep(Math.max(0, 1000 / 60 - diff));
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

			createBufferStrategy(2);

			(new Thread() {
				public void run() {
					while (true) {
						do {
							do {
								Graphics graphics = GameCanvas.this
										.getBufferStrategy().getDrawGraphics();
								drawGame(graphics);
								graphics.dispose();
							}
							while (GameCanvas.this.getBufferStrategy()
									.contentsRestored());
							GameCanvas.this.getBufferStrategy().show();
						}
						while (GameCanvas.this.getBufferStrategy()
								.contentsLost());
					}
				}
			}).start();
		}

		public void drawGame(Graphics g) {
			middle = new Vector2D(getWidth() / 2, getHeight() / 2);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());

			current.draw(g);
			drawRooms(current, g, new boolean[10000]);
			drawHUD(thiefTest, g);
		}

		public void drawHUD(Player p, Graphics g) {
			g.setColor(Color.GRAY);
			g.fillRect(0, getHeight() - 200, getWidth(), 200);

			g.setColor(Color.GRAY.darker());
			g.fillRect(100, getHeight() - 180, 300, 20);

			g.setColor(Color.RED);
			g.fillRect(100, getHeight() - 180, (int) (300.0
					* p.getStats().getHealth() / p.getStats().getMaxHealth()),
					20);

			g.setColor(Color.WHITE);
			g.drawString(
					p.getStats().getHealth() + "/"
							+ p.getStats().getMaxHealth(),
					230, getHeight() - 165);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				cs.press(ControlState.KEY_ATTACK);
				break;
			case MouseEvent.BUTTON3:
				cs.press(ControlState.KEY_AB3);
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				cs.release(ControlState.KEY_ATTACK);
				break;
			case MouseEvent.BUTTON3:
				cs.release(ControlState.KEY_AB3);
				break;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			cs.updateMouse(e.getPoint());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			cs.updateMouse(e.getPoint());
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				cs.press(ControlState.KEY_UP);
				break;
			case KeyEvent.VK_A:
				cs.press(ControlState.KEY_LEFT);
				break;
			case KeyEvent.VK_S:
				cs.press(ControlState.KEY_DOWN);
				break;
			case KeyEvent.VK_D:
				cs.press(ControlState.KEY_RIGHT);
				break;
			case KeyEvent.VK_Q:
				cs.press(ControlState.KEY_AB1);
				break;
			case KeyEvent.VK_E:
				cs.press(ControlState.KEY_AB2);
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				cs.release(ControlState.KEY_UP);
				break;
			case KeyEvent.VK_A:
				cs.release(ControlState.KEY_LEFT);
				break;
			case KeyEvent.VK_S:
				cs.release(ControlState.KEY_DOWN);
				break;
			case KeyEvent.VK_D:
				cs.release(ControlState.KEY_RIGHT);
				break;
			case KeyEvent.VK_Q:
				cs.release(ControlState.KEY_AB1);
				break;
			case KeyEvent.VK_E:
				cs.release(ControlState.KEY_AB2);
				break;
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
}
