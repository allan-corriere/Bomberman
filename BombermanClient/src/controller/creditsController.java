package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class creditsController {

	@FXML VBox credits;

    public creditsController() {
    }
     
    @FXML
    private void initialize() 
    {

    }
    
    @FXML
    private void quit() {
    	Stage stage =  (Stage) credits.getScene().getWindow();
		stage.close();
    }
        
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
}
     
