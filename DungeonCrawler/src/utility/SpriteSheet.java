package utility;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	public static Image[] FLOORS, DECORATIVE_IMAGES, BLOCKING_IMAGES, MAGE_IMAGES, DOORS;

	public static Image random(Image[] source)
	{
		return source[(int) (Math.random() * source.length)];
	}

	public static void initializeImages()
	{
		try
		{
			FLOORS = new Image[1];
			FLOORS[0] = ImageIO.read(new File("img//Floor1.png"));

			DECORATIVE_IMAGES = new Image[4];
			for (int i = 1; i < 5; i++)
				DECORATIVE_IMAGES[i-1] = ImageIO.read(new File("img//DecorativeImage" + i + ".png"));
			
			BLOCKING_IMAGES = new Image[3];
			for (int i = 1; i < 4; i++)
				BLOCKING_IMAGES[i-1] = ImageIO.read(new File("img//BlockingImage" + i + ".png"));
			
			MAGE_IMAGES = new Image[4];
			for (int i = 1; i < 4; i++)
				MAGE_IMAGES[i-1] = ImageIO.read(new File("img//Mage" + i + ".png"));
			
			DOORS = new Image[2];
			DOORS[0] = ImageIO.read(new File("img//TemporaryDoor1.png"));
			DOORS[1] = ImageIO.read(new File("img//TemporaryDoor2.png"));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load an image");
			e.printStackTrace();
		}
	}

}
