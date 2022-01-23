package gamescene;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import player.Player;
import socket.SocketWriter;
import sql.ConnectSql;

public class EndGame {
	private SocketWriter sw;
	private ServerSocket serverSocket;
	private Player players[];
	private int numberDead;
	private Socket clients[] = new Socket[4];
	private boolean gameOver = false;
	private String winner;

	
	public EndGame(Player [] players,SocketWriter sw,ServerSocket serverSocket, Socket clients[]) {
		this.sw = sw;
		this.serverSocket =serverSocket;
		this.players = players;
		this.clients = clients;
	}
	
	public void start() {
		Timer t = new Timer();
		TimerTask tend = new TimerTask() { //check for match to end
    		
			@Override
			public void run() {
				numberDead = 0;
				for(int i = 0; i < 4 ; i++) {
					if(!players[i].isAlive()) {
						numberDead += 1;
					}else {
						winner = players[i].getUserName();
					}
				}
				if(numberDead == 3) { //winner
					sw.send("gameover:win");
			    	ConnectSql dBCon = new ConnectSql("test","toto","toto");
					dBCon.WriteTab(winner);
				}
				else if(numberDead == 4) { //draw match
					sw.send("gameover:draw");
					
				}
				if(numberDead == 3 || numberDead == 4) {
					t.cancel();
					

					    try {
					    	serverSocket.close();
					    	System.out.println("server socket closed");
					    	for(int i = 0; i < 4 ; i++) {
					    		clients[i].close();
					    		System.out.println("Connection with client "+i+" closed");
					    	}
					    	gameOver = true;
					    } catch (IOException e) { /* failed */ }
				
				}
				
			}
    	};
    
    	t.scheduleAtFixedRate(tend, 0, 500);
    	while(!gameOver) {
    		try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
}
