import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import controller.GameController;
import controller.MenuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import socket.SocketReader;
import socket.SocketWriter;

	
public class Main extends Application {
	
	
	public static void main(String[] args) {
        Application.launch(args);
	}
	
    //Chargement de la scene javafx
	
    @Override
    public void start(Stage stage) throws IOException
    {
    	try {
        
       
        //Cr�ation et affichage de la  fen�tre de menu

        FXMLLoader fxmlLoader = new FXMLLoader(new File("ressources/menu.fxml").toURI().toURL());     
        Pane root = (Pane) fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Bomberman Menu");
        scene.getRoot().requestFocus();

        
        //R�cup�ration du userName 
        MenuController menuControl = (MenuController) fxmlLoader.getController();
        String Username = menuControl.getUserName();
        // Display the Stage
        stage.show();
        
//        // Create the FXMLLoader 
//        FXMLLoader loader = new FXMLLoader(new File("ressources/main_scene.fxml").toURI().toURL());
         fxmlLoader.setControllerFactory(controllerClass -> new GameController(fxmlLoader.getController(),stage, Username));
//        // Create the Pane and all Details
//        Pane root = (Pane) loader.load();
//        
//        // Create the Scene
//        Scene scene = new Scene(root); 
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
;
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
        		Platform.exit();
                System.exit(0);
            }
        });


        scene.getRoot().requestFocus();
        
        
    	}
    	
    	catch (IOException e) {
			e.printStackTrace();
		}
         
    }
}