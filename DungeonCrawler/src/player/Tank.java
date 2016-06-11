package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import engine.Stats;
import engine.StatusEffect;
import utility.Constants;
import utility.Vector2D;
import world.Room;

public class Tank extends Player {

	public Tank() {
		super();
		setStats(new Stats(Constants.TANK_HEALTH, Constants.TANK_ATTACK_SPEED, Constants.TANK_ATTACK_LENGTH, Constants.TANK_SPEED, Constants.TANK_DEFENCE));
	}

	@Override
	public void draw(Graphics g, Vector2D offset) {
		Vector2D shifted = getPos().add(offset);
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) shifted.getX() - getWidth() / 2, (int) shifted.getY() - getHeight() / 2, getWidth(), getHeight());
	}

	@Override
	public boolean attack(Point p, Room r) {
		return super.attack(p, r);
	}

	// Shield spell: gives all players a 1.5x defensive buff for 10 seconds
	// Cooldown: 25 seconds
	@Override
	public void ability1(Point p, Room r) {
		if (getCooldown(1) == 0) {
			ArrayList<Player> players = r.getPlayers();
			for (int i = 0; i < players.size(); i++) {
				players.get(i).giveStatusEffect(new StatusEffect(Constants.TANK_BUFF_LENGTH, 0, Constants.TANK_BUFF_STRENGTH, StatusEffect.DEF, true));
				setCooldown(1, Constants.TANK_AB1_COOLDOWN);
			}
		}
	}

	@Override
	public void ability2(Point p, Room r) {
		
	}

	@Override
	public void ability3(Point p, Room r) {
		
	}

	@Override
	public int getWidth() {
		return 64;
	}

	@Override
	public int getHeight() {
		return 64;
	}
}