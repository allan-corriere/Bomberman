import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import controller.MainController;
import controller.MenuController;
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
        
        //R�cup�ration du userName 
        MenuController menuControl = (MenuController) fxmlLoader2.getController();
        String Username = menuControl.getUserName();
        // Display the Stage
        stage.show();
        
        // Create the FXMLLoader 
        FXMLLoader loader = new FXMLLoader(new File("ressources/main_scene.fxml").toURI().toURL());
        loader.setControllerFactory(controllerClass -> new MainController(fxmlLoader2.getController(),stage2, Username));
        // Create the Pane and all Details
        Pane root = (Pane) loader.load();
        
        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("Main Scene");



        scene.getRoot().requestFocus();
        
        
    	}
    	
    	catch (IOException e) {
			e.printStackTrace();
		}
         
    }
}