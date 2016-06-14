package enemy;

import java.util.ArrayList;

import player.Player;
import utility.Vector2D;
import world.Room;

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
		return (targets.size() == 0 ? null : targets.get(best).getPos()
				.subtract(pos).getNormalized());
	}
}
