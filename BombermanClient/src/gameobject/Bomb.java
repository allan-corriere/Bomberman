package gameobject;

import java.io.File;
import java.util.List;

import gameobject.actions.ThreadBomb;
import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bomb extends GameObject implements DestructableObject, UnmovableObject {
	private int timer;
	private int radius;
	
private Image image = new Image(new File("ressources/bomb.png").toURI().toString());
	
	
	public Bomb() {
		fxLayer = new ImageView(image);
		this.setPosX(0.0);
		this.setPosY(0.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}
	
	public void startBomb(List<GameObject> gameObjectList){
		ThreadBomb b1 = new ThreadBomb(this, gameObjectList);
		b1.start();
		
	}
	
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}	
	
}
