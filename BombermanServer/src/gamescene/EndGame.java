package gamescene;

import java.util.Timer;
import java.util.TimerTask;

import player.Player;
import socket.SocketWriter;

public class EndGame {
	private SocketWriter sw;
	private Player players[];
	private int numberDead;

	
	public EndGame(Player [] players,SocketWriter sw) {
		this.sw = sw;
		this.players = players;
	}
	
	public void start() {
		Timer t = new Timer();
		TimerTask tend = new TimerTask() {
    		
			@Override
			public void run() {
				numberDead = 0;
				for(int i = 0; i < 4 ; i++) {
					if(!players[i].isAlive()) {
						numberDead += 1;
					}
				}
				if(numberDead == 3) {
					sw.send("gameover:");
					t.cancel();
				}
			}
    	};
    
    	t.scheduleAtFixedRate(tend, 0, 500);
	}
}
