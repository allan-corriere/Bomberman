//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import socket.SocketReader;
import socket.SocketWriter;

public class Connection {
	public static void main(String[] args) {
		final int port = 65432;
		
		ServerSocket serverSocket = null; 
	    try {  
	      serverSocket = new ServerSocket(port);  
	      while (true) {  
	        Socket client = serverSocket.accept();  
	        //1 Two client connections on account opening handle reads and writes   
	        new Thread(new SocketReader(client)).start();  
	        new Thread(new SocketWriter(client)).start();  
	      }  
	    } catch (Exception e) {  
	      e.printStackTrace();  
	    } finally{ 
	      try { 
	        if(serverSocket != null){ 
	          serverSocket.close(); 
	        } 
	      } catch (IOException e) { 
	        e.printStackTrace(); 
	      } 
	    } 
	  }  
		
//		try (ServerSocket socketServeur = new ServerSocket(port)) {
//			System.out.println("Lancement du serveur");
//			while (true) {
//				try (
//					Socket socketClient = socketServeur.accept();
//					BufferedReader in = new BufferedReader(
//							new InputStreamReader(socketClient.getInputStream())
//					);
//				) {
//					System.out.println("Connexion avec : " + socketClient.getInetAddress());
//					String message = in.readLine();
//					System.out.println(message);
//				}
//			}
//		} catch (IOException e) {
//			System.out.println("Erreur sur le port " + port + " ou erreur de connexion");
//		}
//	}
}
