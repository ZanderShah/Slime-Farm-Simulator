package engine;
import utility.Vector2D;
import world.Room;

public abstract class Projectile extends DamageSource {

	private Vector2D position;
	private Vector2D speed;
	
	public Projectile(Hitbox h, int frequency, int duration, Vector2D pos, Vector2D spd, boolean single, boolean player, double damage) {
		super(h, frequency, duration, single, player, damage);
		position = pos;
		speed = spd;
	}
	
	public Projectile(Hitbox h, int frequency, int duration, Vector2D pos, Vector2D spd, boolean single, boolean player, double damage, StatusEffect effect) {
		super(h, frequency, duration, single, player, damage, effect);
		position = pos;
		speed = spd;
	}

	@Override
	public void update(Room r) {
		super.update(r);
		position.addToThis(speed);
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getSpeed() {
		return speed;
	}
}