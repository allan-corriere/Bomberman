package gameobject;

import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import gameobject.bonus.Bonus;



public class Brick extends GameObject implements DestructableObject, UnmovableObject {


	private Bonus brickBonus;

	
	public Bonus getBrickBonus() {
		return brickBonus;
	}

	public void setBrickBonus(Bonus brickBonus) {
		this.brickBonus = brickBonus;
	}
	
}
