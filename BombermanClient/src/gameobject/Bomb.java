package gameobject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gameobject.actions.ThreadBomb;
import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import gameobject.bonus.Bonus;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import socket.SocketWriter;
import animations.ExplodeAnims;

public class Bomb extends GameObject implements UnmovableObject {
	private int timer;
	private int radius;
	private boolean blockedXplus = false;
	private boolean blockedXminus = false;
	private boolean blockedYplus = false;
	private boolean blockedYminus = false ;
	private boolean destructXplus = false ; 
	private boolean destructXMinus = false ; 
	private boolean destructYPlus = false ; 
	private boolean destructYMinus = false ;
	
	//attribute for message prompting
	private Text mainMessage;
	private int enemyCount;
	private GameObject lastEnemy  = new GameObject();
	private Player player ;
	private SocketWriter sw;


	
	
	
private Image SmallBomb1 = new Image(new File("ressources/bomb.png").toURI().toString());
private Image BigBomb1 = new Image(new File("ressources/Bombes/bombs1/Bigbomb1.png").toURI().toString());


	
	public Bomb(Timer gameTimer,int radius, Text mainMessage, SocketWriter sw) {
		this.gameTimer = gameTimer;
		fxLayer = new ImageView(SmallBomb1);
		this.radius = radius;
		this.mainMessage = mainMessage;
		this.sw = sw;
		this.setPosX(0.0);
		this.setPosY(0.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
	}
	
	public void startBomb(List<GameObject> gameObjectList){
		ThreadBomb b1 = new ThreadBomb(this, gameObjectList);
		b1.start();
		
	}

	
	public void explode(List<GameObject> gameObjectList) {
		

		List<GameObject> objectToRemove = new ArrayList<GameObject>();
		enemyCount = 0;
		for(int i = 1; i <= radius; i++) {
			for (GameObject object : gameObjectList) {
				//count number of enemies alive
				if(object instanceof Enemy) {
					enemyCount += 1;
				}
				
				//get the approximation of position
				double objectPosX = (int) (object.getPosX() / 50.0);
				double deltaX = object.getPosX() % 50.0;
				double objectPosY = (int) (object.getPosY() / 50.0);
				double deltaY = object.getPosY() % 50.0;

				//player on square
				if(deltaX == 0 && deltaY == 0) {
					objectPosX = object.getPosX();
					objectPosY = object.getPosY();
				}
				//adjust bomb position to the nearest square
				else {
					if(deltaX > 50.0/2.0) {
						objectPosX += 1;
					
					}
					if(deltaY > 50.0/2.0) {
						objectPosY += 1;
					}
					objectPosX *= 50.0;
					objectPosY *= 50.0;
				}
				if(object instanceof DestructableObject) {
					//same pos
					if(radius == 1 && objectPosX == this.getPosX() && objectPosY == this.getPosY()) {
						objectToRemove.add(object);
					}
					// X plus
					else if(objectPosX == (this.getPosX()+50.0*i) && objectPosY == this.getPosY() && blockedXplus == false) {
						objectToRemove.add(object);
						destructXplus = true;
						
					}
					// X minus
					else if(objectPosX == (this.getPosX()-50.0*i) && objectPosY == this.getPosY() && blockedXminus == false) {
						objectToRemove.add(object);
						destructXMinus = true;
					}
					// Y plus
					else if(objectPosX == this.getPosX() && (objectPosY == this.getPosY()+50.0*i) && blockedYplus == false) {
						objectToRemove.add(object);
						destructYPlus = true;
					}
					// Y minus
					else if(objectPosX == this.getPosX() && (objectPosY == this.getPosY()-50.0*i) && blockedYminus == false) {
						objectToRemove.add(object);
						destructYMinus = true;
					} 
				}
				//check for blocking object in field including brick to ndo't break next object
				if(!(object instanceof DestructableObject) || object instanceof Brick) {
				// X plus
				if(objectPosX == (this.getPosX()+50.0*i) && objectPosY == this.getPosY()) {
					blockedXplus = true;
				}
				// X minus
				else if(objectPosX == (this.getPosX()-50.0*i) && objectPosY == this.getPosY()) {
					blockedXminus = true;
				}
				// Y plus
				else if(objectPosX == this.getPosX() && (objectPosY == this.getPosY()+50.0*i)) {
					blockedYplus = true;
				}
				// Y minus
				else if(objectPosX == this.getPosX() && (objectPosY == this.getPosY()-50.0*i)) {
					blockedYminus = true;
				} 
				}
			}
		}
		//remove object from the game
		for(GameObject object : objectToRemove) {
			object.fxLayer.setVisible(false);
			if(object instanceof Brick) { //prompt when  bonus
				if(((Brick) object).brickBonus != null) { //bricks
					Bonus bonus = ((Brick) object).brickBonus;
					//gameObjectList.remove(object);
					bonus.fxLayer.setVisible(true);		
					gameObjectList.add(bonus);
				}
			}
			gameObjectList.remove(object);
			
			if(object instanceof Player) {
				if(((Player) object).isAlive()) { // send death to server
					((Player) object).setAlive(false);
					this.sw.send("playerdeath:");
				}
				
				this.player = (Player)object;
				if(enemyCount != 1) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() { //player dead
							mainMessage.setVisible(true);
							if(!mainMessage.getText().contains("Vous avez gagné !!!")) { // si la bombe explose à la fin de la partie
								mainMessage.setText("Vous êtes mort !\n"+"Il reste "+(enemyCount)+" joueurs en vie");
							}
						}
					});
				}else {
					for(GameObject listenemy : gameObjectList) {
						if(listenemy instanceof Enemy) {
							lastEnemy = listenemy;
						}
					}
					Platform.runLater(new Runnable() {
						@Override
						public void run() { //player dead
							mainMessage.setVisible(true);
							mainMessage.setText("Vous êtes mort !\n Le joueur n°"+((Enemy)lastEnemy).getPlayerNumber()+" "+((Enemy)lastEnemy).getUserName()+" remporte la partie");
						}
					});
				}
			}
			else if(object instanceof Enemy) {
				if(mainMessage.isVisible()) { //si message affiché = joueur mort
					if(enemyCount-1 == 1) {
						for(GameObject listenemy : gameObjectList) {
							if(listenemy instanceof Enemy) {
								lastEnemy = listenemy;
							}
						}
						Platform.runLater(new Runnable() { //win enemy
							@Override
							public void run() { //player dead
								mainMessage.setVisible(true);
								mainMessage.setText("Vous êtes mort !\n Le joueur n°"+((Enemy)lastEnemy).getPlayerNumber()+" "+((Enemy)lastEnemy).getUserName()+" remporte la partie");
							}
						});
					}else if(enemyCount-1 != 0) { // -1 enemy
						Platform.runLater(new Runnable() {
							@Override
							public void run() { //player dead
								mainMessage.setVisible(true);
								mainMessage.setText("Vous êtes mort !\n"+"Il reste "+(enemyCount-1)+" joueurs en vie");
							}
						});
					}
				}else { //player still alive
					if(enemyCount-1 == 0) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								mainMessage.setVisible(true);
								mainMessage.setText("Vous avez gagné !!!");
							}
						});
					}
				}
			}
			if(object instanceof Player || object instanceof Enemy) { //text center
				Platform.runLater(new Runnable() {
					@Override
					public void run() { //player dead
						Pane RBox = (Pane)fxLayer.getParent();
						mainMessage.setTextAlignment(TextAlignment.CENTER);
				        mainMessage.layoutXProperty().bind(RBox.widthProperty().subtract(mainMessage.prefWidth(-1)).divide(2));
				        mainMessage.layoutYProperty().bind(RBox.heightProperty().subtract(mainMessage.prefHeight(-1)).divide(2));
					}
				});
			}
			
		}
		
		
		
		// Animation explosion bombe 
		


		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				System.out.println("bombexplode");
				Pane RBox = (Pane)fxLayer.getParent();
				ExplodeAnims bombAnim = new ExplodeAnims(gameObjectList, RBox,getPosX(), getPosY(), radius, blockedXplus, blockedYplus, blockedXminus, blockedYminus, destructXplus, destructXMinus, destructYPlus, destructYMinus);
				bombAnim.setVisible();
				
			}
		});
		

	}
	


	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}	
	
}
