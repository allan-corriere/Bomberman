package gameobject;

import java.io.File;
import java.util.ArrayList;
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
		radius = 2;
	}
	
	public void startBomb(List<GameObject> gameObjectList){
		ThreadBomb b1 = new ThreadBomb(this, gameObjectList);
		b1.start();
		
	}
	
	public void explode(List<GameObject> gameObjectList) {
		List<GameObject> objectToRemove = new ArrayList<GameObject>();
		boolean blockedXplus = false;
		boolean blockedXminus = false;
		boolean blockedYplus = false;
		boolean blockedYminus = false;
		for(int i = 1; i <= radius; i++) {
			for (GameObject object : gameObjectList) {
				if(object instanceof DestructableObject && !(object instanceof Bomb)) {
					//same pos
					if(object.getPosX() == this.getPosX() && object.getPosY() == this.getPosY()) {
						objectToRemove.add(object);
					}
					// X plus
					else if(object.getPosX() == (this.getPosX()+50.0*i) && object.getPosY() == this.getPosY() && blockedXplus == false) {
						objectToRemove.add(object);
					}
					// X minus
					else if(object.getPosX() == (this.getPosX()-50.0*i) && object.getPosY() == this.getPosY() && blockedXminus == false) {
						objectToRemove.add(object);
					}
					// Y plus
					else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()+50.0*i) && blockedYplus == false) {
						objectToRemove.add(object);
					}
					// Y minus
					else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()-50.0*i) && blockedYminus == false) {
						objectToRemove.add(object);
					} 
				}
				//check for blocking object in field
				else if(!(object instanceof DestructableObject)) {
					// X plus
					if(object.getPosX() == (this.getPosX()+50.0*i) && object.getPosY() == this.getPosY()) {
						blockedXplus = true;
					}
					// X minus
					else if(object.getPosX() == (this.getPosX()-50.0*i) && object.getPosY() == this.getPosY()) {
						blockedXminus = true;
					}
					// Y plus
					else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()+50.0*i)) {
						blockedXplus = true;
					}
					// Y minus
					else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()-50.0*i)) {
						blockedXminus = true;
					} 
				}
			}
		}
		for(GameObject object : objectToRemove) {
			object.fxLayer.setVisible(false);
			gameObjectList.remove(object);
		}
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
