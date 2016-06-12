package server;

import java.awt.Graphics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import player.PlayerState;
import utility.Constants;
import utility.ControlState;
import utility.Vector2D;
import world.DungeonFactory;
import world.Room;

public class Server {

	private static final int REC_PACKET_SIZE = 5000;
	
	private HashMap<InetAddress, Client> clients = new HashMap<InetAddress, Client>();
	private ArrayList<Client> clientList = new ArrayList<Client>();
	private boolean inGame;
	private DatagramSocket sock;
	private Room current[];
	private int currentFloor;
	
	private ByteArrayOutputStream byteStream;
	private ObjectOutputStream os;
	
	public Server() {
		currentFloor = 0;
		current = DungeonFactory.generateMap(Constants.NUMBER_OF_ROOMS, 0,
				Constants.NUMBER_OF_FLOORS);
		current[currentFloor].setCurrent();
		inGame = false;
		try {
			sock = new DatagramSocket(Constants.SERVER_PORT);
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
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void startGame() {
		inGame = true;
		for (int i = 0; i < clientList.size(); i++) {
			try {
				clientList.get(i).send(new byte[] {2});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		(new Thread() {
			long lastUpdate;
			
			public void run() {
				lastUpdate = System.currentTimeMillis();
				while (true) {
					for (int i = 0; i < clientList.size(); i++) {
						clientList.get(i).update(current[currentFloor]);
					}
					current[currentFloor].update();

					// Send all players to all clients
					for (int i = 0; i < clientList.size(); i++) {
						try {
							byteStream = new ByteArrayOutputStream();
							os = new ObjectOutputStream(byteStream);
							os.flush();
							os.writeInt(current[currentFloor].getPlayers().size());
							for (int j = 0; j < current[currentFloor].getPlayers().size(); j++) {
								os.writeObject(new PlayerState(current[currentFloor].getPlayers().get(j)));
							}
							os.flush();
							byte[] object = byteStream.toByteArray();
							byte[] message = new byte[object.length + 1];
							message[0] = 3;
							for (int j = 0; j < object.length; j++) {
								message[j + 1] = object[j];
							}
							
							for (int j = 0; j < clientList.size(); j++) {
								clientList.get(j).send(message);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// Send each client their own player
					for (int i = 0; i < clientList.size(); i++) {
						try {
							byteStream = new ByteArrayOutputStream();
							os = new ObjectOutputStream(byteStream);
							os.flush();
							os.writeObject(new PlayerState(clientList.get(i).getPlayer()));
							os.flush();
							byte[] object = byteStream.toByteArray();
							byte[] message = new byte[object.length + 1];
							message[0] = 4;
							for (int j = 0; j < object.length; j++) {
								message[j + 1] = object[j];
							}
							
							clientList.get(i).send(message);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
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
	
	public void parsePacket(DatagramPacket dp) throws Exception {
		switch (dp.getData()[0]) {
		case 0: // Connected
			if (!inGame && clients.size() < 4) {
				System.out.println("Client connected");
				Client c = new Client(dp.getAddress());
				if (clients.isEmpty()) {
					c.setHost(true);
				}
				clients.put(dp.getAddress(), c);
				clientList.add(c);
				c.send(new byte[] {0});
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
				for (int i = 0; i < clientList.size(); i++) {
					current[currentFloor].addPlayer(clientList.get(i).getPlayer());
				}
			}
			break;
		case 3: // Control update
			if (inGame) {
				ControlState cs = new ControlState();
				ByteArrayInputStream byteStream = new ByteArrayInputStream(dp.getData());
				byteStream.read();
				ObjectInputStream ois = new ObjectInputStream(byteStream);
				Object o = ois.readObject();
				cs = (ControlState) o;
				ois.close();
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