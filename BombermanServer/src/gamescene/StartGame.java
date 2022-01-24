package gamescene;

import java.util.Timer;
import java.util.TimerTask;

import player.Player;
import socket.SocketWriter;

public class StartGame {
	private boolean currentlyPlaying =false;
	private Player [] players;
	
	public StartGame(Player [] players) {
		this.players = players;
	}
	
	public void start(SocketWriter sw) {
		Timer t = new Timer();
		TimerTask t5 = new TimerTask() {
    		
			@Override
			public void run() {
				sw.send("gamestart:5");
			}
    	};
    	TimerTask t4 = new TimerTask() {
    		
			@Override
			public void run() {
				sw.send("gamestart:4");
			}
    	};
    	TimerTask t3 = new TimerTask() {
    		
			@Override
			public void run() {
				sw.send("gamestart:3");
			}
    	};
    	TimerTask t2 = new TimerTask() {
    		
			@Override
			public void run() {
				sw.send("gamestart:2");
			}
    	};
    	TimerTask t1 = new TimerTask() {
    		
			@Override
			public void run() {
				sw.send("gamestart:1");
			}
    	};
    	TimerTask t0 = new TimerTask() {
    		
			@Override
			public void run() {
				sw.send("gamestart:0");
				for(int i = 0; i < players.length; i++) {
					if(!players[i].isAlive()) {
						sw.send("dead:"+players[i].getNumberOfPlayer());
					}
				}
				currentlyPlaying = true;
			}
    	};
    	t.schedule(t5, 0);
    	t.schedule(t4, 1000);
    	t.schedule(t3, 2000);
    	t.schedule(t2, 3000);
    	t.schedule(t1, 4000);
    	t.schedule(t0, 5000);
	}

	public boolean isCurrentlyPlaying() {
		return currentlyPlaying;
	}

	public void setCurrentlyPlaying(boolean currentlyPlaying) {
		this.currentlyPlaying = currentlyPlaying;
	}
	
}
