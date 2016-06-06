import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame
{
	public static Room entry;
	static Image floor;

	public Test()
	{
		super("Procedural Generator Test");
		TestPanel tp = new TestPanel();
		setResizable(true);
		setContentPane(tp);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	public static void main(String[] args)
	{
		// Seems to lose a lot of rooms due to rounding errors lmao
		SpriteSheet.initializeImages();
		entry = DungeonFactory.generateMap(25, 0);
		entry.setCurrent(true);
		Test pdt = new Test();
		pdt.setVisible(true);
	}

	static void drawRooms(Room t, Graphics g, boolean[] vis)
	{
		if (t == null || vis[t.id()])
			return;

		t.draw(g);
		vis[t.id()] = true;
		
		
		
		drawRooms(t.getUp(), g, vis);
		drawRooms(t.getDown(), g, vis);
		drawRooms(t.getRight(), g, vis);
		drawRooms(t.getLeft(), g, vis);
	}

	static void draw(Graphics g){
		try{
			floor = ImageIO.read(new File("img\\Floor1.png"));
		}
		catch(Exception IOException){
			
		}
		
		g.drawImage(floor, 0, 0, null);
	}
	static class TestPanel extends JPanel implements MouseListener,
			MouseMotionListener, KeyListener
	{

		private ControlState cs;

		// private Tank tankTest = new Tank();
		private Warrior warriorTest = new Warrior();
		private Thief thiefTest = new Thief();
		// private Hunter hunterTest;
		private Mage mageTest = new Mage();

		public TestPanel()
		{
			// try{
			// hunterTest = new Hunter();
			// }
			// catch(Exception IOException){
			//
			// }
			setPreferredSize(new Dimension(1000, 1000));
			setFocusable(true);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);

			cs = new ControlState();

			// r.addPlayer(tankTest);
			entry.addPlayer(warriorTest);
			entry.addPlayer(thiefTest);
			// r.addPlayer(hunterTest);
			entry.addPlayer(mageTest);

			(new Thread() {
				long lastUpdate;

				public void run()
				{
					lastUpdate = System.currentTimeMillis();
					while (true)
					{
						// tankTest.update(cs, r);
						warriorTest.update(cs, entry);
						thiefTest.update(cs, entry);
						// hunterTest.update(cs, r);
						mageTest.update(cs, entry);
						entry.update();
						repaint(0);
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

		public void paintComponent(Graphics g)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());

			entry.draw(g);
			drawRooms(entry, g, new boolean[10000]);
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
