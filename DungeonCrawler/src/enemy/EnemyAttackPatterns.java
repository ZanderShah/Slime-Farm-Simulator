package enemy;

import java.util.ArrayList;

import player.Player;
import utility.Vector2D;
import world.Room;

/**
 * Static methods to return vectors matching different attack patterns
 *
 * @author Alexander Shah
 * @version Jun 15, 2016
 */
public class EnemyAttackPatterns
{
	static Vector2D runTowardsPlayer(Room r, Vector2D pos)
	{
		ArrayList<Player> targets = r.getPlayers();
		int best = 0;
		for (int i = 1; i < targets.size(); i++)
			if (targets.get(i).getPos().subtract(pos).getLength() < targets
					.get(best).getPos().subtract(pos).getLength())
				best = i;
		return (targets.size() == 0 ? random() : targets.get(best).getPos()
				.subtract(pos).getNormalized());
	}

	static Vector2D random()
	{
		return new Vector2D(Math.random() * 360);
	}
}
