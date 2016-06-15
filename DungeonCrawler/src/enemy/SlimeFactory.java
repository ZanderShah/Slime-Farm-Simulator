package enemy;

import world.Room;

public class SlimeFactory
{
	public static Slime getSlime(int x, int y)
	{
		int slimeType = (int) (Math.random() * 2);
		
		if (slimeType == 0)
		{
			return new BlueSlime(x, y);
		}
		else if (slimeType == 1)
		{
			return new RedSlime(x, y);
		}
		
		return null;
	}
}
