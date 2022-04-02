package assign3CST126CST133;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    private Image image;
    private Coors obCoor;
    private boolean Live;
    private double width;
    private double height;
    private boolean Left = false;
    public static final String ImageFolder = "file:src/assign3CST126CST133/CADCAMInvadersImages/";
    

    public Sprite(Image image,  double nXPos, double nYPos) {
      setImage(image);
       obCoor = new Coors(nXPos,nYPos);
       Live = true;
        
    }
    
    public Image getImage()
    {
    	return this.image;
    }
    
    public void setImage(Image image)
    {
    	this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        
    }
   
    
    public double getWidth()
    {
    	return this.width;
    }
    public double getHeight()
    {
    	return this.height;
    }

    public void setPosition(double x, double y) {
       this.obCoor.setX(x);
        this.obCoor.setY(y);
        
    }

    public Coors getCoors()
    {
    	return this.obCoor;
    	
    }
    
    public void dodge(GraphicsContext gc)
    {
    	this.clear(gc);
    	if(Left) 
    	{
    		Left = false;
    		this.obCoor.setX(this.obCoor.getX() - 35);
    	}
    	else
    	{
    		this.obCoor.setX(this.obCoor.getX() + 35);
    		Left = true;
    	}
    	this.render(gc);
    }
    
    public void clear(GraphicsContext gc)
    {
    	gc.clearRect(obCoor.getX(), obCoor.getY(), width, height);
    	
    }
    
    public void render(GraphicsContext gc) {
        gc.drawImage(image, obCoor.getX(), obCoor.getY());
        
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(obCoor.getX(), obCoor.getY(), width, height);
    }

    public boolean intersects(Sprite spr) {
        return spr.getBoundary().intersects(this.getBoundary());
    }

	public boolean isLive() {
		// TODO Auto-generated method stub
		return Live;
	}
	
	public void Hit()
	{
		this.Live = false;
	}
}
