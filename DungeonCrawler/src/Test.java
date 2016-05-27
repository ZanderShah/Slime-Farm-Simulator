import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame {
	
	public Test() {
		super("Procedural Generator Test");
		TestPanel tp = new TestPanel();
		setResizable(true);
		setContentPane(tp);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	public static void main(String[] args) {
		Test pdt = new Test();
		pdt.setVisible(true);
	}
	
	static class TestPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
		
		private ControlState cs;
		private Level currentLevel;
		
		private Tank tankTest = new Tank();
		private Warrior warriorTest = new Warrior();
		
		public TestPanel() {
			setPreferredSize(new Dimension(1000, 1000));
			setFocusable(true);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			
			cs = new ControlState();
			
			(new Thread() {
				long lastUpdate;
				public void run() {
					lastUpdate = System.currentTimeMillis();
					while (true) {
						tankTest.update(cs, currentLevel);
						warriorTest.update(cs, currentLevel);
						repaint(0);
						long time = System.currentTimeMillis();
						long diff = time - lastUpdate;
						lastUpdate = time;
						try {
							Thread.sleep(Math.max(0, 1000 / 60 - diff));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			tankTest.draw(g);
			warriorTest.draw(g);
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
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mouseDragged(MouseEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	}
}