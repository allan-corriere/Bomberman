import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import gameobject.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
	
public class Main extends Application {
	
	public static void main(String[] args) {
		Connection conn = new Connection(65432, "localhost", "Osloh");
		
		try {
			conn.connect();
			conn.sendPositionData(100, 100);
			conn.sendPositionData(100, 102);
			conn.sendPositionData(100, 104);
			conn.closeConnection();
		} catch (Exception e) {
			System.err.println(e.getMessage());
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