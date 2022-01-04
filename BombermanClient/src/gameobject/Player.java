package gameobject;

import java.io.File;
import java.util.*;

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

	public void move(KeyCode code, List<GameObject> gameObjectList) {
		boolean moveOk = true;
		System.out.println("joueur x:"+this.getPosX()+" y:"+this.getPosY());
		double playerTop = this.getPosY()+0.1;
		double playerBottom = this.getPosY()+49.99;
		double playerLeft = this.getPosX()+0.1;
		double playerRight = this.getPosX()+49.00;
		
		if (code ==KeyCode.Z)
		{				
			if (this.getPosY()>0)
			{
			//check si un object ne bloque pas le passage
			for (GameObject object : gameObjectList) {
				if(System.identityHashCode(object) != System.identityHashCode(this)) {
					double bottom = object.getPosY()+50.00;
					double left = object.getPosX();
					double right = object.getPosX()+50.00;
					if(bottom >= (playerTop-this.getSpeed()) && bottom < playerTop && left < playerLeft && right >= playerLeft || bottom >= (playerTop-this.getSpeed()) && bottom < playerTop && left < playerRight && right >= playerRight) {
						moveOk = false;
					}
				}
			}
				//if(player.getPosX()+player.fxLayer.getFitWidth()<wall1.getPosX() && player.getPosX()>wall1.getPosX()+wall1.fxLayer.getFitWidth())
			if(moveOk == true)
				{
					//if(player.getPosY()>wall1.getPosY()+wall1.fxLayer.getFitHeight())
						this.setPosY(this.getPosY()-this.getSpeed());

						
				}
			}
		}
		
		if (code==KeyCode.S)
		{
			double prefHeight = ((Pane) this.fxLayer.getParent()).getPrefHeight();
			//check sortie de page
			if (prefHeight-this.getPosY()>this.fxLayer.getFitHeight()) {
				//check si un object ne bloque pas le passage
				for (GameObject object : gameObjectList) {
					if(System.identityHashCode(object) != System.identityHashCode(this)) {
						double top = object.getPosY();
						double left = object.getPosX();
						double right = object.getPosX()+50.00;
						
						if(top <= (playerBottom+this.getSpeed()) && top > playerBottom && left < playerLeft && right >= playerLeft || top <= (playerBottom+this.getSpeed()) && top > playerBottom && left < playerRight && right >= playerRight) {
							moveOk = false;
						}
					}
				}
			}
			if(moveOk == true) {
				this.setPosY(this.getPosY()+this.getSpeed());
			}

		}
		
		if (code==KeyCode.Q)
		{
			if (this.getPosX()>0) {
				//check si un object ne bloque pas le passage
				for (GameObject object : gameObjectList) {
					if(System.identityHashCode(object) != System.identityHashCode(this)) {
						double right = object.getPosX()+50.00;
						double top = object.getPosY();
						double bottom = object.getPosY()+50.0;
						if(right >= (playerLeft-this.getSpeed()) && right < playerLeft && top < playerTop && bottom >= playerTop || right >= (playerLeft-this.getSpeed()) && right < playerLeft && top < playerBottom && bottom >= playerBottom) {
							moveOk = false;
						}
					}
				}
				if(moveOk == true) {
					this.setPosX(this.getPosX()-this.getSpeed());
				}
			}
		}
		
		if (code==KeyCode.D)
		{
			double prefWidth = ((Pane) this.fxLayer.getParent()).getPrefWidth();
			if (prefWidth-this.getPosX()>this.fxLayer.getFitWidth()){
				
				//check si un object ne bloque pas le passage
				for (GameObject object : gameObjectList) {
					if(System.identityHashCode(object) != System.identityHashCode(this)) {
						double left = object.getPosX();
						double top = object.getPosY();
						double bottom = object.getPosY()+50.0;						
						if(left <= (playerRight+this.getSpeed()) && left > playerRight && top < playerTop && bottom >= playerTop || left <= (playerRight+this.getSpeed()) && left > playerRight && top < playerBottom && bottom >= playerBottom) {
							moveOk = false;
						}
					}
				}
				if(moveOk == true) {
					this.setPosX(this.getPosX()+this.getSpeed());
				}
			}
		}
	}
	
	public void placeBomb(KeyCode code, Pane RBox, List<GameObject> gameObjectList) {
		if (code==KeyCode.SPACE)
		{
			Bomb bomb = new Bomb();
			//place bomb in the good place
			int nbSquareX = (int) (this.getPosX() / 50.0);
			double deltaX = this.getPosX() % 50.0;
			int nbSquareY = (int) (this.getPosY() / 50.0);
			double deltaY = this.getPosY() % 50.0;
			System.out.println("square X "+nbSquareX);
			System.out.println("square Y "+nbSquareY);
			System.out.println(deltaX);
			System.out.println(deltaY);
			
			if(deltaX == 0 && deltaY == 0) {
				bomb.setPosX(this.getPosX());
				bomb.setPosY(this.getPosY());
			}
			else {
				if(deltaX > 50.0/2.0) {
					nbSquareX += 1;
				}
				if(deltaY > 50.0/2.0) {
					nbSquareY += 1;
				}
				bomb.setPosX(nbSquareX*50.0);
				bomb.setPosY(nbSquareY*50.0);
				
			}

			gameObjectList.add(bomb);
			bomb.fxLayer.setVisible(false);
			RBox.getChildren().add(bomb.fxLayer);
			bomb.startBomb(gameObjectList);
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
