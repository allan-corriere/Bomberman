//import java.io.BufferedReader;
import java.io.EOFException;

//import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


import gamescene.EndGame;
import gamescene.GenerateMap;
import gamescene.StartGame;
import player.Player;
import socket.SocketReader;
import socket.SocketWriter;

public class Connection {
	public static void main(String[] args) {
		boolean ServerRun = true;
		while(ServerRun) {
			final int port = 65432;
			System.out.println("New Server instance running");
			Socket clients[] = new Socket[4];
			int numberOfClient = 0;
			
			Player players[] = new Player[4];

			for (int i= 0; i < 4; i++) {
				players[i] = new Player();
				players[i].setAlive(true);
				players[i].setNumberOfPlayer(i);
			}
			
			GenerateMap map = new GenerateMap();
			//ouverture du serveur à 4 clients
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
		    			numberOfClient++;
		    		}
		    	}
		    	//fermeture des connexions ultérieurs
		    	serverSocket.close();
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
		    	//envoi des usernames aux clients et retrait des joueurs déconnectés
		    	for (int i= 0; i < 4; i++) {
		    		sw.send("playerinfo:"+players[i].getUserName());
		    		if(!players[i].isAlive()) {
		    			sw.send("dead:"+players[i].getNumberOfPlayer());
		    		}
		    	}
		    	//timer de démarrage
		    	StartGame startGame = new StartGame(sw);
		    	startGame.start();
		    	
		    	//Gestion de la fin d'une partie
		    	EndGame endGame = new EndGame(players, sw, serverSocket, clients);
		    	endGame.start();
		    	

		    } catch (EOFException e) {
		    	System.out.println("Client disconnected");
		    	ServerRun =false;
		    } catch (Exception e) {  
		      e.printStackTrace();
		      ServerRun = false;
		    }
		}
		
		
		
		
			
		
	    
	  }  
}
