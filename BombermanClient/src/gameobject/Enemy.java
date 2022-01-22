package gameobject;

import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import javafx.scene.image.ImageView;

public class Enemy extends GameObject implements DestructableObject {
	private int PlayerNumber;
	private String userName;

	public Enemy(int x, int y, ImageView imageView) {
		super(x, y, imageView);
	}

	public int getPlayerNumber() {
		return PlayerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		PlayerNumber = playerNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
