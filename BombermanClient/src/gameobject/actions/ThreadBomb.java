package gameobject.actions;

import java.util.concurrent.TimeUnit;
import java.util.*;
import gameobject.Bomb;
import gameobject.attribute.GameObject;

public class ThreadBomb extends Thread {
	private Bomb bomb = new Bomb();
	private List<GameObject> gameObjectList = null;
	
	public ThreadBomb(Bomb bomb, List<GameObject> gameObjectList){
		this.bomb = bomb;
		this.gameObjectList = gameObjectList;
	}
	
	public void run() {
		bomb.fxLayer.setVisible(true);
		try {
			TimeUnit.SECONDS.sleep(3);
		}
		catch (Exception e) {
			System.out.println("failed to start bomb");
		}
		System.out.println(gameObjectList.size());
		bomb.explode(gameObjectList);
		bomb.fxLayer.setVisible(false);
		gameObjectList.remove(bomb);
	}
	
}
