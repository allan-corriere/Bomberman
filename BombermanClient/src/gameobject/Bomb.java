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
	boolean blockedXplus = false;
	boolean blockedXminus = false;
	boolean blockedYplus = false;
	boolean blockedYminus = false ;
	
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
				if(object instanceof DestructableObject) {
					//same pos
					if(radius == 1 && object.getPosX() == this.getPosX() && object.getPosY() == this.getPosY()) {
						objectToRemove.add(object);
					}
					// X plus
					else if(object.getPosX() == (this.getPosX()+50.0*i) && object.getPosY() == this.getPosY() && blockedXplus == false) {
						objectToRemove.add(object);
					}
					// X minus
					else if(object.getPosX() == (this.getPosX()-50.0*i) && object.getPosY() == this.getPosY() && blockedXminus == false) {
						objectToRemove.add(object);
					}
					// Y plus
					else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()+50.0*i) && blockedYplus == false) {
						objectToRemove.add(object);
					}
					// Y minus
					else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()-50.0*i) && blockedYminus == false) {
						objectToRemove.add(object);
					} 
				}
				//check for blocking object in field including brick to ndo't break next object
				if(!(object instanceof DestructableObject) || object instanceof Brick) {
				// X plus
				if(object.getPosX() == (this.getPosX()+50.0*i) && object.getPosY() == this.getPosY()) {
					blockedXplus = true;
				}
				// X minus
				else if(object.getPosX() == (this.getPosX()-50.0*i) && object.getPosY() == this.getPosY()) {
					blockedXminus = true;
				}
				// Y plus
				else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()+50.0*i)) {
					blockedYplus = true;
				}
				// Y minus
				else if(object.getPosX() == this.getPosX() && (object.getPosY() == this.getPosY()-50.0*i)) {
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
				ExplodeAnims bombAnim = new ExplodeAnims(gameObjectList, RBox,getPosX(), getPosY(), radius, blockedXplus, blockedYplus, blockedXminus, blockedYminus);
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
