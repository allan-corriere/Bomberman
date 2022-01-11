//import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import gamescene.GenerateMap;
import socket.SocketReader;
import socket.SocketWriter;

public class Connection {
	public static void main(String[] args) {
		final int port = 65432;
		
		Socket clients[] = new Socket[4];
		int numberOfClient = 0;
		
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
	    			new Thread(new SocketReader(clients[numberOfClient], numberOfClient, sw, map.getMap())).start();  
	    	        //new Thread(new SocketWriter(clients[numberOfClient])).start();
	    			numberOfClient++;
	    		}
	    	}
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
