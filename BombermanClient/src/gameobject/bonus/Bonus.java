package gameobject.bonus;


import java.util.Timer;

import gameobject.attribute.Crossable;
import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import javafx.scene.image.Image;


//acceleration deceleration
// bombe rayon
//nb bombes max
public class Bonus extends GameObject implements UnmovableObject, Crossable, DestructableObject {
	protected Image image;
	
	public Bonus(Timer gameTimer, double posX, double posY) {

	}

	
}
