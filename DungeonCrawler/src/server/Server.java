package server;

import java.awt.Graphics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

import utility.Constants;
import utility.ControlState;
import utility.Vector2D;
import world.DungeonFactory;
import world.Room;

public class Server {

	private static final int REC_PACKET_SIZE = 5000;
	
	private HashMap<InetAddress, Client> clients = new HashMap<InetAddress, Client>();
	private boolean inGame;
	private DatagramSocket sock;
	private Room current[];
	private int currentFloor;
	
	public Server() {
		currentFloor = 0;
		current = DungeonFactory.generateMap(Constants.NUMBER_OF_ROOMS, 0,
				Constants.NUMBER_OF_FLOORS);
		current[currentFloor].setCurrent();
		inGame = false;
		try {
			sock = new DatagramSocket(7382);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() {
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
			long lastUpdate;
			
			public void run() {
				lastUpdate = System.currentTimeMillis();
				while (true) {
					for (int i = 0; i < clients.size(); i++) {
						clients.get(clients.keySet().toArray()[i]).update(current[currentFloor]);
					}
					current[currentFloor].update();

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
	}
	
	public void parsePacket(DatagramPacket dp) {
		switch (dp.getData()[0]) {
		case 0: // Connected
			if (!inGame && clients.size() < 4) {
				System.out.println("Client connected");
				Client c = new Client(dp.getAddress());
				if (clients.isEmpty()) {
					c.setHost(true);
				}
				clients.put(dp.getAddress(), c);
			}
			break;
		case 1: // Choose class
			if (!inGame) {
				System.out.println("Class chosen: " + dp.getData()[1]);
				clients.get(dp.getAddress()).chooseClass(dp.getData()[1]);
			}
			break;
		case 2: // Start game
			if (clients.get(dp.getAddress()).isHost()) {
				System.out.println("Game started");
				startGame();
				for (int i = 0; i < clients.size(); i++) {
					current[currentFloor].addPlayer(clients.get(clients.keySet().toArray()[i]).getPlayer());
				}
			}
			break;
		case 3: // Control update
			if (inGame) {
				ControlState cs;
				try {
					ByteArrayInputStream byteStream = new ByteArrayInputStream(dp.getData());
					byteStream.read();
					ObjectInputStream ois = new ObjectInputStream(byteStream);
					Object o = ois.readObject();
					cs = (ControlState) o;
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
					cs = new ControlState();
				}
				clients.get(dp.getAddress()).setControls(cs);
			}
			break;
		}
	}
	
	public void draw(Graphics g) {
		if (inGame) {
			current[currentFloor].detailedDraw(g, new Vector2D(0, 0), null);
		}
	}
}