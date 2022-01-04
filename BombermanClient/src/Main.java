import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import gameobject.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import socket.SocketReader;
import socket.SocketWriter;
	
public class Main extends Application {
	
	public static void main(String[] args) {
		//Connection conn = new Connection(65432, "localhost", "Osloh");
		
		try { 
		      // instantiation 1 a Socket , and specify the server address and port  
		      Socket client = new Socket("localhost", 65432); 
		      // Start two threads, 1 The individual is responsible for reading, 1 Responsible for writing  
		      new Thread(new SocketReader(client)).start(); 
		      new Thread(new SocketWriter(client)).start(); 
		    } catch (ConnectException e) {
		    	System.out.println("Unable to connect to server");
		    } catch (EOFException e) {
		    	System.out.println("Server connection lost");
		    } catch (Exception e) { 
		      e.printStackTrace(); 
		    }
        Application.launch(args);
	}
	
    //Chargement de la scene javafx
	
    @Override
    public void start(Stage stage) throws IOException
    {
    	try {
        // Create the FXMLLoader 
        FXMLLoader loader = new FXMLLoader(new File("ressources/main_scene.fxml").toURI().toURL());
        // Path to the FXML File
       /* String fxmlDocPath = "ressources/main_scene.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);*/
 
        // Create the Pane and all Details
        Pane root = (Pane) loader.load();
        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("Main Scene");
        // Display the Stage
        stage.show();
        scene.getRoot().requestFocus();
    	}
    	
    	catch (IOException e) {
			e.printStackTrace();
		}
         
    }
}