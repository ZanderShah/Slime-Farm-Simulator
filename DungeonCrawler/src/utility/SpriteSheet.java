package utility;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	public static Image[] FLOORS, DECORATIVE_IMAGES, BLOCKING_IMAGES,
			MAGE_IMAGES, HUNTER_IMAGES, CLERIC_IMAGES, TANK_IMAGES, WARRIOR_IMAGES, DOORS, WALLS,
			PROJECTILES, ENEMIES, MENUS, MELEE_ATTACKS;
	public static Image[][] THIEF_IMAGES, PARTICLES, HUD_IMAGES;
	public static Image[] KING_SLIME;

	public static Image random(Image[] source, Random rng)
	{
		return source[(int) (rng.nextDouble() * source.length)];
	}

	public static void initializeImages()
	{
		try
		{
			FLOORS = new Image[1];
			FLOORS[0] = ImageIO.read(new File("img//Floor1.png"));

			DECORATIVE_IMAGES = new Image[4];
			for (int i = 1; i < 5; i++)
				DECORATIVE_IMAGES[i - 1] = ImageIO.read(new File(
						"img//DecorativeImage" + i + ".png"));

			BLOCKING_IMAGES = new Image[4];
			for (int i = 1; i < 5; i++)
				BLOCKING_IMAGES[i - 1] = ImageIO.read(new File(
						"img//BlockingImage" + i + ".png"));

			MAGE_IMAGES = new Image[4];
			HUNTER_IMAGES = new Image[4];
			CLERIC_IMAGES = new Image[4];
			TANK_IMAGES = new Image[4];
			WARRIOR_IMAGES = new Image[4];
			THIEF_IMAGES = new Image[4][2];
			for (int i = 1; i <= 4; i++)
			{
				MAGE_IMAGES[i - 1] = ImageIO.read(new File("img//Mage" + i
						+ ".png"));
				HUNTER_IMAGES[i - 1] = ImageIO.read(new File("img//Hunter" + i
						+ ".png"));
				CLERIC_IMAGES[i - 1] = ImageIO.read(new File("img//Cleric" + i
						+ ".png"));
				THIEF_IMAGES[i - 1][0] = ImageIO.read(new File("img//Thief" + i
						+ ".png"));
				TANK_IMAGES[i - 1] = ImageIO.read(new File("img//Tank" + i
						+ ".png"));
				WARRIOR_IMAGES[i - 1] = ImageIO.read(new File("img//Warrior" + i
						+ ".png"));
			}

			for (int i = 1; i <= 4; i++)
			{
				THIEF_IMAGES[i - 1][1] = ImageIO.read(new File("img//ThiefQ"
						+ i + ".png"));
			}

			DOORS = new Image[2];
			DOORS[0] = ImageIO.read(new File("img//TemporaryDoor1.png"));
			DOORS[1] = ImageIO.read(new File("img//TemporaryDoor2.png"));

			WALLS = new Image[1];
			WALLS[0] = ImageIO.read(new File("img//Wall.png"));

			PROJECTILES = new Image[8];
			for (int i = 1; i <= 3; i++)
				PROJECTILES[i - 1] = ImageIO.read(new File("img//Flame" + i
						+ ".png"));
			PROJECTILES[3] = ImageIO.read(new File("img//MageQ.png"));
			for (int i = 1; i <= 3; i++)
				PROJECTILES[3 + i] = ImageIO.read(new File("img//Arrow" + i
						+ ".png"));
			PROJECTILES[7] = ImageIO.read(new File("img//ThiefR.png"));

			PARTICLES = new Image[1][2];
			PARTICLES[0][0] = ImageIO.read(new File("img//Particle1.png"));
			PARTICLES[0][1] = ImageIO.read(new File("img//Particle2.png"));

			ENEMIES = new Image[5];
			for (int i = 1; i <= 5; i++)
				ENEMIES[i - 1] = ImageIO.read(new File(String.format(
						"img//Enemy%d.png", i)));

			KING_SLIME = new Image[4];
			for (int i = 1; i <= 4; i++)
			{
				KING_SLIME[i - 1] = ImageIO.read(new File(String.format(
						"img//KingSlime%d.png", i)));
			}

			HUD_IMAGES = new Image[6][3];
			for (int j = 1; j <= 3; j++){
				HUD_IMAGES[2][j - 1] = ImageIO.read(new File(String.format(
						"img//Icon%d%d.png", 2, j)));
				HUD_IMAGES[1][j - 1] = ImageIO.read(new File(String.format(
						"img//Icon%d%d.png", 1, j)));
				HUD_IMAGES[4][j - 1] = ImageIO.read(new File(String.format(
						"img//Icon%d%d.png", 4, j)));
			}
			
			MENUS = new Image[1];
			MENUS[0] = ImageIO.read(new File("img//PlayerSelectMenu.png"));
			MENUS[1] = ImageIO.read(new File("img//MainMenu.png"));
			MENUS[2] = ImageIO.read(new File("img//HelpMenu.png"));
			
			MELEE_ATTACKS = new Image[2];
			MELEE_ATTACKS[0] = ImageIO.read(new File("WarriorSwing.png"));
			MELEE_ATTACKS[0] = ImageIO.read(new File("ThiefSwing.png"));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load an image");
			e.printStackTrace();
		}
	}

}
