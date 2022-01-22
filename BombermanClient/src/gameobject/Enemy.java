package gameobject;

import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import javafx.scene.image.ImageView;

public class Enemy extends GameObject implements DestructableObject {
	private int PlayerNumber;

	public Enemy(int x, int y, ImageView imageView) {
		super(x, y, imageView);
	}

	public int getPlayerNumber() {
		return PlayerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		PlayerNumber = playerNumber;
	}
	

}
