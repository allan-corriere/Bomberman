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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
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
	
	//deplacement
	private boolean zPressed = false;
	private boolean qPressed = false;
	private boolean sPressed = false;
	private boolean dPressed = false;
	
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private ArrayList<String> keyPressed = new ArrayList<>();
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
	
	private boolean left_right;;

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

	}
	
	public void sendPositionToServer() {
		this.sw.send("move:"+this.getPosX()+":"+this.getPosY());
	}
	
	public void sendBombPositionToServer(Bomb bomb) {
		this.sw.send("bomb:"+bomb.getPosX()+":"+bomb.getPosY()+":"+this.bombRadius);
	}

	public void movePress(KeyCode code,Pane RBox, List<GameObject> gameObjectList) {
		if (code==KeyCode.Z) {
			if(!zPressed) {
				keyPressed.add("up");
			}
			zPressed = true;
		}else if (code==KeyCode.Q) {
			if(!qPressed) {
				keyPressed.add("left");
			}
			qPressed = true;
		}		
		else if (code==KeyCode.S) {
			if(!sPressed) {
				keyPressed.add("down");
			}
			sPressed = true;
		}
		else if (code==KeyCode.D) {
			if(!dPressed) {
				keyPressed.add("right");
			}
			dPressed = true;
		}
		

	}
	
	public void movingRelease(KeyCode code) {
		if (code==KeyCode.Z) {
			if(zPressed) {
				System.out.println(keyPressed.size());
				keyPressed.remove("up");
				System.out.println(keyPressed.size());
			}
			zPressed = false;
		}else if (code==KeyCode.Q) {
			if(qPressed) {
				System.out.println(keyPressed.size());
				keyPressed.remove("left");
				System.out.println(keyPressed.size());
			}
			qPressed = false;
		}
		else if (code==KeyCode.S) {
			if(sPressed) {
				System.out.println(keyPressed.size());
				keyPressed.remove("down");
				System.out.println(keyPressed.size());
			}
			sPressed = false;
		}
		else if (code==KeyCode.D) {
			if(dPressed) {
				System.out.println(keyPressed.size());
				keyPressed.remove("right");
				System.out.println(keyPressed.size());
			}
			dPressed = false;
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
	
	
	public void placeBomb(KeyCode code, Pane RBox, List<GameObject> gameObjectList) {
		if (code==KeyCode.SPACE && maxBomb > currentBombNb )
		{
			Bomb bomb = new Bomb(this.gameTimer, bombRadius);
			bomb.fxLayer.toFront();
			currentBombNb +=1;
			//place bomb in the good place
			int nbSquareX = (int) (this.getPosX() / 50.0);
			double deltaX = this.getPosX() % 50.0;
			int nbSquareY = (int) (this.getPosY() / 50.0);
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

	public boolean iszPressed() {
		return zPressed;
	}

	public void setzPressed(boolean zPressed) {
		this.zPressed = zPressed;
	}

	public boolean isqPressed() {
		return qPressed;
	}

	public void setqPressed(boolean qPressed) {
		this.qPressed = qPressed;
	}

	public boolean issPressed() {
		return sPressed;
	}

	public void setsPressed(boolean sPressed) {
		this.sPressed = sPressed;
	}

	public boolean isdPressed() {
		return dPressed;
	}

	public void setdPressed(boolean dPressed) {
		this.dPressed = dPressed;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public ArrayList<String> getKeyPressed() {
		return keyPressed;
	}

	public void setKeyPressed(ArrayList<String> keyPressed) {
		this.keyPressed = keyPressed;
	}	
	
}
