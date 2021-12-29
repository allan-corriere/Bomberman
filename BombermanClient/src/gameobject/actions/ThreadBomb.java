package gameobject.actions;

import java.util.concurrent.TimeUnit;
import gameobject.Bomb;

public class ThreadBomb extends Thread {
	private Bomb bomb = new Bomb();
	
	public ThreadBomb(Bomb bomb){
		this.bomb = bomb;
	}
	
	public void run() {
		bomb.fxLayer.setVisible(true);
		try {
			TimeUnit.SECONDS.sleep(3);
		}
		catch (Exception e) {
			System.out.println("failed to start bomb");
		}
		bomb.fxLayer.setVisible(false);
	}
	
}
