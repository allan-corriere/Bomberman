package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class errorController {

	
	private String message;
	@FXML 
	private Label errorMessage;
	

    public errorController(String Message) {
    	this.message = Message;
    }
     
    @FXML
    private void initialize() 
    {
    	errorMessage.setText(message);
    }
    
    @FXML
    private void quit() {
    	Stage stage =  (Stage) errorMessage.getScene().getWindow();
		stage.close();
    }
        
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
}
     

