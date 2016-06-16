package utility;

public class Constants
{
	public static final boolean DEBUG = false;
	public static final boolean OFFLINE = true;

	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	public static final Vector2D MIDDLE = new Vector2D(SCREEN_WIDTH / 2,
			SCREEN_HEIGHT / 2);
	public static final int CLIENT_PORT = 7382;
	public static final int SERVER_PORT = 7383;

	public static final long TIMEOUT = 10000;

	public static final int NUMBER_OF_ROOMS = 12, NUMBER_OF_FLOORS = 5;
	public static final int RIGHT = 1, UP = 2, LEFT = 3, DOWN = 4;
	public static final int MIN_ROOM_WIDTH = 10, MAX_ROOM_WIDTH = 20,
			MIN_ROOM_HEIGHT = 10, MAX_ROOM_HEIGHT = 20;

	// I feel like putting all this stuff into a file makes balancing easier

	public static final boolean TRIPPY_SLIMES = true;

	// Class max healths
	public static final double WARRIOR_HEALTH = 200;
	public static final double THIEF_HEALTH = 100;
	public static final double MAGE_HEALTH = 150;
	public static final double TANK_HEALTH = 400;
	public static final double HUNTER_HEALTH = 150;
	public static final double CLERIC_HEALTH = 200;

	// Class speeds
	public static final double WARRIOR_SPEED = 2.0;
	public static final double THIEF_SPEED = 2.5;
	public static final double MAGE_SPEED = 2.0;
	public static final double TANK_SPEED = 1.25;
	public static final double HUNTER_SPEED = 2.0;
	public static final double CLERIC_SPEED = 2.0;

	// Class attack speeds (basic attack, delay between consecutive attacks)
	public static final int WARRIOR_ATTACK_SPEED = 60;
	public static final int THIEF_ATTACK_SPEED = 40;
	public static final int MAGE_ATTACK_SPEED = 2;
	public static final int TANK_ATTACK_SPEED = 100;
	public static final int HUNTER_ATTACK_SPEED = 60;
	public static final int CLERIC_ATTACK_SPEED = 150;

	// Class attack lengths (basic attack, duration of usage)
	public static final int WARRIOR_ATTACK_LENGTH = 40;
	public static final int THIEF_ATTACK_LENGTH = 15;
	public static final int MAGE_ATTACK_LENGTH = 1;
	public static final int TANK_ATTACK_LENGTH = 80;
	public static final int HUNTER_ATTACK_LENGTH = 40;
	public static final int CLERIC_ATTACK_LENGTH = 40;

	// Class defence stats
	public static final double WARRIOR_DEFENCE = 20.0;
	public static final double THIEF_DEFENCE = 10.0;
	public static final double MAGE_DEFENCE = 15.0;
	public static final double TANK_DEFENCE = 30.0;
	public static final double HUNTER_DEFENCE = 15.0;
	public static final double CLERIC_DEFENCE = 20.0;

	// Warrior specific stats
	public static final int WARRIOR_SWORD_SIZE = 64;
	public static final int WARRIOR_SWING_ANGLE = 120;
	public static final double WARRIOR_DAMAGE = 30.0;
	public static final int WARRIOR_KNOCKBACK = 20;
	public static final int WARRIOR_RAGE_LENGTH = 500;
	public static final double WARRIOR_RAGE_HEAL = 3.0;

	// Thief specific stats
	public static final int THIEF_SWORD_SIZE = 48;
	public static final int THIEF_SWING_ANGLE = 30;
	public static final double THIEF_DAMAGE = 40.0;
	public static final int THIEF_KNOCKBACK = 15;
	public static final double KNIFE_SPEED = 8.0;
	public static final double KNIFE_DAMAGE = 50.0;
	public static final int KNIFE_KNOCKBACK = 5;
	public static final int KNIFE_SPREAD = 5;

	// Mage specific stats
	public static final int MAGE_SPRAY = 20;
	public static final double MAGE_DAMAGE = 1.0;
	public static final int MAGE_DEBUFF_RANGE = 250;
	public static final int MAGE_DEBUFF_LENGTH = 480;
	public static final int MAGE_FIRE_RANGE = 100;
	public static final int MAGE_FIRE_LENGTH = 300;

	// Tank specific stats
	public static final int TANK_SWORD_SIZE = 80;
	public static final int TANK_SWING_ANGLE = 140;
	public static final double TANK_DAMAGE = 50.0;
	public static final int TANK_KNOCKBACK = 50;
	public static final int TANK_BUFF_LENGTH = 600;
	public static final double TANK_BUFF_STRENGTH = 1.5;
	public static final int TANK_STUN_RANGE = 150;
	public static final int TANK_STUN_LENGTH = 240;
	public static final int TANK_REFLECT_TIME = 120;

	// Hunter specific stats
	public static final double ARROW_SPEED = 6.0;
	public static final double ARROW_DAMAGE = 25.0;
	public static final double PIERCING_ARROW_DAMAGE = 50.0;
	public static final double POISON_ARROW_DAMAGE = 50.0;
	public static final int ARROW_KNOCKBACK = 10;

	// Cleric specific stats
	public static final int CLERIC_SWORD_SIZE = 60;
	public static final int CLERIC_SWING_ANGLE = 100;
	public static final double CLERIC_DAMAGE = 40.0;
	public static final int CLERIC_HEAL_LENGTH = 900;
	public static final double CLERIC_HEAL_STRENGTH = 2.0;
	public static final int CLERIC_BURST_LENGTH = 20;
	public static final double CLERIC_BURST_STRENGTH = 20.0;
	public static final double BOLT_SPEED = 1.0;
	public static final double BOLT_DAMAGE = 40.0;
	public static final int BOLT_KNOCKBACK = 20;
	public static final int CLERIC_INVULNERABLE_LENGTH = 400;

	public static final int[][] AB_COOLDOWNS = { { 360, 2500, 300 }, // Warrior
			{ 600, 90, 300 }, // Thief
			{ 600, 900, 1200 }, // Mage
			{ 1500, 900, 1200 }, // Tank
			{ 600, 600, 600 }, // Hunter
			{ 1200, 1800, 2500 } }; // Cleric

	public static final int[] EXPERIENCE_REQUIRED = { 10, 20, 30, 50, 80, 130,
			210, 340, 550 };
	public static final double[] LEVEL_HEALTH = { 1.3, 1.1, 1, 1.4, 1.1, 1.1 };
	public static final double[] LEVEL_DAMAGE = { 1.2, 1.2, 1.025, 1.2, 1.3,
			1.2 };
	public static final double[] LEVEL_DEFENCE = { 1.2, 1.1, 1.1, 1.3, 1.1, 1.1 };
	public static final double[] LEVEL_SPEED = { 1.025, 1.075, 1.025, 1.025,
			1.025, 1.025 };

	// Slime specific stats
	public static final double SLIMEBALL_SPEED = 3.0;
	public static final double POISON_SLIMEBALL_SPEED = 1.5;
	public static final double SLIMEBALL_DAMAGE = 20.0;
}
