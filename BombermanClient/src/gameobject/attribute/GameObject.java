package gameobject.attribute;
import javafx.scene.image.ImageView;

public class GameObject {
	private double posX;
	private double posY;
	private double height;
	private double width;
	public ImageView fxLayer;
	
	
	public void setHeight(double Height)
	{
		height = Height;
	}
	
	public void setWidth(double Width)
	{
		width = Width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getPosX() {
		return posX;
	}
	public void setPosX(double PosX) {
		posX = PosX;
		fxLayer.setLayoutX(PosX);
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double PosY) {
		posY = PosY;
		fxLayer.setLayoutY(PosY);
	}
}
