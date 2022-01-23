package gamescene;

import java.util.Timer;
import java.util.TimerTask;

import socket.SocketWriter;

public class StartGame {
	private SocketWriter sw;
	
	public StartGame(SocketWriter sw) {
		this.sw = sw;
	}
	
	public void start() {
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
			}
    	};
    	t.schedule(t5, 0);
    	t.schedule(t4, 1000);
    	t.schedule(t3, 2000);
    	t.schedule(t2, 3000);
    	t.schedule(t1, 4000);
    	t.schedule(t0, 5000);
	}
}
