package gameobject;

import java.io.File;
import java.util.Timer;

import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import gameobject.bonus.Bonus;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Brick extends GameObject implements DestructableObject, UnmovableObject {
	private Image image = new Image(new File("ressources/breakable_brick.png").toURI().toString());
	public Bonus brickBonus;

	public Brick(Timer gameTimer) {
		this.gameTimer = gameTimer;
		fxLayer = new ImageView(image);
		this.setPosX(50.0);
		this.setPosY(50.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}
	
	public Brick(Timer gameTimer, double posX, double posY,Bonus bonus) {
		this.gameTimer = gameTimer;
		fxLayer = new ImageView(image);
		this.setPosX(posX);
		this.setPosY(posY);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
		this.brickBonus = bonus;
	}
		
	public Bonus getBrickBonus() {
		return brickBonus;
	}

	public void setBrickBonus(Bonus brickBonus) {
		this.brickBonus = brickBonus;
	}
	
}
