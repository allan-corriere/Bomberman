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

	
	
	
private Image image = new Image(new File("ressources/bomb.png").toURI().toString());
	
	public Bomb(Timer gameTimer,int radius) {
		this.gameTimer = gameTimer;
		fxLayer = new ImageView(image);
		this.radius = radius;
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

		for(int i = 1; i <= radius; i++) {
			for (GameObject object : gameObjectList) {
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
		for(GameObject object : objectToRemove) {
			object.fxLayer.setVisible(false);
			
			if(object instanceof Brick) { //prompt when  bonus
				if(((Brick) object).brickBonus != null) {
					Bonus bonus = ((Brick) object).brickBonus;
					gameObjectList.remove(object);
					bonus.fxLayer.setVisible(true);		
					gameObjectList.add(bonus);
				}
				else {
					gameObjectList.remove(object);
				}
			}
			else {
				gameObjectList.remove(object);
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
