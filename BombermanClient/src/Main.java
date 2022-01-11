import java.io.File;
import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import socket.SocketReader;
import socket.SocketWriter;

	
public class Main extends Application {
	
	public static void main(String[] args) {
		//Connection conn = new Connection(65432, "localhost", "Osloh");
		
//		try { 
//		      // instantiation 1 a Socket , and specify the server address and port  
//		      //GameClient client = new GameClient("localhost", 65432, "Osloh"); 
//		      // Start two threads, 1 The individual is responsible for reading, 1 Responsible for writing  
//		      //new Thread(new SocketReader(client)).start(); 
//		      //new Thread(new SocketWriter(client)).start(); 
//		    } catch (ConnectException e) {
//		    	System.out.println("Unable to connect to server");
//		    } catch (EOFException e) {
//		    	System.out.println("Server connection lost");
//		    } catch (Exception e) { 
//		      e.printStackTrace(); 
//		    }
        Application.launch(args);
	}
	
    //Chargement de la scene javafx
	
    @Override
    public void start(Stage stage) throws IOException
    {
    	try {
        
        
        //Cr�ation et affichage de la  fen�tre de menu
        
        FXMLLoader fxmlLoader2 = new FXMLLoader(new File("ressources/menu.fxml").toURI().toURL());     
        VBox root1 = (VBox) fxmlLoader2.load();
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(root1);
        stage2.setScene(scene2);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initStyle(StageStyle.UNDECORATED);
        stage2.setTitle("Bomberman Menu");
        scene2.getRoot().requestFocus();
        stage2.showAndWait();
        

        stage.show();
        
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
        
        scene.getRoot().requestFocus();
        
        
    	}
    	
    	catch (IOException e) {
			e.printStackTrace();
		}
         
    }
}