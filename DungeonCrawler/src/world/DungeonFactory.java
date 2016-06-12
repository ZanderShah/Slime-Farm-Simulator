package world;

import java.awt.Image;

import utility.Constants;
import utility.SpriteSheet;
import utility.Vector2D;
import enemy.Slime;

public class DungeonFactory
{
	private static int totalRooms;

	private static int randomWidth()
	{
		return (int) (Math.random() * (Constants.MAX_ROOM_WIDTH
				- Constants.MIN_ROOM_WIDTH + 1))
				+ Constants.MIN_ROOM_WIDTH;
	}

	private static int randomHeight()
	{
		return (int) (Math.random() * (Constants.MAX_ROOM_HEIGHT
				- Constants.MIN_ROOM_HEIGHT + 1))
				+ Constants.MIN_ROOM_HEIGHT;
	}

	public static Room[] generateMap(int numberOfRooms, int difficulty,
			int numberOfFloors)
	{
		Room entry[] = new Room[numberOfFloors];

		for (int floor = 0; floor < numberOfFloors; floor++)
		{
			entry[floor] = new Room(850, 200, randomWidth(), randomHeight(),
					difficulty, 0);
			totalRooms = 1;
			generateConnections(entry[floor], numberOfRooms - 1, difficulty);
			setBossRoom(entry[floor], new boolean[totalRooms], totalRooms - 1);
			addDoors(entry[floor], new boolean[totalRooms]);
			fillWithObjects(entry[floor], difficulty, new boolean[totalRooms]);
			fillWithEnemies(entry[floor], difficulty, new boolean[totalRooms]);
		}

		return entry;
	}

	private static void generateConnections(Room room, int left, int difficulty)
	{
		if (left == 0)
			return;

		int roomsConnected = (int) (Math.random() * (Math.min(left, 4))) + 1, successfulConnections = 0;
		boolean newLeft = false, newRight = false, newUp = false, newDown = false;

		for (int tries = 0; tries < 10000
				&& roomsConnected != successfulConnections; tries++)
		{
			int direction = (int) (Math.random() * 4) + 1;

			int width = randomWidth(), height = randomHeight();

			if (direction == Constants.LEFT && room.getLeft() == null
					&& fits(room, room.x() - width, room.y() + room.height()
							/ 2 - height / 2, width, height,
							new boolean[totalRooms]))
			{
				room.setLeft(new Room(room.x() - width, room.y()
						+ room.height()
						/ 2 - height / 2, width, height, difficulty, totalRooms));
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
									/ 2 - height / 2, width, height,
							new boolean[totalRooms]))
			{
				room.setRight(new Room(room.x() + room.width(),
						room.y() + room.height()
								/ 2 - height / 2, width, height, difficulty,
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

	private static void setBossRoom(Room room, boolean[] vis, int goal)
	{
		if (room == null || vis[room.id()] || vis[goal])
			return;

		vis[room.id()] = true;

		if (room.id() == goal)
			room.setBossRoom();

		setBossRoom(room.getUp(), vis, goal);
		setBossRoom(room.getDown(), vis, goal);
		setBossRoom(room.getLeft(), vis, goal);
		setBossRoom(room.getRight(), vis, goal);
	}

	private static void addDoors(Room room, boolean[] vis)
	{
		if (room == null || vis[room.id()])
			return;

		vis[room.id()] = true;

		if (room.getLeft() != null)
			room.setDoor(
					new LevelObject(new Vector2D(-SpriteSheet.DOORS[0]
							.getWidth(null) / 2, room.height() * 64 / 2
							- SpriteSheet.DOORS[0].getHeight(null) / 2), false,
							false, SpriteSheet.DOORS[0]), Constants.LEFT);
		if (room.getRight() != null)
			room.setDoor(
					new LevelObject(new Vector2D(room.width() * 64
							- SpriteSheet.DOORS[0]
									.getWidth(null) / 2, room
							.height() * 64 / 2
							- SpriteSheet.DOORS[0].getHeight(null) / 2), false,
							false,
							SpriteSheet.DOORS[0]), Constants.RIGHT);
		if (room.getUp() != null)
			room.setDoor(new LevelObject(new Vector2D(room.width() * 64 / 2
					- SpriteSheet.DOORS[1].getWidth(null) / 2,
					-SpriteSheet.DOORS[1].getHeight(null) / 2), false,
					false,
					SpriteSheet.DOORS[1]), Constants.UP);
		if (room.getDown() != null)
			room.setDoor(new LevelObject(new Vector2D(room.width() * 64 / 2
					- SpriteSheet.DOORS[1].getWidth(null) / 2, room.height()
					* 64
					- SpriteSheet.DOORS[1].getHeight(null) / 2),
					false,
					false,
					SpriteSheet.DOORS[1]), Constants.DOWN);

		addDoors(room.getUp(), vis);
		addDoors(room.getDown(), vis);
		addDoors(room.getLeft(), vis);
		addDoors(room.getRight(), vis);
	}

	private static void fillWithObjects(Room room, int difficulty, boolean[] vis)
	{
		if (room == null || vis[room.id()])
			return;

		vis[room.id()] = true;

		for (int i = 0; i < 50; i++)
		{
			Image img;

			boolean blocks = Math.round(Math.random()) == 0;
			if (blocks)
				img = SpriteSheet.random(SpriteSheet.BLOCKING_IMAGES);
			else
				img = SpriteSheet.random(SpriteSheet.DECORATIVE_IMAGES);

			int x = room.randomX(img), y = room.randomY(img);

			LevelObject lo = new LevelObject(new Vector2D(x, y), false, blocks,
					img);
			if (room.hasSpaceFor(lo.hitbox()))
				room.addLevelObject(lo);
		}

		fillWithObjects(room.getUp(), difficulty, vis);
		fillWithObjects(room.getDown(), difficulty, vis);
		fillWithObjects(room.getLeft(), difficulty, vis);
		fillWithObjects(room.getRight(), difficulty, vis);
	}

	private static void fillWithEnemies(Room room, int difficulty, boolean[] vis)
	{
		if (room == null || vis[room.id()])
			return;

		vis[room.id()] = true;

		for (int i = 0; i < 20; i++)
		{
			int x = room.randomX(SpriteSheet.ENEMIES[0]), y = room
					.randomY(SpriteSheet.ENEMIES[0]);
			Slime slimey = new Slime(x, y);
			if (room.hasSpaceFor(slimey.getHitbox()))
				room.addEnemy(slimey);
		}

		fillWithEnemies(room.getUp(), difficulty, vis);
		fillWithEnemies(room.getDown(), difficulty, vis);
		fillWithEnemies(room.getLeft(), difficulty, vis);
		fillWithEnemies(room.getRight(), difficulty, vis);
	}
}