package gameobject;

import java.io.File;

import gameobject.attribute.GameObject;
import gameobject.attribute.MovableObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;



public class Player extends GameObject implements MovableObject{
	private boolean alive;
	private double speed;
	//bomb spec
	private int maxBomb;
	private int currentBombNb;
	private int bombRadius;
	private Image image = new Image(new File("ressources/hero.jpg").toURI().toString());
	
	
	public Player() {
		fxLayer = new ImageView(image);
		this.setPosX(0.0);
		this.setPosY(0.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}

	public void move(KeyCode code) {
		if (code ==KeyCode.UP)
		{
			if (this.getPosY()>0)
			{
				//if(player.getPosX()+player.fxLayer.getFitWidth()<wall1.getPosX() && player.getPosX()>wall1.getPosX()+wall1.fxLayer.getFitWidth())
				{
					//if(player.getPosY()>wall1.getPosY()+wall1.fxLayer.getFitHeight())
						this.setPosY(this.getPosY()-this.getSpeed());

						
				}
			}
		}
		
		if (code==KeyCode.DOWN)
		{
			double prefHeight = ((Pane) this.fxLayer.getParent()).getPrefHeight();

			if (prefHeight-this.getPosY()>this.fxLayer.getFitHeight())
				this.setPosY(this.getPosY()+this.getSpeed());
		}
		
		if (code==KeyCode.LEFT)
		{
			if (this.getPosX()>0)
				this.setPosX((this.getPosX()-this.getSpeed()));
		}
		
		if (code==KeyCode.RIGHT)
		{
			double prefWidth = ((Pane) this.fxLayer.getParent()).getPrefWidth();

			if (prefWidth-this.getPosX()>this.fxLayer.getFitWidth())
			{
				this.setPosX(this.getPosX()+this.getSpeed());
			}
		}
	}
	
	public void placeBomb(Pane RBox, KeyCode code) {
		if (code==KeyCode.SPACE)
		{
			Bomb bomb = new Bomb();
			bomb.setPosX(this.getPosX());
			bomb.setPosY(this.getPosY());
			bomb.fxLayer.setVisible(false);
			RBox.getChildren().add(bomb.fxLayer);
			bomb.startBomb();
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getMaxBomb() {
		return maxBomb;
	}
	public void setMaxBomb(int maxBomb) {
		this.maxBomb = maxBomb;
	}
	public int getCurrentBombNb() {
		return currentBombNb;
	}
	public void setCurrentBombNb(int currentBombNb) {
		this.currentBombNb = currentBombNb;
	}
	public int getBombRadius() {
		return bombRadius;
	}
	public void setBombRadius(int bombRadius) {
		this.bombRadius = bombRadius;
	}	
}
