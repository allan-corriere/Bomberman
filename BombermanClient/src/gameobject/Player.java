package gameobject;

import gameobject.attribute.GameObject;
import gameobject.attribute.MovableObject;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;



public class Player extends GameObject implements MovableObject{
	private boolean alive;
	private double speed;
	//bomb spec
	private int maxBomb;
	private int currentBombNb;
	private int bombRadius;

	

	
	
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
	
	public void placeBomb(Bomb bomb, KeyCode code) {
		if (code==KeyCode.SPACE)
		{
			bomb.setPosX(this.getPosX());
			bomb.setPosY(this.getPosY());
			bomb.startBomb();
		}
	}
	
}
