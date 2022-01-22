package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sql.ConnectSql;

public class MenuController {
	@FXML 
	private VBox Menu;
		
	private String userName;
	
	@FXML
	private Label newGame;
	
	@FXML
	private Label exit;
	
	@FXML private TextField pseudo;
	
	
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
		if (pseudo.getText()!="")
		{
			Label test = (Label)event.getSource();
		    Stage stage = (Stage)test.getScene().getWindow();
		    userName = pseudo.getText();
		    stage.close();
		}
		else 
		{
			final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(exit.getScene().getWindow());
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("Veuillez saisir un pseudo"));
            Scene dialogScene = new Scene(dialogVbox, 200, 20);
            dialog.setScene(dialogScene);
            dialog.show();
		}
	}
	
	@FXML
	private void highscore(MouseEvent event) {
		try
	    {
		    FXMLLoader fxmlLoader2 = new FXMLLoader(new File("ressources/highscores.fxml").toURI().toURL());     
		    VBox root1 = (VBox) fxmlLoader2.load();
		    Stage stage2 = new Stage();
		    Scene scene2 = new Scene(root1);
		    stage2.setScene(scene2);
		    stage2.initModality(Modality.APPLICATION_MODAL);
		    stage2.initStyle(StageStyle.DECORATED);
		    stage2.setTitle("HighScores");
		    scene2.getRoot().requestFocus();
		    stage2.showAndWait();
	    }
	
    	catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@FXML 
	private void creditsClik(MouseEvent event) {
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
	
	public String getUserName() {
		return userName;
	}
	
	
	//Passe le champ pseudo en inactif 
	public void setFieldDisable() {
		pseudo.setEditable(false);
	}
	

     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     

     
}
