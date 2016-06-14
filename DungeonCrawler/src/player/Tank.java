package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import engine.ParticleEmitter;
import engine.Stats;
import engine.StatusEffect;
import engine.damage.TankStun;
import utility.Constants;
import utility.Vector2D;
import world.Room;

public class Tank extends Player
{

	private int reflecting;

	public Tank()
	{
		super(3);
		setStats(new Stats(Constants.TANK_HEALTH, Constants.TANK_ATTACK_SPEED,
				Constants.TANK_ATTACK_LENGTH, Constants.TANK_SPEED,
				Constants.TANK_DEFENCE));
		reflecting = 0;
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) shifted.getX() - getWidth() / 2, (int) shifted.getY()
				- getHeight() / 2, getWidth(), getHeight());
	}

	@Override
	public boolean attack(Point p, Room r)
	{
		return super.attack(p, r);
	}

	@Override
	public void update(Room r)
	{
		if (reflecting > 0)
			reflecting--;
		super.update(r);
	}

	public boolean isReflecting()
	{
		return reflecting > 0;
	}

	// Shield spell: gives all players a 1.5x defensive buff for 10 seconds
	// Cooldown: 25 seconds
	@Override
	public void ability1(Point p, Room r)
	{
		if (getCooldown(1) == 0)
		{
			setAbilityActive(1, Constants.TANK_BUFF_LENGTH);
			ArrayList<Player> players = r.getPlayers();
			for (int i = 0; i < players.size(); i++)
			{
				players.get(i).giveStatusEffect(
						new StatusEffect(Constants.TANK_BUFF_LENGTH, 0,
								Constants.TANK_BUFF_STRENGTH, StatusEffect.DEF,
								true));
			}
		}
	}

	// Stun: stuns all nearby enemies
	// Cooldown 15 seconds
	@Override
	public void ability2(Point p, Room r)
	{
		if (getCooldown(2) == 0)
		{
			r.addEmitter(new ParticleEmitter(0, getPos(), new Vector2D(), 2,
					40, 0, 0, 80, Constants.TANK_STUN_RANGE, 0, 40));
			r.addDamageSource(new TankStun(getPos(), Constants.TANK_STUN_RANGE,
					true, getID()), getStats().getDamageMultiplier());
			setCooldown(2, Constants.AB_COOLDOWNS[3][1]);
		}
	}

	// Reflect: hide behind your shield and reflect all projectiles (also turn
	// them friendly)
	// Cooldown: 20 seconds
	@Override
	public void ability3(Point p, Room r)
	{
		if (getCooldown(3) == 0)
		{
			setAbilityActive(0, Constants.TANK_REFLECT_TIME);
			setAbilityActive(3, Constants.TANK_REFLECT_TIME);
			reflecting = Constants.TANK_REFLECT_TIME;
		}
	}

	@Override
	public int getWidth()
	{
		return 64;
	}

	@Override
	public int getHeight()
	{
		return 64;
	}
}