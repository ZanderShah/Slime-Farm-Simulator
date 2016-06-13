package utility;

public class Constants
{
	public static final boolean DEBUG = true;
	public static final boolean OFFLINE = true;
	
	
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 768;
	public static final Vector2D MIDDLE = new Vector2D(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	public static final int CLIENT_PORT = 7382;
	public static final int SERVER_PORT = 7383;
	
	public static final int NUMBER_OF_ROOMS = 25, NUMBER_OF_FLOORS = 5;
	public static final int RIGHT = 1, UP = 2, LEFT = 3, DOWN = 4;
	public static final int MIN_ROOM_WIDTH = 20, MAX_ROOM_WIDTH = 30,
			MIN_ROOM_HEIGHT = 20, MAX_ROOM_HEIGHT = 30;
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
	public static final double TANK_SPEED = 1.0;
	public static final double HUNTER_SPEED = 2.0;
	public static final double CLERIC_SPEED = 2.0;

	// Class attack speeds (basic attack, delay between consecutive attacks)
	public static final int WARRIOR_ATTACK_SPEED = 60;
	public static final int THIEF_ATTACK_SPEED = 40;
	public static final int MAGE_ATTACK_SPEED = 2;
	public static final int TANK_ATTACK_SPEED = 80;
	public static final int HUNTER_ATTACK_SPEED = 60;
	public static final int CLERIC_ATTACK_SPEED = 60;

	// Class attack lengths (basic attack, duration of usage)
	public static final int WARRIOR_ATTACK_LENGTH = 40;
	public static final int THIEF_ATTACK_LENGTH = 15;
	public static final int MAGE_ATTACK_LENGTH = 1;
	public static final int TANK_ATTACK_LENGTH = 60;
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

	// Thief specific stats
	public static final int THIEF_SWORD_SIZE = 48;
	public static final int THIEF_SWING_ANGLE = 30;
	public static final double THIEF_DAMAGE = 40.0;
	public static final int THIEF_KNOCKBACK = 15;

	// Mage specific stats
	public static final int MAGE_SPRAY = 20;
	public static final double MAGE_DAMAGE = 1.0;
	public static final int MAGE_DEBUFF_RANGE = 250;
	public static final int MAGE_DEBUFF_LENGTH = 480;
	public static final int MAGE_FIRE_RANGE = 100;
	public static final int MAGE_FIRE_LENGTH = 300;
	
	// Tank specific stats
	public static final int TANK_SWORD_SIZE = 60;
	public static final int TANK_SWING_ANGLE = 100;
	public static final double TANK_DAMAGE = 40.0;
	public static final int TANK_BUFF_LENGTH = 600;
	public static final double TANK_BUFF_STRENGTH = 1.5;
	public static final int TANK_STUN_RANGE = 150;
	public static final int TANK_STUN_LENGTH = 240;
	public static final int TANK_REFLECT_TIME = 120;

	// Hunter specific stats
	public static final double ARROW_SPEED = 6.0;
	public static final double ARROW_DAMAGE = 25.0;
	public static final int ARROW_KNOCKBACK = 10;

	// Cleric specific stats
	public static final int CLERIC_SWORD_SIZE = 60;
	public static final int CLERIC_SWING_ANGLE = 100;
	public static final double CLERIC_DAMAGE = 40.0;
	public static final int CLERIC_HEAL_LENGTH = 900;
	public static final double CLERIC_HEAL_STRENGTH = 2.0;
	public static final int CLERIC_BURST_LENGTH = 20;
	public static final double CLERIC_BURST_STRENGTH = 1.0;
	
	public static final int[][] AB_COOLDOWNS = {{360, 300, 300},    // Warrior
												{600, 90, 300},     // Thief
												{600, 900, 1200},   // Mage
												{1500,  900, 1200}, // Tank
												{600, 600, 600},    // Hunter
												{1200, 1800, 300}};   // Cleric
}