package world;
import java.awt.Image;

import utility.SpriteSheet;
import utility.Vector2D;

public class DungeonFactory
{
	private static final int MIN_ROOM_WIDTH = 20, MAX_ROOM_WIDTH = 30,
			MIN_ROOM_HEIGHT = 20, MAX_ROOM_HEIGHT = 30;
	private static final int RIGHT = 1, UP = 2, LEFT = 3, DOWN = 4;
	private static int totalRooms;

	private static int randomWidth()
	{
		return (int) (Math.random() * (MAX_ROOM_WIDTH - MIN_ROOM_WIDTH + 1))
				+ MIN_ROOM_WIDTH;
	}

	private static int randomHeight()
	{
		return (int) (Math.random() * (MAX_ROOM_HEIGHT - MIN_ROOM_HEIGHT + 1))
				+ MIN_ROOM_HEIGHT;
	}

	public static Room generateMap(int numberOfRooms, int difficulty)
	{
		Room entry = new Room(850, 850, randomWidth(), randomHeight(),
				difficulty, 0);
		totalRooms = 1;
		generateConnections(entry, numberOfRooms - 1, difficulty);
		fillWithObjects(entry, difficulty, new boolean[totalRooms]);
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

			if (direction == LEFT && room.getLeft() == null
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
			else if (direction == UP && room.getUp() == null
					&& fits(room, room.x() + room.width() / 2 - width / 2,
							room.y() + room.height(), width,
							height, new boolean[totalRooms]))
			{
				room.setUp(new Room(room.x() + room.width() / 2 - width / 2,
						room.y() + room.height(), width, height, difficulty,
						totalRooms));
				room.getUp().setDown(room);
				totalRooms++;
				successfulConnections++;
				newUp = true;
				// System.out.printf("Room %d placed UP of Room %d%n",
				// room.getUp().id(), room.id());
			}
			else if (direction == RIGHT && room.getRight() == null
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
			else if (direction == DOWN && room.getDown() == null
					&& fits(room, room.x() + room.width() / 2 - width / 2,
							room.y() - height, width, height,
							new boolean[totalRooms]))
			{
				room.setDown(new Room(room.x() + room.width() / 2 - width / 2,
						room.y() - height, width, height, difficulty,
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

	private static void fillWithObjects(Room room, int difficulty, boolean[] vis)
	{
		if (room == null || vis[room.id()])
			return;

		vis[room.id()] = true;

		for (int i = 0; i < 50; i++)
		{
			Image img; 
			int x = room.randomX(), y = room.randomY();
			boolean blocks = Math.round(Math.random()) == 0;
			if (!blocks)
				img = SpriteSheet.random(SpriteSheet.DECORATIVE_IMAGES);
			else
				img = SpriteSheet.random(SpriteSheet.BLOCKING_IMAGES);

			LevelObject lo = new LevelObject(new Vector2D(x, y), false, blocks,
					img);
			if (room.hasSpaceFor(lo))
				room.addLevelObject(lo);
		}

		fillWithObjects(room.getUp(), difficulty, vis);
		fillWithObjects(room.getDown(), difficulty, vis);
		fillWithObjects(room.getLeft(), difficulty, vis);
		fillWithObjects(room.getRight(), difficulty, vis);
	}
}