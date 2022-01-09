package gameobject.bonus;

import java.io.File;
import java.util.Timer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BombNumberBonus extends Bonus {

	public BombNumberBonus(Timer gameTimer, double posX, double posY) {
		super(gameTimer, posX, posY);
		image = new Image(new File("ressources/bonus_bomb_number.png").toURI().toString());
		fxLayer = new ImageView(image);
		this.gameTimer = gameTimer;
		this.setPosX(posX);
		this.setPosY(posY);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
		fxLayer.setVisible(false);
	}

}
