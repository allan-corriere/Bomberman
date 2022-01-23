//import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TimerTask;

import gamescene.GenerateMap;
import gamescene.StartGame;
import player.Player;
import socket.SocketReader;
import socket.SocketWriter;

import sql.ConnectSql;

public class Connection {
	public static void main(String[] args) {
		final int port = 65432;
		
		Socket clients[] = new Socket[4];
		int numberOfClient = 0;
		
		Player players[] = new Player[4];

		for (int i= 0; i < 4; i++) {
			players[i] = new Player();
			players[i].setNumberOfPlayer(i);
		}
		
		GenerateMap map = new GenerateMap();

		//System.out.println(map.getMap());

		
		SocketWriter sw = new SocketWriter(clients);
		
		ServerSocket serverSocket = null; 
	    try {  
	    	serverSocket = new ServerSocket(port); 
	    	while(numberOfClient != 4) {
	    		Socket client = serverSocket.accept();
	    		if(client != null) {
	    			System.out.println("Client " + client.getInetAddress() + " connected with id " + numberOfClient);
	    			clients[numberOfClient] = client;

	    			new Thread(new SocketReader(players[numberOfClient], clients[numberOfClient], sw, map.getMap())).start();  
	    	        //new Thread(new SocketWriter(clients[numberOfClient])).start();
	    			numberOfClient++;
	    		}
	    	}
	    	//attente que tout les joueurs soient connectés
	    	int nbPlayerReady = 0;
	    	while(nbPlayerReady != 4) {
	    		nbPlayerReady = 0;
	    		for (int i= 0; i < 4; i++) {
		    		if(players[i].getUserName() != null) {
		    			nbPlayerReady += 1;
		    		}
		    	}
	    	}
	    	//envoi des usernames aux clients
	    	for (int i= 0; i < 4; i++) {
	    		sw.send("playerinfo:"+players[i].getUserName());
	    	}
	    	StartGame startGame = new StartGame(sw);
	    	startGame.start();
//	        Socket client = serverSocket.accept();
//	        new Thread(new SocketReader(client)).start();  
//	        new Thread(new SocketWriter(client)).start();
	    } catch (EOFException e) {
	    	System.out.println("Client disconnected");
	    } catch (Exception e) {  
	      e.printStackTrace();  
	    } finally{ 
	      try { 
	        if(serverSocket != null){ 
	          serverSocket.close(); 
	        } 
	      }catch (EOFException e) {
	    	  System.out.println("Client disconnected");
	      } catch (IOException e) { 
	        e.printStackTrace(); 
	      }
	    }
		
		
		
			
		
	    
	  }  
}
