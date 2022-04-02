package assign3CST126CST133;

import javafx.scene.image.ImageView;

public class AnimationUtils 
{
	public static void moveOne(ImageView obImage, int nLimit)
	{
		if (obImage.getX() <= nLimit)
		{
			obImage.setX(obImage.getX() + 3);
		}
		
	}
	
	public static void moveTwo(ImageView obImage, int nLimit)
	{
		if (obImage.getX() >= nLimit)
		{
			obImage.setX(obImage.getX() - 3);
		}
		
	}
	
	public static void moveDown(ImageView obImage, int nLimit)
	{
		if (obImage.getY() <= nLimit)
		{
			obImage.setY(obImage.getY() + 3);
		}
	}
	
	public static void moveUp(ImageView obImage, int nLimit)
	{
		if (obImage.getY() >= nLimit)
		{
			obImage.setY(obImage.getY() - 3);
		}
	}
	
	
	
}
