package server;

import java.awt.Graphics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import utility.Constants;
import utility.ControlState;
import utility.Vector2D;
import world.DungeonFactory;
import world.Room;

public class Server {

	private static final int REC_PACKET_SIZE = 5000;

	private ArrayList<Client> clientList = new ArrayList<Client>();
	private boolean inGame;
	private DatagramSocket sock;
	private Room dungeon[];
	private int currentFloor;
	private long gameStart;

	private ByteArrayOutputStream byteOutStream;
	private ByteArrayInputStream byteInStream;
	private ObjectOutputStream os;
	private ObjectInputStream is;

	public Server() {
		currentFloor = 0;
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
		gameStart = System.currentTimeMillis();
		long seed = (new Random()).nextLong();
		dungeon = DungeonFactory.generateMap(Constants.NUMBER_OF_ROOMS, 0, Constants.NUMBER_OF_FLOORS, seed);
		dungeon[currentFloor].setCurrent();
		inGame = true;
		for (int i = 0; i < clientList.size(); i++) {
			try {
				byteOutStream = new ByteArrayOutputStream();
				os = new ObjectOutputStream(byteOutStream);
				os.flush();
				os.writeLong(seed);
				os.flush();
				byte[] s = byteOutStream.toByteArray();
				byte[] message = new byte[s.length + 1];
				message[0] = 2;
				for (int j = 0; j < s.length; j++) {
					message[j + 1] = s[j];
				}
				clientList.get(i).send(message);
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
						if (System.currentTimeMillis() - clientList.get(i).getPacketTime() > Constants.TIMEOUT
								&& System.currentTimeMillis() - gameStart > Constants.TIMEOUT && inGame) {
							clientList.get(i).getPlayer().damage(999999999);
							clientList.remove(i);
							i--;
						}
					}
					for (int i = 0; i < clientList.size(); i++) {
						clientList.get(i).update(dungeon[currentFloor]);
					}
					dungeon[currentFloor].update();

					// Send all players to all clients
					try {
						byteOutStream = new ByteArrayOutputStream();
						os = new ObjectOutputStream(byteOutStream);
						os.flush();
						os.writeInt(dungeon[currentFloor].getPlayers().size());
						for (int j = 0; j < dungeon[currentFloor].getPlayers().size(); j++) {
							os.writeObject(dungeon[currentFloor].getPlayers().get(j));
						}
						os.flush();
						byte[] object = byteOutStream.toByteArray();
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

					// Send each client their own player
					for (int i = 0; i < clientList.size(); i++) {
						try {
							byte[] object = getObjectBytes(clientList.get(i).getPlayer());
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

					try {
						byteOutStream = new ByteArrayOutputStream();
						os = new ObjectOutputStream(byteOutStream);
						os.flush();
						os.writeInt(dungeon[currentFloor].getDamageSources().size());
						for (int j = 0; j < dungeon[currentFloor].getDamageSources().size(); j++) {
							os.writeObject(dungeon[currentFloor].getDamageSources().get(j));
						}
						os.flush();
						byte[] object = byteOutStream.toByteArray();
						byte[] message = new byte[object.length + 1];
						message[0] = 5;
						for (int j = 0; j < object.length; j++) {
							message[j + 1] = object[j];
						}

						for (int j = 0; j < clientList.size(); j++) {
							clientList.get(j).send(message);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						byteOutStream = new ByteArrayOutputStream();
						os = new ObjectOutputStream(byteOutStream);
						os.flush();
						os.writeInt(dungeon[currentFloor].getEnemies().size());
						for (int j = 0; j < dungeon[currentFloor].getEnemies().size(); j++) {
							os.writeObject(dungeon[currentFloor].getEnemies().get(j));
						}
						os.flush();
						byte[] object = byteOutStream.toByteArray();
						byte[] message = new byte[object.length + 1];
						message[0] = 6;
						for (int j = 0; j < object.length; j++) {
							message[j + 1] = object[j];
						}

						for (int j = 0; j < clientList.size(); j++) {
							clientList.get(j).send(message);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					int roomCheck = dungeon[currentFloor].atDoor();
					if (roomCheck == Constants.LEFT) {
						dungeon[currentFloor] = dungeon[currentFloor].moveTo(dungeon[currentFloor].getLeft(), roomCheck);
					} else if (roomCheck == Constants.RIGHT) {
						dungeon[currentFloor] = dungeon[currentFloor].moveTo(dungeon[currentFloor].getRight(), roomCheck);
					} else if (roomCheck == Constants.UP) {
						dungeon[currentFloor] = dungeon[currentFloor].moveTo(dungeon[currentFloor].getUp(), roomCheck);
					} else if (roomCheck == Constants.DOWN) {
						dungeon[currentFloor] = dungeon[currentFloor].moveTo(dungeon[currentFloor].getDown(), roomCheck);
					}
					
					if (roomCheck >= 1) {
						for (int i = 0; i < clientList.size(); i++) {
							try {
								clientList.get(i).send(new byte[] {7, (byte) roomCheck});
							} catch (Exception e) {
								e.printStackTrace();
							}
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
		int client = -1;
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).getAddress().equals(dp.getAddress())) {
				client = i;
				clientList.get(i).updatePacketTime();
			}
		}
		if (dp.getData()[0] == 0 || client != -1) {
			switch (dp.getData()[0]) {
			case 0: // Connected
				if (!inGame && clientList.size() < 4) {
					System.out.println("Client connected");
					Client c = new Client(dp.getAddress());
					if (clientList.isEmpty()) {
						c.setHost(true);
					}
					clientList.add(c);
					c.send(new byte[] { 0 });
				}
				break;
			case 1: // Choose class
				if (!inGame) {
					System.out.println("Class chosen: " + dp.getData()[1]);
					clientList.get(client).chooseClass(dp.getData()[1]);
					byteOutStream = new ByteArrayOutputStream();
					os = new ObjectOutputStream(byteOutStream);
					os.flush();
					os.writeLong(clientList.get(client).getPlayer().getID());
					os.writeInt(dp.getData()[1]);
					os.flush();
					byte[] object = byteOutStream.toByteArray();
					byte[] message = new byte[object.length + 1];
					message[0] = 1;
					for (int j = 0; j < object.length; j++) {
						message[j + 1] = object[j];
					}
					clientList.get(client).send(message);
				}
				break;
			case 2: // Start game
				if (clientList.get(client).isHost()) {
					System.out.println("Game started");
					startGame();
					for (int i = 0; i < clientList.size(); i++) {
						dungeon[currentFloor].addPlayer(clientList.get(i).getPlayer());
					}
				}
				break;
			case 3: // Control update
				if (inGame) {
					ControlState cs = new ControlState();
					byteInStream = new ByteArrayInputStream(dp.getData());
					byteInStream.read();
					is = new ObjectInputStream(byteInStream);
					Object o = is.readObject();
					cs = (ControlState) o;
					is.close();
					clientList.get(client).setControls(cs);
				}
				break;
			}
		}
	}

	public void draw(Graphics g) {
		if (inGame) {
			dungeon[currentFloor].detailedDraw(g, new Vector2D(0, 0), null);
		}
	}

	public byte[] getObjectBytes(Object s) throws Exception {
		byteOutStream = new ByteArrayOutputStream();
		os = new ObjectOutputStream(byteOutStream);
		os.flush();
		os.writeObject(s);
		os.flush();
		return byteOutStream.toByteArray();
	}

	public byte[] getObjectBytes(Object[] s) throws Exception {
		byteOutStream = new ByteArrayOutputStream();
		os = new ObjectOutputStream(byteOutStream);
		os.flush();
		for (int i = 0; i < s.length; i++) {
			os.writeObject(s[i]);
		}
		os.flush();
		return byteOutStream.toByteArray();
	}
}