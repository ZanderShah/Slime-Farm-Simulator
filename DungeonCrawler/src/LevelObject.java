public class LevelObject {
	private Vector2D position;
	private boolean destructable;
	private int width, height;
	
	public LevelObject(Vector2D pos) {
		position = pos;
	}
	
	public Vector2D getPos() {
		return position;
	}
}