import java.util.ArrayList;


public class Floor
{
	private ArrayList<Room> rooms;
	private ArrayList<Integer>[] adj;
	
	public Floor(int n, int d)
	{
		adj = DungeonFactory.generateConnections(n);
		rooms = DungeonFactory.fillRooms(adj, d);
	}
}
