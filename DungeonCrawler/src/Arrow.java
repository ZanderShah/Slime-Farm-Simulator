public class Arrow extends Projectile {
	
	public Arrow(Vector2D pos, Vector2D dir) {
		super(pos, dir.getNormalized().multiply(4.0));
	}
}