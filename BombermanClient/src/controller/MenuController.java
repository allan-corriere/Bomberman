package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class MenuController {
	@FXML 
	private VBox Menu;
	
	@FXML
	private Label newGame;
	
	@FXML
	private Label exit;
	
    public MenuController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	
    }
    
    
	@FXML
	private void controlMenu(KeyEvent event) {
		System.out.println(event.getCode());
	}

	@FXML
	private void begin(MouseEvent event) {
		Label test = (Label)event.getSource();
	    Stage stage = (Stage)test.getScene().getWindow();
	    stage.close();
	}
		
	@FXML 
	private void creditsClik(MouseEvent event) {
		System.out.println("OK");
	    try
	    {
		    FXMLLoader fxmlLoader2 = new FXMLLoader(new File("ressources/credits.fxml").toURI().toURL());     
		    VBox root1 = (VBox) fxmlLoader2.load();
		    Stage stage2 = new Stage();
		    Scene scene2 = new Scene(root1);
		    stage2.setScene(scene2);
		    stage2.initModality(Modality.APPLICATION_MODAL);
		    stage2.initStyle(StageStyle.DECORATED);
		    stage2.setTitle("Crédits");
		    scene2.getRoot().requestFocus();
		    stage2.showAndWait();
	    }
    	catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void end(MouseEvent event) {
		Platform.exit();
        System.exit(0);
	}
	@FXML
	private void exited(MouseEvent event) {
		
		//Récupération de l'id du container cliqué : 
		Label test = (Label)event.getSource();
		test.setStyle(null);
	}
	
	@FXML
	private void entered(MouseEvent event) {
		
		//Récupération de l'id du container cliqué : 
		Label test = (Label)event.getSource();
		test.setStyle("-fx-background-color:#ffc200;");
	}
	
	

     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     

     
}
