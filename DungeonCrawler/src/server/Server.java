package server;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

import utility.ControlState;
import world.Room;

public class Server {

	private static final int REC_PACKET_SIZE = 8;
	
	private HashMap<InetAddress, Client> clients = new HashMap<InetAddress, Client>();
	private boolean inGame;
	private DatagramSocket sock;
	private Room currentRoom;
	
	public Server() {
		inGame = false;
		try {
			sock = new DatagramSocket(7382);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		(new Thread() {
			@Override
			public void run() {
				while (true) {
					DatagramPacket dp = new DatagramPacket(new byte[REC_PACKET_SIZE], REC_PACKET_SIZE);
					try {
						sock.receive(dp);
						parsePacket(dp);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void startGame() {
		inGame = true;
		(new Thread() {
			public void run() {
				long lastUpdate = System.currentTimeMillis();
				while (inGame) {
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
	
	public void parsePacket(DatagramPacket dp) {
		switch (dp.getData()[0]) {
		case 0: // Connected
			if (!inGame) {
				Client c = new Client(dp.getAddress());
				if (clients.isEmpty()) {
					c.setHost(true);
				}
				clients.put(dp.getAddress(), c);
			}
			break;
		case 1: // Choose class
			if (!inGame)
				clients.get(dp.getAddress()).chooseClass(dp.getData()[1]);
			break;
		case 2: // Start game
			if (clients.get(dp.getAddress()).isHost()) {
				startGame();
			}
			break;
		case 3: // Control update
			if (inGame) {
				ControlState cs = new ControlState();
				byte pressed = dp.getData()[1];
				for (int i = 0; i < 8; i++) {
					if (((pressed >> i) & 1) == 1) {
						cs.press(i);
					}
				}
				int x = (dp.getData()[2] << 8) | dp.getData()[3];
				int y = (dp.getData()[4] << 8) | dp.getData()[5];
				cs.updateMouse(new Point(x, y));
				clients.get(dp.getAddress()).update(cs, currentRoom);
			}
			break;
		}
	}
}