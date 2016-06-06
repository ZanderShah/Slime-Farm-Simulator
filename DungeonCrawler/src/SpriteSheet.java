import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	public static Image[] FLOORS, DECORATIVE_IMAGES, BLOCKING_IMAGES;

	static Image random(Image[] source)
	{
		return source[(int) (Math.random() * source.length)];
	}

	static void initializeImages()
	{
		try
		{
			FLOORS = new Image[1];
			FLOORS[0] = ImageIO.read(new File("img//Floor1.png"));

			DECORATIVE_IMAGES = new Image[2];
			DECORATIVE_IMAGES[0] = ImageIO.read(new File(
					"img//DecorativeImage1.png"));
			DECORATIVE_IMAGES[1] = ImageIO.read(new File("img//DecorativeImage2.png"));
			
			BLOCKING_IMAGES = new Image[1];
			BLOCKING_IMAGES[0] = ImageIO.read(new File("img//BlockingImage1.png"));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load an image");
			e.printStackTrace();
		}
	}

}
