package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import player.Cleric;
import player.Hunter;
import player.Mage;
import player.Player;
import player.Tank;
import player.Thief;
import player.Warrior;
import utility.ControlState;
import world.Room;

public class Client {
	private DatagramSocket sock;
	private InetAddress address;
	private Player p;
	private boolean host;
	private long lastPacket;
	private ControlState cs;
	
	public Client(InetAddress addr) {
		try {
			sock = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		address = addr;
		p = new Warrior();
		host = false;
		lastPacket = System.currentTimeMillis();
		cs = new ControlState();
	}
	
	public void updatePacketTime() {
		lastPacket = System.currentTimeMillis();
	}
	
	public long getPacketTime() {
		return lastPacket;
	}
	
	public void chooseClass(int c) {
		switch (c) {
		case 0:
			p = new Warrior();
			break;
		case 1:
			p = new Thief();
			break;
		case 2:
			p = new Mage();
			break;
		case 3:
			p = new Tank();
			break;
		case 4:
			p = new Hunter();
			break;
		case 5:
			p = new Cleric();
			break;
		}
	}

	public void update(Room currentRoom) {
		p.update(cs, currentRoom);
	}

	public void sendUpdate(Room r) {
		DatagramPacket dp = new DatagramPacket(null, 0, address, 7382);
		try {
			sock.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setHost(boolean b) {
		host = b;
	}

	public boolean isHost() {
		return host;
	}

	public void setControls(ControlState cs) {
		this.cs = cs;
	}

	public Player getPlayer() {
		return p;
	}
}
