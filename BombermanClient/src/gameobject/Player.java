package gameobject;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import gameobject.attribute.Crossable;
import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.MovableObject;
import gameobject.bonus.BombNumberBonus;
import gameobject.bonus.BombPowerBonus;
import gameobject.bonus.Bonus;
import gameobject.bonus.PlayerSpeedBonus;
import gameobject.Bomb;
import animations.ExplodeAnims;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import socket.GameClient;
import socket.SocketReader;
import socket.SocketWriter;



public class Player extends GameObject implements MovableObject, DestructableObject{
	private SocketWriter sw;
	private boolean alive;
	private double speed;
	//bomb spec
	private int maxBomb;
	private int currentBombNb;
	private int bombRadius;
	
	//Chargement des frames de d�placement
	
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


	public Player(Timer gameTimer, SocketWriter sw) {

		this.currentBombNb = 0;
		this.gameTimer = gameTimer;
		fxLayer = new ImageView(face0);
		this.setPosX(0.0);
		this.setPosY(0.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
		this.sw = sw;
		this.setSpeed(5);
		this.setMaxBomb(1);
		this.setBombRadius(1);
		this.alive = true;
//		try {
//			GameClient client = new GameClient("localhost", 65432, "Osloh");
//			new Thread(new SocketReader(client)).start();
//			
//			this.sw = new SocketWriter(client);
//			new Thread(this.sw).start();
//			
//			//this.sw = new TestSW(client);
//			//this.sw.run();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public void sendPositionToServer() {
		this.sw.send("move:"+this.getPosX()+":"+this.getPosY());
	}
	
	public void sendBonusToServer(double x, double y) {
		this.sw.send("bonus:"+x+":"+y);
	}
	
	public void sendBombPositionToServer(Bomb bomb) {
		this.sw.send("bomb:"+bomb.getPosX()+":"+bomb.getPosY()+":"+this.bombRadius);
	}

	public void move(KeyCode code,Pane RBox, List<GameObject> gameObjectList) {
		boolean moveOk = true;
		double playerTop = this.getPosY()+0.1;
		double playerBottom = this.getPosY()+49.99;
		double playerLeft = this.getPosX()+0.1;
		double playerRight = this.getPosX()+49.00;
		this.fxLayer.toFront();
		
		if (code ==KeyCode.Z)
		{
			if (this.getPosY()>0)
			{
			//check si un object ne bloque pas le passage
			for (GameObject object : gameObjectList) {
				if(System.identityHashCode(object) != System.identityHashCode(this)) {
					if(!(object instanceof Crossable)) {
						double bottom = object.getPosY()+50.00;
						double left = object.getPosX();
						double right = object.getPosX()+50.00;
						if(bottom >= (playerTop-this.getSpeed()) && bottom < playerTop && left < playerLeft && right >= playerLeft || bottom >= (playerTop-this.getSpeed()) && bottom < playerTop && left < playerRight && right >= playerRight) {
							moveOk = false;
						}
					}
				}
			}
				//if(player.getPosX()+player.fxLayer.getFitWidth()<wall1.getPosX() && player.getPosX()>wall1.getPosX()+wall1.fxLayer.getFitWidth())
			if(moveOk == true){
				
				this.setPosY(this.getPosY()-this.getSpeed());
				this.fxLayer.toBack();   /// Garder les bombes visibles 

				//Animations du personnage //////////////
							
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
			
			
			//check for bonus
			PlayerOnBonus(RBox, gameObjectList);
			}
		}
		
		if (code==KeyCode.S)
		{
			//check si un object ne bloque pas le passage
			for (GameObject object : gameObjectList) {
				if(System.identityHashCode(object) != System.identityHashCode(this)) {
					if(!(object instanceof Crossable)) {
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
			this.fxLayer.toBack();   /// Garder les bombes visibles 

			
			
			//Animations du personnage //////////////////
						
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
		
		
		
		//check for bonus
		PlayerOnBonus(RBox, gameObjectList);
		
		}
		
		if (code==KeyCode.Q)
		{
			
			if (this.getPosX()>0) {
				//check si un object ne bloque pas le passage
				for (GameObject object : gameObjectList) {
					if(System.identityHashCode(object) != System.identityHashCode(this)) {
						if(!(object instanceof Crossable)) {
							double right = object.getPosX()+50.00;
							double top = object.getPosY();
							double bottom = object.getPosY()+50.0;
							if(right >= (playerLeft-this.getSpeed()) && right < playerLeft && top < playerTop && bottom >= playerTop || right >= (playerLeft-this.getSpeed()) && right < playerLeft && top < playerBottom && bottom >= playerBottom) {
								moveOk = false;
							}
						}
					}
				}
				if(moveOk == true) {
					
					this.setPosX(this.getPosX()-this.getSpeed());
					this.fxLayer.toBack();   /// Garder les bombes visibles 

					
					//Animations du personnage ////////////////////
					
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
			}
			
			//check for bonus
			PlayerOnBonus(RBox, gameObjectList);
		}
		
		if (code==KeyCode.D)
		{
			double prefWidth = ((Pane) this.fxLayer.getParent()).getPrefWidth();
			if (prefWidth-this.getPosX()>this.fxLayer.getFitWidth()){
				
				//check si un object ne bloque pas le passage
				for (GameObject object : gameObjectList) {
					if(System.identityHashCode(object) != System.identityHashCode(this)) {
						if(!(object instanceof Crossable)) {
							
							double left = object.getPosX();
							double top = object.getPosY();
							double bottom = object.getPosY()+50.0;						
							if(left <= (playerRight+this.getSpeed()) && left > playerRight && top < playerTop && bottom >= playerTop || left <= (playerRight+this.getSpeed()) && left > playerRight && top < playerBottom && bottom >= playerBottom) {
								moveOk = false;
							}
						}
					}
				}
				if(moveOk == true) {
					this.setPosX(this.getPosX()+this.getSpeed());
					this.fxLayer.toBack();   /// Garder les bombes visibles 

					
					//Animations du personnage ////////////////::
					
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
			}
			
			//check for bonus
			PlayerOnBonus(RBox, gameObjectList);
			
		}
		if(moveOk) {
			this.sendPositionToServer();
		}
	}
	
	//Retour de l'image de base lors du relachement de la touche
	
	public void resetLayer(KeyCode code) {
		
		if (code==KeyCode.S) {
			this.fxLayer.setImage(face0);
			countFace=0;
		}
		if (code==KeyCode.Q) {
			this.fxLayer.setImage(left0);
			countLeft=0;
		}
		if (code==KeyCode.D) {
			this.fxLayer.setImage(right0);
			countRight=0;
		}
		if (code==KeyCode.Z) {
			this.fxLayer.setImage(back0);
			countBack=0;
		}
	}
	
	
	public void placeBomb(KeyCode code, Pane RBox, List<GameObject> gameObjectList, Text endMessage) {
		
		int nbSquareX = (int) (this.getPosX() / 50.0);
		int nbSquareY = (int) (this.getPosY() / 50.0);


		if (code==KeyCode.SPACE && maxBomb > currentBombNb )
		{
			Bomb bomb = new Bomb(this.gameTimer, bombRadius, endMessage);
			bomb.fxLayer.toFront();
			currentBombNb +=1;
			//place bomb in the good place
			double deltaX = this.getPosX() % 50.0;
			double deltaY = this.getPosY() % 50.0;
			
			//player on square
			if(deltaX == 0 && deltaY == 0) {
				bomb.setPosX(this.getPosX());
				bomb.setPosY(this.getPosY());
			}
			//adjust bomb position to the nearest square
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
			this.sendBombPositionToServer(bomb);
			bomb.fxLayer.setVisible(false);
			RBox.getChildren().add(bomb.fxLayer);
			bomb.startBomb(gameObjectList);

			
			//task to decount the bomb once explode
			TimerTask task = new TimerTask()
	    	{

	    	    @Override
	    	    public void run()
	    	    {
	    	    	currentBombNb -= 1;
	    	    
	    	    }

	    	};
			gameTimer.schedule(task, 3000);

			

		}


	}
	
	public void PlayerOnBonus(Pane RBox, List<GameObject> gameObjectList) {
		int nbSquareX = (int) (this.getPosX() / 50.0);
		double deltaX = this.getPosX() % 50.0;
		int nbSquareY = (int) (this.getPosY() / 50.0);
		double deltaY = this.getPosY() % 50.0;
		Bonus selectedBonus = null;
		
		//player on square
		if(deltaX == 0 && deltaY == 0) {
			for(GameObject object : gameObjectList) {
				if(object instanceof Bonus){
					if(object.getPosX() == nbSquareX*50.0 && object.getPosY() == nbSquareY*50.0) {
						selectedBonus = ((Bonus) object);
					}
				}
			}
				
		}
		//adjust player position to the nearest square
		else {
			if(deltaX > 50.0/2.0) {
				nbSquareX += 1;
			}
			if(deltaY > 50.0/2.0) {
				nbSquareY += 1;
			}
			for(GameObject object : gameObjectList) {
				if(object instanceof Bonus){
					if(object.getPosX() == nbSquareX*50.0 && object.getPosY() == nbSquareY*50.0) {
						selectedBonus = ((Bonus) object);
					}
				}
			}
			
		}
		
		//apply bonus to player
		if(selectedBonus != null) {
			this.sendBonusToServer(selectedBonus.getPosX(), selectedBonus.getPosY());
			//speed
			if(selectedBonus instanceof PlayerSpeedBonus) {
				speed += 5;
				TimerTask SpeedTask = new TimerTask()
		    	{

		    	    @Override
		    	    public void run()
		    	    {
		    	    	speed -= 5;
		    	    }
		    	};
				gameTimer.schedule(SpeedTask, 5000);
				
			}
			//bomb number
			else if(selectedBonus instanceof BombNumberBonus) {
				maxBomb += 1;
				TimerTask NumberTask = new TimerTask()
		    	{

		    	    @Override
		    	    public void run()
		    	    {
		    	    	maxBomb -= 1;   	    
		    	    }
		    	};
				gameTimer.schedule(NumberTask, 5000);
				
			}
			//bomb power
			else if(selectedBonus instanceof BombPowerBonus) {
				bombRadius += 1;
				TimerTask PowerTask = new TimerTask()
		    	{

		    	    @Override
		    	    public void run()
		    	    {
		    	    	bombRadius -= 1;		    	    
		    	    }
		    	};
				gameTimer.schedule(PowerTask, 5000);
				
			}
			selectedBonus.fxLayer.setVisible(false);
			gameObjectList.remove(selectedBonus);
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
