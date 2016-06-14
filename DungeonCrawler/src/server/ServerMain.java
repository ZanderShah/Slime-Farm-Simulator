package server;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utility.Constants;
import utility.SpriteSheet;

public class ServerMain {

	public static void main(String[] args) {
		SpriteSheet.initializeImages();
		Server s = new Server();
		s.startServer();
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				s.draw(g);
			}
		};
		panel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		frame.setContentPane(panel);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		(new Thread() {
			public void run() {
				while (true) {
					panel.repaint();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}