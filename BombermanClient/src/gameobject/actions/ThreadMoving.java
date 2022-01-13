package gameobject.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import gameobject.Player;
import gameobject.attribute.Crossable;
import gameobject.attribute.GameObject;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class ThreadMoving implements Runnable{
	private Player player;
	private Pane RBox;
	private List<GameObject> gameObjectList;
	private ArrayList<String> keyPressed = new ArrayList<>();
	
	private Image face0 = new Image(new File("ressources/Hero/face0.png").toURI().toString());
	private Image face1 = new Image(new File("ressources/Hero/face1.png").toURI().toString());
	private Image face2 = new Image(new File("ressources/Hero/face2.png").toURI().toString());
	private double countFace;

	private Image back0 = new Image(new File("ressources/Hero/back0.png").toURI().toString());
	private Image back1 = new Image(new File("ressources/Hero/back1.png").toURI().toString());
	private Image back2 = new Image(new File("ressources/Hero/back2.png").toURI().toString());
	private double countBack;
	
	private Image right0 = new Image(new File("ressources/Hero/right0.png").toURI().toString());
	private Image right1 = new Image(new File("ressources/Hero/right1.png").toURI().toString());
	private Image right2 = new Image(new File("ressources/Hero/right2.png").toURI().toString());
	private double countRight;

	private Image left0 = new Image(new File("ressources/Hero/left0.png").toURI().toString());
	private Image left1 = new Image(new File("ressources/Hero/left1.png").toURI().toString());
	private Image left2 = new Image(new File("ressources/Hero/left2.png").toURI().toString());
	private double countLeft;
	
	private boolean left_right;
	
	public ThreadMoving(Player player,Pane RBox, List<GameObject> gameObjectList) {
		this.player = player;
		this.RBox = RBox;
		this.gameObjectList = gameObjectList;
		keyPressed = this.player.getKeyPressed();
		
	}
	
	public void run() {
		String code = "";
		while(player.isAlive() == true) {
			code = "";
			if(keyPressed.isEmpty() != true) {
				code = keyPressed.get(keyPressed.size() - 1);
			}	
			//move
			boolean moveOk = true;
			double playerTop = player.getPosY()+0.1;
			double playerBottom = player.getPosY()+49.99;
			double playerLeft = player.getPosX()+0.1;
			double playerRight = player.getPosX()+49.00;
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					player.fxLayer.toFront();
				}
			});
	
			if (code =="up")
					{
						System.out.println("ca bouge");
						if (player.getPosY()>0)
						{
						//check si un object ne bloque pas le passage
						for (GameObject object : gameObjectList) {
							if(System.identityHashCode(object) != System.identityHashCode(this)) {
								if(!(object instanceof Crossable)) {
									double bottom = object.getPosY()+50.00;
									double left = object.getPosX();
									double right = object.getPosX()+50.00;
									if(bottom >= (playerTop-player.getSpeed()) && bottom < playerTop && left < playerLeft && right >= playerLeft || bottom >= (playerTop-player.getSpeed()) && bottom < playerTop && left < playerRight && right >= playerRight) {
										moveOk = false;
									}
								}
							}
						}
						//if(player.getPosX()+player.fxLayer.getFitWidth()<wall1.getPosX() && player.getPosX()>wall1.getPosX()+wall1.fxLayer.getFitWidth())
						if(moveOk == true){
							
							player.setPosY(player.getPosY()-player.getSpeed());
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									player.fxLayer.toBack();   /// Garder les bombes visibles 
								}
							});
			
							//Animations du personnage //////////////
										
							if (!(player.fxLayer.getImage().equals(back1)||player.fxLayer.getImage().equals(back2))){
									
									if (left_right == true ) {
										player.fxLayer.setImage(back1);
										left_right = false;
									}
									else {
										player.fxLayer.setImage(back2);
										left_right=true;
									}
								}
							
							else {
								if (countBack%3 == 0) {
					
									
									if (player.fxLayer.getImage().equals(back2)){
										player.fxLayer.setImage(back1);
									}
									
									else if (player.fxLayer.getImage().equals(back1)){
										player.fxLayer.setImage(back2);
										}
									}
							}
							countBack +=1 ;
							}
						
						
						//check for bonus
						player.PlayerOnBonus(RBox, gameObjectList);
						}
					}
					
					if (code== "down")
					{
						//check si un object ne bloque pas le passage
						for (GameObject object : gameObjectList) {
							if(System.identityHashCode(object) != System.identityHashCode(this)) {
								if(!(object instanceof Crossable)) {
									double top = object.getPosY();
									double left = object.getPosX();
									double right = object.getPosX()+50.00;
									if(top <= (playerBottom+player.getSpeed()) && top > playerBottom && left < playerLeft && right >= playerLeft || top <= (playerBottom+player.getSpeed()) && top > playerBottom && left < playerRight && right >= playerRight) {
										moveOk = false;
									}
								}
							}
						}
			
					if(moveOk == true) {
						player.setPosY(player.getPosY()+player.getSpeed());
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								player.fxLayer.toBack();   /// Garder les bombes visibles 
							}
						});
			
						
						
						//Animations du personnage //////////////////
									
						if (!(player.fxLayer.getImage().equals(face1)||player.fxLayer.getImage().equals(face2))){
								
								if (left_right == true ) {
									player.fxLayer.setImage(face1);
									left_right = false;
								}
								else {
									player.fxLayer.setImage(face2);
									left_right=true;
								}
							}
						
						else {
							if (countFace%3 == 0) {
				
								
								if (player.fxLayer.getImage().equals(face2)){
									player.fxLayer.setImage(face1);
								}
								
								else if (player.fxLayer.getImage().equals(face1)){
									player.fxLayer.setImage(face2);
									}
								}
						}
						countFace +=1 ; 
						}
					
					
					
					//check for bonus
					player.PlayerOnBonus(RBox, gameObjectList);
					
					}
					
					if (code=="left")
					{
						
						if (player.getPosX()>0) {
							//check si un object ne bloque pas le passage
							for (GameObject object : gameObjectList) {
								if(System.identityHashCode(object) != System.identityHashCode(this)) {
									if(!(object instanceof Crossable)) {
										double right = object.getPosX()+50.00;
										double top = object.getPosY();
										double bottom = object.getPosY()+50.0;
										if(right >= (playerLeft-player.getSpeed()) && right < playerLeft && top < playerTop && bottom >= playerTop || right >= (playerLeft-player.getSpeed()) && right < playerLeft && top < playerBottom && bottom >= playerBottom) {
											moveOk = false;
										}
									}
								}
							}
							if(moveOk == true) {
								
								player.setPosX(player.getPosX()-player.getSpeed());
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										player.fxLayer.toBack();   /// Garder les bombes visibles 
									}
								});
			
								
								//Animations du personnage ////////////////////
								
								if (!(player.fxLayer.getImage().equals(left1)||player.fxLayer.getImage().equals(left2))){
										
										if (left_right == true ) {
											player.fxLayer.setImage(left1);
											left_right = false;
										}
										else {
											player.fxLayer.setImage(left2);
											left_right=true;
										}
									}
								
								else {
									if (countLeft%3 == 0) {
						
										
										if (player.fxLayer.getImage().equals(left2)){
											player.fxLayer.setImage(left1);
										}
										
										else if (player.fxLayer.getImage().equals(left1)){
											player.fxLayer.setImage(left2);
											}
										}
								}
								countLeft +=1 ;
								
						
				
							}
						}
						
						//check for bonus
						player.PlayerOnBonus(RBox, gameObjectList);
					}
					
					if (code=="right")
					{
						double prefWidth = ((Pane) player.fxLayer.getParent()).getPrefWidth();
						if (prefWidth-player.getPosX()>player.fxLayer.getFitWidth()){
							
							//check si un object ne bloque pas le passage
							for (GameObject object : gameObjectList) {
								if(System.identityHashCode(object) != System.identityHashCode(this)) {
									if(!(object instanceof Crossable)) {
										
										double left = object.getPosX();
										double top = object.getPosY();
										double bottom = object.getPosY()+50.0;						
										if(left <= (playerRight+player.getSpeed()) && left > playerRight && top < playerTop && bottom >= playerTop || left <= (playerRight+player.getSpeed()) && left > playerRight && top < playerBottom && bottom >= playerBottom) {
											moveOk = false;
										}
									}
								}
							}
							if(moveOk == true) {
								player.setPosX(player.getPosX()+player.getSpeed());
								
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										player.fxLayer.toBack();   /// Garder les bombes visibles 
									}
								});
								
			
								
								//Animations du personnage ////////////////::
								
								if (!(player.fxLayer.getImage().equals(right1)||player.fxLayer.getImage().equals(right2))){
										
										if (left_right == true ) {
											player.fxLayer.setImage(right1);
											left_right = false;
										}
										else {
											player.fxLayer.setImage(right2);
											left_right=true;
										}
									}
								
								else {
									if (countRight%3 == 0) {
						
										
										if (player.fxLayer.getImage().equals(right2)){
											player.fxLayer.setImage(right1);
										}
										
										else if (player.fxLayer.getImage().equals(right1)){
											player.fxLayer.setImage(right2);
											}
										}
								}
								countRight +=1 ;
							}
						}
						
						//check for bonus
						player.PlayerOnBonus(RBox, gameObjectList);
						
					}
		}
		
	}
}
