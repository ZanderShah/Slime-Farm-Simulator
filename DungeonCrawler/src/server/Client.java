package server;

import java.net.DatagramPacket;
import java.net.InetAddress;

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
	private InetAddress address;
	private Player p;
	private boolean host;
	
	public Client(InetAddress addr) {
		address = addr;
		p = new Warrior();
		host = false;
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

	public void update(ControlState cs, Room currentRoom) {
		p.update(cs, currentRoom);
	}

	public void sendUpdate(Room r) {
		DatagramPacket dp = new DatagramPacket(null, 0, address, 7382);
	}

	public void setHost(boolean b) {
		host = b;
	}

	public boolean isHost() {
		return host;
	}
}
