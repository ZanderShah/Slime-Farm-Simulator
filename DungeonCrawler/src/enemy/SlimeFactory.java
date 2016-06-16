package enemy;

import world.Room;

/**
 * Static method for generating random slimes
 *
 * @author Alexander Shah
 * @version Jun 15, 2016
 */
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
