import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	public static Image[] FLOORS, DECORATIVE_IMAGES;

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

			DECORATIVE_IMAGES = new Image[1];
			DECORATIVE_IMAGES[0] = ImageIO.read(new File(
					"img//DecorativeObject1.png"));
		}
		catch (IOException e)
		{
			System.out.println("Failed to load an image");
			e.printStackTrace();
		}
	}

}
