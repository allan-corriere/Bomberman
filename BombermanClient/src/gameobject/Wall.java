package gameobject;

import java.io.File;

import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;




public class Wall extends GameObject implements UnmovableObject {
	private Image image = new Image(new File("ressources/wall.jpg").toURI().toString());
	
	
	public Wall() {
		fxLayer = new ImageView(image);
		this.setPosX(50.0);
		this.setPosY(50.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}
	
	public Wall(double posX, double posY) {
		fxLayer = new ImageView(image);
		this.setPosX(posX);
		this.setPosY(posY);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}

}
