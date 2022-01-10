package gameobject.actions;


import java.util.*;
import gameobject.Bomb;
import gameobject.attribute.GameObject;

public class ThreadBomb extends Thread {
	private Bomb bomb;
	private List<GameObject> gameObjectList = null;

	public ThreadBomb(Bomb bomb, List<GameObject> gameObjectList){
		this.bomb = bomb;
		this.gameObjectList = gameObjectList;
	}
	
	class BombTask extends TimerTask  {
		List<GameObject> gameObjectList;

	     public BombTask(List<GameObject> gameObjectList) {
	         this.gameObjectList = gameObjectList;
	     }

	     @Override
	     public void run() {
	         bomb.explode(gameObjectList);
	 		bomb.fxLayer.setVisible(false);
			gameObjectList.remove(bomb);
	     }
	}
	
	

	
	public void run() {
		BombTask taskExplode = new BombTask(gameObjectList);
		bomb.gameTimer.schedule(taskExplode, 3000);
		bomb.fxLayer.setVisible(true);
	}
	
}
