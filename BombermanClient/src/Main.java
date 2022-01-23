import java.io.File;
import java.io.IOException;


import controller.GameController;
import controller.MenuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


	
public class Main extends Application {
	
	public static void main(String[] args) {
        Application.launch(args);
	}
	
    //Chargement de la scene javafx
    @Override
    public void start(Stage stage) throws IOException
    {
    	try {
        //Création et affichage de la  fenêtre de menu

        FXMLLoader fxmlLoader = new FXMLLoader(new File("ressources/menu.fxml").toURI().toURL());     
        Pane root = (Pane) fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Bomberman Menu");
        scene.getRoot().requestFocus();

        //Récupération du userName 
        MenuController menuControl = (MenuController) fxmlLoader.getController();
        String Username = menuControl.getUserName();
        fxmlLoader.setControllerFactory(controllerClass -> new GameController(fxmlLoader.getController(),stage, Username)); 
        // Affichage de la fenêtre
        stage.show();
      
        //exit du programme complet à la fermeture
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
        		Platform.exit();
                System.exit(0);
            }
        });

        scene.getRoot().requestFocus();
        

    	}	catch (IOException e) {
			e.printStackTrace();
		}
         
    }
}