package gameobject;

import java.io.File;

import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import gameobject.bonus.Bonus;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Brick extends GameObject implements DestructableObject, UnmovableObject {
	private Image image = new Image(new File("ressources/wall.jpg").toURI().toString());
	private Bonus brickBonus;

	public Brick() {
		fxLayer = new ImageView(image);
		this.setPosX(50.0);
		this.setPosY(50.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}
	
	public Brick(double posX, double posY) {
		fxLayer = new ImageView(image);
		this.setPosX(posX);
		this.setPosY(posY);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}
	
	public Bonus getBrickBonus() {
		return brickBonus;
	}

	public void setBrickBonus(Bonus brickBonus) {
		this.brickBonus = brickBonus;
	}
	
}
