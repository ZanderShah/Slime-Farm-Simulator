package engine;

import utility.Vector2D;
import world.Room;

public class ParticleEmitter {
	private int type;
	private Vector2D position;
	private Vector2D direction;
	private int lifetime;
	private int particleLifetime;
	private int frequency;
	private int particleFrequency;
	private int counter;
	private double randomPos;
	private double randomDir;
	private int randomLife;

	public ParticleEmitter(int t, Vector2D p, Vector2D d, int l, int pl, int f, int pf, double rp, double rd, int rl) {
		type = t;
		position = p;
		direction = d;
		lifetime = l;
		particleLifetime = pl;
		frequency = f;
		particleFrequency = pf;
		counter = f;
		randomPos = rp;
		randomDir = rd;
		randomLife = rl;
	}
	
	public boolean isDead() {
		return lifetime <= 0;
	}

	public void update(Room r) {
		if (lifetime > 0) lifetime--;
		if (counter > 0) counter--;
		if (counter == 0 && lifetime > 0) {
			counter = frequency;
			int l = particleLifetime + (int) (Math.random() * (randomLife * 2 + 1)) - randomLife;
			Vector2D p = position.add((new Vector2D(Math.random() * 2 - 1, Math.random() * 2 - 1).getNormalized()).multiply(Math.random() * randomPos));
			Vector2D d = direction.add((new Vector2D(Math.random() * 2 - 1, Math.random() * 2 - 1).getNormalized()).multiply(Math.random() * randomDir));
			r.addParticle(new Particle(type, particleFrequency, l, p, d));
		}
	}
}
