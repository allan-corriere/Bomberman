package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sql.ConnectSql;

public class HighScoreController {

	@FXML 
	private Label player1;
	@FXML 
	private Label player2;
	@FXML 
	private Label player3;	
	@FXML 
	private Label player4;
	@FXML 
	private Label player5;
	@FXML 
	private Label player6;
	@FXML 
	private Label player7;
	@FXML 
	private Label player8;
	@FXML 
	private Label player9;
	@FXML
	private Label player10;
	
	
	@FXML private Label victory1;
	@FXML private Label victory2;
	@FXML private Label victory3;
	@FXML private Label victory4;
	@FXML private Label victory5;
	@FXML private Label victory6;
	@FXML private Label victory7;
	@FXML private Label victory8;
	@FXML private Label victory9;
	@FXML private Label victory10;
	
	private String ip;
	private Stage menuDisplay;
	
	@FXML 
	private VBox VBox;
	
    public HighScoreController(Stage menu, String ipServ) 
    {
    	this.ip=ipServ;
    	this.menuDisplay = menu;
    }
     
    @FXML
    private void initialize() 
    {
    	ConnectSql dBCon = new ConnectSql(ip,"bombermandb","bomberuser","bombermdp");
    	try {
    		ArrayList<String> test = dBCon.getTab();
    		int i=1;
    		for (String obj : test) {
    			if (i==1)player1.setText(obj);
    			if (i==2)victory1.setText(obj);
    			if (i==3)player2.setText(obj);
    			if (i==4)victory2.setText(obj);
    			if (i==5)player3.setText(obj);
    			if (i==6)victory3.setText(obj);
    			if (i==7)player4.setText(obj);
    			if (i==8)victory4.setText(obj);
    			if (i==9)player5.setText(obj);
    			if (i==10)victory5.setText(obj);
    			i++;				
    			}
    	}catch(Exception e) {
    		//tâche de retour au menu
    		Timer t = new Timer();
    		TimerTask tfailed = new TimerTask() {
        		
    			@Override
    			public void run() {
    				failedToConnect();
    			}
        	};
        	t.schedule(tfailed, 10);
    	}
    }
    
    
	
    @FXML
    private void quit() {
    	Stage stage =  (Stage) victory1.getScene().getWindow();
		stage.close();
    }



     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
    
    public void failedToConnect() {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() { //player dead
				//Stage stage =  (Stage) VBox.getScene().getWindow();
			//	stage.close();
				//retour au menu
				menuDisplay.show();
				//page d'erreur
				FXMLLoader fxmlLoader2;
				try {
					fxmlLoader2 = new FXMLLoader(new File("ressources/erreur.fxml").toURI().toURL());
				    fxmlLoader2.setControllerFactory(controllerClass -> new errorController("Le serveur n'est pas disponible"));
				    VBox root1 = (VBox) fxmlLoader2.load();
				    Stage stage2 = new Stage();
				    Scene scene2 = new Scene(root1);
				    stage2.setScene(scene2);
				    stage2.initModality(Modality.APPLICATION_MODAL);
				    stage2.initStyle(StageStyle.UNDECORATED);
				    stage2.setTitle("Erreur");
				    scene2.getRoot().requestFocus();
				    stage2.showAndWait();
				}
			    catch (MalformedURLException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
			}
		});
    }
}
     
