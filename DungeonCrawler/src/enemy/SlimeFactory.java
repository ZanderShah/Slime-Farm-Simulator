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
		int slimeType = (int) (Math.random() * 5);

		if (slimeType == 0)
		{
			return new BlueSlime(x, y);
		}
		else if (slimeType == 1)
		{
			return new RedSlime(x, y);
		}
		else if (slimeType == 2)
		{
			return new GreenSlime(x, y);
		}
		else if (slimeType == 3)
		{
			return new GoldSlime(x, y);
		}
		else if (slimeType == 4)
		{
			return new PurpleSlime(x, y);
		}

		return null;
	}
}
