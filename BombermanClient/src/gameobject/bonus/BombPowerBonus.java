package gameobject.bonus;

import java.io.File;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BombPowerBonus extends Bonus {

	public BombPowerBonus( double posX, double posY) {
		super(posX, posY);
		image = new Image(new File("ressources/bonus_bomb_power.png").toURI().toString());
		fxLayer = new ImageView(image);
		this.setPosX(posX);
		this.setPosY(posY);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
		fxLayer.setVisible(false);
	}

}
