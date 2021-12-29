package gameobject;

import gameobject.actions.ThreadBomb;
import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;

public class Bomb extends GameObject implements DestructableObject, UnmovableObject {
	

	private int timer;
	private int radius;
	
	public void startBomb(){
		ThreadBomb b1 = new ThreadBomb(this);
		b1.start();
		
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
