package app;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JFrame;

import player.Cleric;
import player.Hunter;
import player.Mage;
import player.Player;
import player.Tank;
import player.Thief;
import player.Warrior;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.DungeonFactory;
import world.Room;

public class Test extends JFrame {
	public static Vector2D middle;

	public Test() {
		super("Dungeon Crawler");

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
		Test mt = new Test();
		mt.setVisible(true);
	}

	static void drawRooms(Room t, Graphics g, Vector2D offset, boolean[] vis) {
		if (t == null || vis[t.id()])
			return;

		t.draw(g, offset);
		vis[t.id()] = true;

		drawRooms(t.getUp(), g, offset, vis);
		drawRooms(t.getDown(), g, offset, vis);
		drawRooms(t.getRight(), g, offset, vis);
		drawRooms(t.getLeft(), g, offset, vis);
	}

	static class GameCanvas extends Canvas implements MouseListener,
			MouseMotionListener, KeyListener {
		private ControlState cs;

		private Tank tankTest = new Tank();
		private Warrior warriorTest = new Warrior();
		private Thief thiefTest = new Thief();
		private Hunter hunterTest = new Hunter();
		private Mage mageTest = new Mage();
		private Cleric clericTest = new Cleric();
		private Player controlled;
		private Room current[];
		private int currentFloor;

		private DatagramSocket sock;

		public GameCanvas() {
			currentFloor = 0;
			current = DungeonFactory.generateMap(Constants.NUMBER_OF_ROOMS, 0,
					Constants.NUMBER_OF_FLOORS);
			current[currentFloor].setCurrent();

			try {
				sock = new DatagramSocket();
			}
			catch (SocketException e) {
				e.printStackTrace();
			}

			setPreferredSize(new Dimension(1000, 1000));
			setFocusable(true);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);

			cs = new ControlState();

			warriorTest.setPos(new Vector2D(30, 30));
			thiefTest.setPos(new Vector2D(30, 30));
			mageTest.setPos(new Vector2D(30, 30));
			tankTest.setPos(new Vector2D(30, 30));
			hunterTest.setPos(new Vector2D(40, 40));
			clericTest.setPos(new Vector2D(30, 30));

			// current[currentFloor].addPlayer(warriorTest);
			current[currentFloor].addPlayer(thiefTest);
			// current[currentFloor].addPlayer(mageTest);
			// current[currentFloor].addPlayer(tankTest);
			// current[currentFloor].addPlayer(hunterTest);
			// current[currentFloor].addPlayer(clericTest);

			// Change controlled to test other players without having to change
			// everything
			// controlled = warriorTest;
			controlled = thiefTest;
			// controlled = mageTest;
			// controlled = tankTest;
			// controlled = hunterTest;
			// controlled = clericTest;

		}

		public void startGame() {
			try {
				sock.send(new DatagramPacket(new byte[] { 0 }, 1, InetAddress
						.getByName("localhost"), 7382));
				sock.send(new DatagramPacket(new byte[] { 1, 4 }, 2,
						InetAddress.getByName("localhost"), 7382));
				sock.send(new DatagramPacket(new byte[] { 2 }, 1, InetAddress
						.getByName("localhost"), 7382));
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			(new Thread() {
				long lastUpdate;

				public void run() {
					lastUpdate = System.currentTimeMillis();
					while (true) {

						// Update player with client data
						byte[] csBytes = cs.getBytes();
						byte[] message = new byte[csBytes.length + 1];
						message[0] = 3;
						for (int i = 0; i < csBytes.length; i++) {
							message[i + 1] = csBytes[i];
						}
						try {
							sock.send(new DatagramPacket(message,
									message.length, InetAddress
											.getByName("localhost"),
									7382));
						}
						catch (Exception e1) {
							e1.printStackTrace();
						}

						// Update stuff locally
						controlled.update(cs, current[currentFloor]);
						current[currentFloor].update();

						int roomCheck = current[currentFloor]
								.atDoor(controlled);
						if (roomCheck == Constants.LEFT) {
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getLeft(),
											roomCheck);
						}
						else if (roomCheck == Constants.RIGHT) {
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getRight(),
											roomCheck);
						}
						else if (roomCheck == Constants.UP) {
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getUp(),
											roomCheck);
						}
						else if (roomCheck == Constants.DOWN) {
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getDown(),
											roomCheck);
						}

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
					long lastUpdate = System.currentTimeMillis();
					while (true) {
						do {
							do {
								Graphics graphics = GameCanvas.this
										.getBufferStrategy().getDrawGraphics();
								// Replace with actual current player
								drawGame(graphics, controlled);
								graphics.dispose();
							}
							while (GameCanvas.this.getBufferStrategy()
									.contentsRestored());
							GameCanvas.this.getBufferStrategy().show();
						}
						while (GameCanvas.this.getBufferStrategy()
								.contentsLost());
						long time = System.currentTimeMillis();
						long diff = time - lastUpdate;
						try {
							Thread.sleep(Math.max(0, 1000 / 60 - diff));
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		public void drawGame(Graphics g, Player p) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			middle = new Vector2D(getWidth() / 2, getHeight() / 2);
			Vector2D offset = middle.subtract(p.getPos());

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());

			current[currentFloor].detailedDraw(g, offset, controlled);
			drawRooms(current[currentFloor], g, offset,
					new boolean[Constants.NUMBER_OF_ROOMS]);

			drawHUD(p, g);
		}

		public void drawHUD(Player p, Graphics g) {
			g.setColor(Color.GRAY);
			g.fillRect(0, getHeight() - 200, getWidth(), 200);

			g.setColor(Color.GRAY.darker());
			g.fillRect(100, getHeight() - 180, 300, 20);

			g.setColor(Color.RED);
			g.fillRect(100, getHeight() - 180, (int) (300.0 * p.getStats()
					.getHealth() / p.getStats().getMaxHealth()), 20);

			g.setColor(Color.WHITE);
			g.drawString((int) p.getStats().getHealth() + "/"
					+ (int) p.getStats().getMaxHealth(), 230,
					getHeight() - 165);

			g.drawString(
					"Ability 1: " + p.getAbilityActive(1) + " Ability 2: "
							+ p.getAbilityActive(2) + " Ability 3: "
							+ p.getAbilityActive(3),
					100, getHeight() - 140);
			g.drawString(
					"Ability 1: " + p.getCooldown(1) + " Ability 2: "
							+ p.getCooldown(2) + " Ability 3: "
							+ p.getCooldown(3),
					100, getHeight() - 100);
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
