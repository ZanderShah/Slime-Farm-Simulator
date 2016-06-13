package app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import engine.LivingEntity;
import player.Hunter;
import player.Player;
import utility.SpriteSheet;
import utility.Vector2D;

public class SerialTest {
	
	public static void main(String[] args) throws Exception {
		SpriteSheet.initializeImages();
		LivingEntity p = new Hunter();
		p.setPos(new Vector2D(30, 30));
		
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.flush();
		o.writeObject(p);
		o.flush();
		byte[] by = b.toByteArray();
		ByteArrayInputStream bi = new ByteArrayInputStream(by);
		ObjectInputStream i = new ObjectInputStream(bi);
		
		Player np = (Player) i.readObject();
		System.out.println(np.getPos());
	}
}
