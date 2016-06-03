public class Projectile extends DamageSource {

	private Vector2D position;
	private Vector2D speed;
	
	public Projectile(Hitbox h, Vector2D pos, Vector2D spd, boolean single) {
		super(h, 0, 999999999, single);
		position = pos;
		speed = spd;
	}
	
	@Override
	public void update(Room r) {
		position.addToThis(speed);
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public Vector2D getSpeed() {
		return speed;
	}
}