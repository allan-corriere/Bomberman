package gameobject.attribute;
import java.util.Timer;

import javafx.scene.image.ImageView;

public class GameObject extends Object {
	private double posX;
	private double posY;
	private double height;
	private double width;
	public ImageView fxLayer;
	public Timer gameTimer = new Timer();
	
	public GameObject() {
		
	}
	
	public GameObject(double x, double y, ImageView fxLayer) {
		this.posX = x;
		this.posY = y;
		this.height = 50;
		this.width = 50;
		this.fxLayer = fxLayer;
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}
	
	public GameObject(ImageView fxLayer) {
		this.fxLayer = fxLayer;
	}
	
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
	
	public void setPosition(double PosX, double PosY) {
		this.posX = PosX;
		this.fxLayer.setLayoutX(PosX);
		this.posY = PosY;
		this.fxLayer.setLayoutY(PosY);
	}
}
