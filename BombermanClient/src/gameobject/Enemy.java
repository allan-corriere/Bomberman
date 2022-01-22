package gameobject;

import java.io.File;

import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends GameObject implements DestructableObject {
	private int PlayerNumber;
	private boolean loaded = false;
	
	//Chargement des frames de d�placement
	
		private Image face0 = new Image(new File("ressources/Enemy/face0.png").toURI().toString());
		private Image face1 = new Image(new File("ressources/Enemy/face1.png").toURI().toString());
		private Image face2 = new Image(new File("ressources/Enemy/face2.png").toURI().toString());
		private double countFace;

		private Image back0 = new Image(new File("ressources/Enemy/back0.png").toURI().toString());
		private Image back1 = new Image(new File("ressources/Enemy/back1.png").toURI().toString());
		private Image back2 = new Image(new File("ressources/Enemy/back2.png").toURI().toString());
		private double countBack;
		
		private Image right0 = new Image(new File("ressources/Enemy/right0.png").toURI().toString());
		private Image right1 = new Image(new File("ressources/Enemy/right1.png").toURI().toString());
		private Image right2 = new Image(new File("ressources/Enemy/right2.png").toURI().toString());
		private double countRight;

		private Image left0 = new Image(new File("ressources/Enemy/left0.png").toURI().toString());
		private Image left1 = new Image(new File("ressources/Enemy/left1.png").toURI().toString());
		private Image left2 = new Image(new File("ressources/Enemy/left2.png").toURI().toString());
		private double countLeft;
		
		private boolean left_right;


	public Enemy(int x, int y, ImageView imageView) {
		super(x, y, imageView);
	}

	public int getPlayerNumber() {
		return PlayerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		PlayerNumber = playerNumber;
	}
	
	@Override
	public void setPosition(double PosX, double PosY) {
		double currentX = this.getPosX();
		double currentY = this.getPosY();
		super.setPosition(PosX, PosY);
		
		
		//ANIMATIONS 
		
		
		//Vers bas 
		
		if (currentY<PosY) {
			if (!(this.fxLayer.getImage().equals(face1)||this.fxLayer.getImage().equals(face2))){
				
				if (left_right == true ) {
					this.fxLayer.setImage(face1);
					left_right = false;
				}
				else {
					this.fxLayer.setImage(face2);
					left_right=true;
				}
			}
		
		else {
			if (countFace%3 == 0) {

				
				if (this.fxLayer.getImage().equals(face2)){
					this.fxLayer.setImage(face1);
				}
				
				else if (this.fxLayer.getImage().equals(face1)){
						this.fxLayer.setImage(face2);
					}
				}
		}
		countFace +=1 ; 
		}
		//Vers haut 
		if (currentY>PosY) {
			if (!(this.fxLayer.getImage().equals(back1)||this.fxLayer.getImage().equals(back2))){
				
				if (left_right == true ) {
					this.fxLayer.setImage(back1);
					left_right = false;
				}
				else {
					this.fxLayer.setImage(back2);
					left_right=true;
				}
			}
		
		else {
			if (countBack%3 == 0) {

				
				if (this.fxLayer.getImage().equals(back2)){
					this.fxLayer.setImage(back1);
				}
				
				else if (this.fxLayer.getImage().equals(back1)){
						this.fxLayer.setImage(back2);
					}
				}
		}
		countBack +=1 ;
		}
		//Vers gauche 
		if (currentX>PosX) {
			if (!(this.fxLayer.getImage().equals(left1)||this.fxLayer.getImage().equals(left2))){
				
				if (left_right == true ) {
					this.fxLayer.setImage(left1);
					left_right = false;
				}
				else {
					this.fxLayer.setImage(left2);
					left_right=true;
				}
			}
		
		else {
			if (countLeft%3 == 0) {

				
				if (this.fxLayer.getImage().equals(left2)){
					this.fxLayer.setImage(left1);
				}
				
				else if (this.fxLayer.getImage().equals(left1)){
						this.fxLayer.setImage(left2);
					}
				}
		}
		countLeft +=1 ;
		
		}
		
		//Vers droite 
		if (currentX<PosX) {
		
			if (!(this.fxLayer.getImage().equals(right1)||this.fxLayer.getImage().equals(right2))){
					
					if (left_right == true ) {
						this.fxLayer.setImage(right1);
						left_right = false;
					}
					else {
						this.fxLayer.setImage(right2);
						left_right=true;
					}
				}
			
			else {
				if (countRight%3 == 0) {
	
					
					if (this.fxLayer.getImage().equals(right2)){
						this.fxLayer.setImage(right1);
					}
					
					else if (this.fxLayer.getImage().equals(right1)){
							this.fxLayer.setImage(right2);
						}
					}
			}
			countRight +=1 ;
		}
		
		if (loaded == false) {
			this.fxLayer.setImage(face0);
			loaded=true;
		}
	}
	
}
		

