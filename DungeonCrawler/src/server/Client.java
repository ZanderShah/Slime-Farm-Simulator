package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import player.Player;
import player.Warrior;
import utility.Constants;
import utility.ControlState;
import utility.Vector2D;
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
		p = Player.makePlayer(c);
		p.setPos(new Vector2D(40, 40));
	}

	public void update(Room currentRoom) {
		p.update(cs, currentRoom);
	}

	public void sendRoom(Room r) {
		DatagramPacket dp = new DatagramPacket(null, 0, address, Constants.CLIENT_PORT);
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

	public InetAddress getAddress() {
		return address;
	}

	public void send(byte[] message) throws Exception {
		sock.send(new DatagramPacket(message, message.length, address, Constants.CLIENT_PORT));
	}
}
