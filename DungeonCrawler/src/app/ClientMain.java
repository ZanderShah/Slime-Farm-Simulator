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
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

import javax.swing.JFrame;

import player.Mage;
import player.Player;
import utility.Constants;
import utility.ControlState;
import utility.SpriteSheet;
import utility.Vector2D;
import world.DungeonFactory;
import world.Room;

public class ClientMain extends JFrame
{

	public ClientMain()
	{
		super("Dungeon Crawler");

		Game gc = new Game();
		getContentPane().add(gc);
		pack();
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		gc.startGraphics();
		if (Constants.OFFLINE)
		{
			gc.startGame();
		}
		else
		{
			try
			{
				gc.connect(InetAddress.getByName("10.242.161.92"));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		SpriteSheet.initializeImages();
		ClientMain mt = new ClientMain();
		mt.setVisible(true);
	}

	static void drawMinimap(Room t, Graphics g, Vector2D offset, boolean[] vis)
	{
		if (t == null || vis[t.id()])
			return;

		t.draw(g, offset);
		vis[t.id()] = true;

		drawMinimap(t.getUp(), g, offset, vis);
		drawMinimap(t.getDown(), g, offset, vis);
		drawMinimap(t.getRight(), g, offset, vis);
		drawMinimap(t.getLeft(), g, offset, vis);
	}

	static class Game extends Canvas implements MouseListener,
			MouseMotionListener, KeyListener
	{
		private static final int REC_PACKET_SIZE = 5000;

		private ControlState cs;

		private Player controlled;
		private Room current[];
		private int currentFloor;
		private boolean inGame;

		private InetAddress addr;
		private DatagramSocket sock;
		private ByteArrayOutputStream byteStream;
		private ObjectOutputStream os;

		public Game()
		{
			currentFloor = 0;
			current = DungeonFactory.generateMap(Constants.NUMBER_OF_ROOMS, 0,
					Constants.NUMBER_OF_FLOORS, (new Random()).nextLong());
			current[currentFloor].setCurrent();

			try
			{
				sock = new DatagramSocket(Constants.CLIENT_PORT);
			}
			catch (SocketException e)
			{
				e.printStackTrace();
			}

			byteStream = new ByteArrayOutputStream(5000);
			try
			{
				os = (new ObjectOutputStream(
						new BufferedOutputStream(byteStream)));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			setPreferredSize(new Dimension(Constants.SCREEN_WIDTH,
					Constants.SCREEN_HEIGHT));
			setFocusable(true);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);

			cs = new ControlState();
		}

		public void connect(InetAddress i)
		{
			addr = i;
			try
			{
				sock.send(new DatagramPacket(new byte[] { 0 }, 1, i,
						Constants.SERVER_PORT));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			(new Thread() {
				@Override
				public void run()
				{
					while (true)
					{
						DatagramPacket dp = new DatagramPacket(
								new byte[REC_PACKET_SIZE], REC_PACKET_SIZE);
						try
						{
							sock.receive(dp);
							parsePacket(dp);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		public void startGraphics()
		{
			createBufferStrategy(2);

			(new Thread() {
				public void run()
				{
					long lastUpdate = System.currentTimeMillis();
					while (true)
					{
						do
						{
							do
							{
								Graphics graphics = Game.this
										.getBufferStrategy().getDrawGraphics();
								if (inGame)
								{
									if (controlled != null)
									{
										drawGame(graphics, controlled);
									}
								}
								graphics.dispose();
							}
							while (Game.this.getBufferStrategy()
									.contentsRestored());
							Game.this.getBufferStrategy().show();
						}
						while (Game.this.getBufferStrategy()
								.contentsLost());
						long time = System.currentTimeMillis();
						long diff = time - lastUpdate;
						try
						{
							Thread.sleep(Math.max(0, 1000 / 60 - diff));
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		public void startGame()
		{
			inGame = true;
			if (Constants.OFFLINE)
			{
				controlled = new Mage();
				controlled.setPos(new Vector2D(40, 40));
				current[currentFloor].addPlayer(controlled);
			}
			(new Thread() {
				long lastUpdate;

				public void run()
				{
					lastUpdate = System.currentTimeMillis();
					while (inGame)
					{
						// Update player with client data
						if (!Constants.OFFLINE)
						{
							try
							{
								byteStream = new ByteArrayOutputStream();
								os = new ObjectOutputStream(byteStream);
								os.flush();
								os.writeObject(cs);
								os.flush();
								byte[] object = byteStream.toByteArray();
								byte[] message = new byte[object.length + 1];
								message[0] = 3;
								for (int i = 0; i < object.length; i++)
								{
									message[i + 1] = object[i];
								}
								sock.send(
										new DatagramPacket(message,
												message.length,
												addr, Constants.SERVER_PORT));
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}

						// Update stuff locally
						if (controlled != null)
							controlled.update(cs, current[currentFloor]);
						current[currentFloor].update();

						int roomCheck = current[currentFloor]
								.atDoor(controlled);
						if (roomCheck == Constants.LEFT)
						{
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getLeft(),
											roomCheck);
						}
						else if (roomCheck == Constants.RIGHT)
						{
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getRight(),
											roomCheck);
						}
						else if (roomCheck == Constants.UP)
						{
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getUp(),
											roomCheck);
						}
						else if (roomCheck == Constants.DOWN)
						{
							current[currentFloor] = current[currentFloor]
									.moveTo(current[currentFloor].getDown(),
											roomCheck);
						}

						long time = System.currentTimeMillis();
						long diff = time - lastUpdate;
						lastUpdate = time;
						try
						{
							Thread.sleep(Math.max(0, 1000 / 60 - diff));
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		private void parsePacket(DatagramPacket dp) throws Exception
		{
			switch (dp.getData()[0])
			{
			case 0: // connected
				System.out.println("Connected to server");
				break;
			case 1: // player selected response
				ObjectInputStream ois = makeObjectStream(dp.getData());
				long id = ois.readLong();
				int type = ois.readInt();
				System.out.println("Class selected: " + type);
				controlled = Player.makePlayer(id, type);
				break;
			case 2: // game started
				System.out.println("Game starting");
				startGame();
				break;
			case 3: // all player update
				ois = makeObjectStream(dp.getData());
				int numPlayers = ois.readInt();
				for (int i = 0; i < numPlayers; i++)
				{
					boolean exists = false;
					Player p = (Player) ois.readObject();
					for (int j = 0; j < current[currentFloor].getPlayers()
							.size(); j++)
					{
						if (current[currentFloor].getPlayers().get(j).getID() == p
								.getID())
						{
							current[currentFloor].getPlayers().set(j, p);
							exists = true;
						}
					}
					if (!exists)
					{
						current[currentFloor].getPlayers().add(p);
					}
				}
				ois.close();
				break;
			case 4: // specific player update
				ois = makeObjectStream(dp.getData());
				controlled = (Player) ois.readObject();
				for (int i = 0; i < current[currentFloor].getPlayers()
						.size(); i++)
				{
					if (current[currentFloor].getPlayers().get(i)
							.getID() == controlled.getID())
					{
						current[currentFloor].getPlayers().set(i, controlled);
					}
				}
				ois.close();
				break;
			}
		}

		private ObjectInputStream makeObjectStream(byte[] bytes)
				throws Exception
		{
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			byteStream.read();
			ObjectInputStream ois = new ObjectInputStream(byteStream);
			return ois;
		}

		public void drawGame(Graphics g, Player p)
		{
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			Vector2D offset = Constants.MIDDLE.subtract(p.getPos());

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());

			current[currentFloor].detailedDraw(g, offset, controlled);
			drawMinimap(current[currentFloor], g, offset,
					new boolean[Constants.NUMBER_OF_ROOMS]);

			drawHUD(p, g);
		}

		public void drawHUD(Player p, Graphics g)
		{
			g.setColor(new Color(0, 0, 0, 127));
			g.fillRect(0, getHeight() - 95, getWidth() - 450, 100);

			// Health bar
			g.setColor(Color.GRAY.darker());
			g.fillRect(30, getHeight() - 75, 300, 20);
			g.setColor(Color.RED);
			g.fillRect(30, getHeight() - 75, (int) (300.0 * p.getStats()
					.getHealth() / p.getStats().getMaxHealth()), 20);
			g.setColor(Color.WHITE);
			g.drawString("HP", 10, getHeight() - 60);

			// Experience bar
			g.setColor(Color.GRAY.darker());
			g.fillRect(50, getHeight() - 40, 280, 20);
			g.setColor(new Color(236, 229, 130));
			g.fillRect(50, getHeight() - 40, (int) (280.0 * p.getExperience()
					/ Constants.EXPERIENCE_REQUIRED[p.getLevel()]), 20);
			g.setColor(Color.WHITE);
			g.drawString("EXP", 5, getHeight() - 25);
			g.drawString((p.getLevel() + 1) + "", 37, getHeight() - 25);
			g.drawRect(30, getHeight() - 40, 19, 19);

			for (int i = 0; i < 3; i++)
			{
				if (p.getAbilityActive(i + 1) > 0)
				{
					g.setColor(new Color(255, 255, 255, 127));
					g.fillRect(340 + 80 * i, getHeight() - 85,
							10 + SpriteSheet.HUD_IMAGES[p.getType()][i]
									.getWidth(null),
							10 + SpriteSheet.HUD_IMAGES[p.getType()][i]
									.getHeight(null));
				}
				g.drawImage(SpriteSheet.HUD_IMAGES[p.getType()][i],
						345 + 80 * i, getHeight() - 80, null);
				if (p.getCooldown(i + 1) > 0)
				{
					g.setColor(new Color(255, 255, 255, 127));
					g.fillArc(
							345 + 80 * i,
							getHeight() - 80,
							SpriteSheet.HUD_IMAGES[p.getType()][i]
									.getWidth(null),
							SpriteSheet.HUD_IMAGES[p.getType()][i]
									.getHeight(null),
							90,
							(int) (p.getCooldown(i + 1) * 1.0
									/ Constants.AB_COOLDOWNS[p.getType()][i] * 360));
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			switch (e.getButton())
			{
			case MouseEvent.BUTTON1:
				cs.press(ControlState.KEY_ATTACK);
				break;
			case MouseEvent.BUTTON3:
				cs.press(ControlState.KEY_AB3);
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			switch (e.getButton())
			{
			case MouseEvent.BUTTON1:
				cs.release(ControlState.KEY_ATTACK);
				break;
			case MouseEvent.BUTTON3:
				cs.release(ControlState.KEY_AB3);
				break;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			cs.updateMouse(e.getPoint());
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			cs.updateMouse(e.getPoint());
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			switch (e.getKeyCode())
			{
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
			if (!inGame) {
				try {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_0:
						sock.send(new DatagramPacket(new byte[] { 1, 0 }, 2, addr, Constants.SERVER_PORT));
						break;
					case KeyEvent.VK_1:
						sock.send(new DatagramPacket(new byte[] { 1, 1 }, 2, addr, Constants.SERVER_PORT));
						break;
					case KeyEvent.VK_2:
						sock.send(new DatagramPacket(new byte[] { 1, 2 }, 2, addr, Constants.SERVER_PORT));
						break;
					case KeyEvent.VK_3:
						sock.send(new DatagramPacket(new byte[] { 1, 3 }, 2, addr, Constants.SERVER_PORT));
						break;
					case KeyEvent.VK_4:
						sock.send(new DatagramPacket(new byte[] { 1, 4 }, 2, addr, Constants.SERVER_PORT));
						break;
					case KeyEvent.VK_5:
						sock.send(new DatagramPacket(new byte[] { 1, 5 }, 2, addr, Constants.SERVER_PORT));
						break;
					case KeyEvent.VK_ENTER:
						sock.send(new DatagramPacket(new byte[] { 2 }, 1, addr, Constants.SERVER_PORT));
						break;
					}
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			switch (e.getKeyCode())
			{
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
		public void mouseClicked(MouseEvent e)
		{
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
		}
	}
}
