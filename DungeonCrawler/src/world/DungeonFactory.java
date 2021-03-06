package world;

import java.awt.Image;
import java.util.Random;

import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;
import enemy.KingSlime;
import enemy.Slime;
import enemy.SlimeFactory;

/**
 * Manages creation of dungeon
 *
 * @author Alexander Shah
 * @version Jun 15, 2016
 */
public class DungeonFactory
{
	private static Random rng;
	private static int totalRooms;

	private static int randomWidth()
	{
		return (int) (rng.nextDouble() * (Constants.MAX_ROOM_WIDTH
				- Constants.MIN_ROOM_WIDTH + 1))
				+ Constants.MIN_ROOM_WIDTH;
	}

	private static int randomHeight()
	{
		return (int) (rng.nextDouble() * (Constants.MAX_ROOM_HEIGHT
				- Constants.MIN_ROOM_HEIGHT + 1))
				+ Constants.MIN_ROOM_HEIGHT;
	}

	/**
	 * Generates an array of entrance rooms
	 * @param numberOfRooms the number of rooms in each floor
	 * @param difficulty the difficulty base difficulty
	 * @param numberOfFloors the number of floors
	 * @param seed seed for random number generation
	 * @return an array of entrance rooms
	 */
	public static Room[] generateMap(int numberOfRooms, int difficulty,
			int numberOfFloors, long seed)
	{
		rng = new Random(seed);
		Room entry[] = new Room[numberOfFloors];
		for (int floor = 0; floor < numberOfFloors; floor++)
			entry[floor] = new Room(900, 100, randomWidth(), randomHeight(),
					difficulty, 0);

		for (int floor = 0; floor < numberOfFloors; floor++)
		{
			totalRooms = 1;
			generateConnections(entry[floor], numberOfRooms - 1, difficulty
					+ floor);
			setBossRoom(entry[floor], new boolean[totalRooms], totalRooms - 1,
					(floor + 1 < numberOfFloors ? entry[floor + 1] : null));
			addDoors(entry[floor], new boolean[totalRooms]);
			fillWithDecorativeObjects(entry[floor],
					new boolean[totalRooms]);
			fillWithBlockingObjects(entry[floor], difficulty,
					new boolean[totalRooms]);
			fillWithEnemies(entry[floor], difficulty, new boolean[totalRooms]);
		}

		return entry;
	}

	/**
	 * Recursively generates connections between rooms
	 * @param room the current room
	 * @param left how many rooms still need to be placed
	 * @param difficulty the difficulty of the floor
	 */
	private static void generateConnections(Room room, int left,
			int difficulty)
	{
		if (left == 0)
			return;

		int roomsConnected = (int) (rng.nextDouble() * (Math.min(left, 4))) + 1, successfulConnections = 0;
		boolean newLeft = false, newRight = false, newUp = false, newDown = false;

		for (int tries = 0; tries < 10000
				&& roomsConnected != successfulConnections; tries++)
		{
			int direction = (int) (rng.nextDouble() * 4) + 1;

			int width = randomWidth(), height = randomHeight();

			if (direction == Constants.LEFT && room.getLeft() == null
					&& fits(room, room.x() - width, room.y() + room.height()
							/ 2 - height / 2, width, height,
							new boolean[totalRooms]))
			{
				room.setLeft(new Room(room.x() - width, room.y()
						+ room.height()
						/ 2
						- height / 2, width, height, difficulty, totalRooms));
				room.getLeft().setRight(room);
				totalRooms++;
				successfulConnections++;
				newLeft = true;
				// System.out.printf("Room %d placed LEFT of Room %d%n",
				// room.getLeft().id(), room.id());
			}
			else if (direction == Constants.UP && room.getUp() == null
					&& fits(room, room.x() + room.width() / 2 - width / 2,
							room.y() - height, width, height,
							new boolean[totalRooms]))
			{
				room.setUp(new Room(room.x() + room.width() / 2 - width / 2,
						room.y() - height, width, height, difficulty,
						totalRooms));
				room.getUp().setDown(room);
				totalRooms++;
				successfulConnections++;
				newUp = true;
				// System.out.printf("Room %d placed UP of Room %d%n",
				// room.getUp().id(), room.id());
			}
			else if (direction == Constants.RIGHT && room.getRight() == null
					&& fits(room, room.x() + room.width(),
							room.y() + room.height()
									/ 2 - height / 2,
							width, height,
							new boolean[totalRooms]))
			{
				room.setRight(new Room(room.x() + room.width(),
						room.y() + room.height()
								/ 2 - height / 2,
						width, height, difficulty,
						totalRooms));
				room.getRight().setLeft(room);
				totalRooms++;
				successfulConnections++;
				newRight = true;
				// System.out.printf("Room %d placed RIGHT of Room %d%n",
				// room.getRight().id(), room.id());
			}
			else if (direction == Constants.DOWN && room.getDown() == null
					&& fits(room, room.x() + room.width() / 2 - width / 2,
							room.y() + room.height(), width,
							height, new boolean[totalRooms]))
			{

				room.setDown(new Room(room.x() + room.width() / 2 - width / 2,
						room.y() + room.height(), width, height, difficulty,
						totalRooms));
				room.getDown().setUp(room);
				totalRooms++;
				successfulConnections++;
				newDown = true;
				// System.out.printf("Room %d placed DOWN of Room %d%n",
				// room.getDown().id(), room.id());
			}
		}

		if (successfulConnections == 0)
			return;

		int split = (left - successfulConnections) / successfulConnections;

		if (newLeft)
			generateConnections(room.getLeft(), split, difficulty);
		if (newUp)
			generateConnections(room.getUp(), split, difficulty);
		if (newRight)
			generateConnections(room.getRight(), split, difficulty);
		if (newDown)
			generateConnections(room.getDown(), split, difficulty);
	}

	/**
	 * Recursively checks whether a room will fit in the current floor
	 * @param room the current room
	 * @param x x-coordinate of the room
	 * @param y y-coordinate of the room
	 * @param width the width of the room
	 * @param height the height of the room
	 * @param vis boolean array of visited rooms
	 * @return whether or not the room can fit in the current floor
	 */
	private static boolean fits(Room room, int x, int y, int width, int height,
			boolean[] vis)
	{
		if (room.x() >= x + width || room.x() + room.width() <= x
				|| room.y() >= y + height || room.y() + room.height() <= y)
		{
			vis[room.id()] = true;

			boolean ret = true;

			if (room.getRight() != null && !vis[room.getRight().id()]
					&& !fits(room.getRight(), x, y, width, height, vis))
				ret = false;
			if (room.getUp() != null && !vis[room.getUp().id()]
					&& !fits(room.getUp(), x, y, width, height, vis))
				ret = false;
			if (room.getLeft() != null && !vis[room.getLeft().id()]
					&& !fits(room.getLeft(), x, y, width, height, vis))
				ret = false;
			if (room.getDown() != null && !vis[room.getDown().id()]
					&& !fits(room.getDown(), x, y, width, height, vis))
				ret = false;

			return ret;
		}

		// System.out.printf("Overlap :( %d %d %d %d %d %d %d %d%n", room.x(),
		// room.y(), room.width(), room.height(), x, y, width, height);

		return false;
	}

	/**
	 * Recursively searches for a specific room to make the boss room
	 * @param room the current room
	 * @param vis boolean array of visited rooms
	 * @param goal where the boss room should be
	 * @param nextLevel the room that the boss room will lead to
	 */
	private static void setBossRoom(Room room, boolean[] vis, int goal,
			Room nextLevel)
	{
		if (room == null || vis[room.id()] || vis[goal])
			return;

		vis[room.id()] = true;

		if (room.id() == goal)
		{
			room.setBossRoom(nextLevel);
			KingSlime kingSlimey = new KingSlime(room.width() / 2 * 64,
					room.height() / 2 * 64, 0);
			room.addEnemy(kingSlimey);
			kingSlimey.addDamage(room);
		}

		setBossRoom(room.getUp(), vis, goal, nextLevel);
		setBossRoom(room.getDown(), vis, goal, nextLevel);
		setBossRoom(room.getLeft(), vis, goal, nextLevel);
		setBossRoom(room.getRight(), vis, goal, nextLevel);
	}

	/**
	 * Adds doors to rooms
	 * @param room the current room
	 * @param vis boolean array of visited rooms
	 */
	private static void addDoors(Room room, boolean[] vis)
	{
		if (room == null || vis[room.id()])
			return;

		vis[room.id()] = true;

		if (room.getLeft() != null)
			room.setDoor(
					new LevelObject(new Vector2D(
							-SpriteSheet.DOORS[Constants.LEFT]
									.getWidth(null), room.height()
									* 64
									/ 2
									- SpriteSheet.DOORS[Constants.LEFT]
											.getHeight(null) / 2),
							false,
							false, SpriteSheet.DOORS[Constants.LEFT]),
					Constants.LEFT);
		if (room.getRight() != null)
			room.setDoor(
					new LevelObject(
							new Vector2D(
									room.width() * 64,
									room
											.height()
											* 64
											/ 2
											- SpriteSheet.DOORS[Constants.RIGHT]
													.getHeight(null) / 2),
							false,
							false,
							SpriteSheet.DOORS[Constants.RIGHT]),
					Constants.RIGHT);
		if (room.getUp() != null)
			room.setDoor(new LevelObject(new Vector2D(room.width() * 64 / 2
					- SpriteSheet.DOORS[Constants.UP].getWidth(null) / 2,
					-SpriteSheet.DOORS[Constants.UP].getHeight(null)), false,
					false,
					SpriteSheet.DOORS[Constants.UP]), Constants.UP);
		if (room.getDown() != null)
			room.setDoor(new LevelObject(new Vector2D(room.width() * 64 / 2
					- SpriteSheet.DOORS[Constants.DOWN].getWidth(null) / 2,
					room.height()
					* 64),
					false,
					false,
					SpriteSheet.DOORS[Constants.DOWN]), Constants.DOWN);

		addDoors(room.getUp(), vis);
		addDoors(room.getDown(), vis);
		addDoors(room.getLeft(), vis);
		addDoors(room.getRight(), vis);
	}

	/**
	 * Recursively fills rooms with random decorative objects
	 * @param room the current room
	 * @param vis boolean array of visited rooms
	 */
	private static void fillWithDecorativeObjects(Room room,
			boolean[] vis)
	{
		if (room == null || vis[room.id()])
			return;

		vis[room.id()] = true;

		for (int i = 0; i < 25; i++)
		{
			Image img = SpriteSheet.random(SpriteSheet.DECORATIVE_IMAGES, rng);

			int x = room.randomX(img, rng), y = room.randomY(img, rng);

			LevelObject lo = new LevelObject(new Vector2D(x, y), false, false,
					img);
			if (room.hasSpaceFor(lo.hitbox(), false))
				room.addLevelObject(lo);
		}

		fillWithDecorativeObjects(room.getUp(), vis);
		fillWithDecorativeObjects(room.getDown(), vis);
		fillWithDecorativeObjects(room.getLeft(), vis);
		fillWithDecorativeObjects(room.getRight(), vis);
	}

	/**
	 * Recursively fills rooms with random blocking objects
	 * @param room the current room
	 * @param difficulty the difficulty of the room
	 * @param vis boolean array of visited rooms
	 */
	private static void fillWithBlockingObjects(Room room, int difficulty,
			boolean[] vis)
	{
		if (room == null || vis[room.id()] || room.isBossRoom())
			return;

		vis[room.id()] = true;

		for (int i = 0; i < 25; i++)
		{
			Image img = SpriteSheet.random(SpriteSheet.BLOCKING_IMAGES, rng);

			int x = room.randomX(img, rng), y = room.randomY(img, rng);

			LevelObject lo = new LevelObject(new Vector2D(x, y), false, true,
					img);
			if (room.hasSpaceFor(lo.hitbox(), false))
				room.addLevelObject(lo);
		}

		fillWithBlockingObjects(room.getUp(), difficulty, vis);
		fillWithBlockingObjects(room.getDown(), difficulty, vis);
		fillWithBlockingObjects(room.getLeft(), difficulty, vis);
		fillWithBlockingObjects(room.getRight(), difficulty, vis);
	}

	/**
	 * Recursively fills rooms with enemies
	 * @param room the current room
	 * @param difficulty the difficulty of the room
	 * @param vis boolean array of visited rooms
	 */
	private static void fillWithEnemies(Room room, int difficulty,
			boolean[] vis)
	{
		if (room == null || vis[room.id()])
			return;

		vis[room.id()] = true;

		for (int i = 0; i < 5 * (difficulty + 1); i++)
		{
			int x = room.randomX(SpriteSheet.ENEMIES[0], rng), y = room
					.randomY(SpriteSheet.ENEMIES[0], rng);
			Slime slimey = SlimeFactory.getSlime(x, y);

			if (room.hasSpaceFor(slimey.getHitbox(), true))
			{
				room.addEnemy(slimey);
				slimey.addDamage(room);
			}
		}

		fillWithEnemies(room.getUp(), difficulty, vis);
		fillWithEnemies(room.getDown(), difficulty, vis);
		fillWithEnemies(room.getLeft(), difficulty, vis);
		fillWithEnemies(room.getRight(), difficulty, vis);
	}
}