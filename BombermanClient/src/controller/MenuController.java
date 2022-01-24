package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MenuController {
	@FXML 
	private VBox Menu;
		
	private String userName;
	
	@FXML
	private Label newGame;
	
	@FXML
	private Label exit;
	
	
	@FXML private TextField pseudo;
	@FXML private TextField ip;
	
	public static final String IP_ADDRESS_PATTERN = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	private String message;
	
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
	
	private boolean verifIP(String ip) {
		Matcher matcher = Pattern.compile(IP_ADDRESS_PATTERN).matcher(ip);
		if (matcher.find()) return true;
		else return false;
	}

	
	//Clique sur lancer la partie
	@FXML
	private void begin(MouseEvent event) {
		if (pseudo.getText()!="" && verifIP(ip.getText())==true)
		{
			

			Label test = (Label)event.getSource();
		    Stage stage = (Stage)test.getScene().getWindow();
		    userName = pseudo.getText();
		    stage.close();

	    	 // Create the FXMLLoader 
	        FXMLLoader loader;
			try { // lancement du jeu
				loader = new FXMLLoader(new File("ressources/main_scene.fxml").toURI().toURL());
			
		        loader.setControllerFactory(controllerClass -> new GameController(this,(Stage)pseudo.getScene().getWindow(), pseudo.getText()));
		        // Create the Pane and all Details
		        Pane root = (Pane) loader.load();
		        
			    Stage stage2 = new Stage();
			    stage2.initModality(Modality.APPLICATION_MODAL);
		        stage2.initStyle(StageStyle.DECORATED);
		        // Create the Scene
		        Scene scene = new Scene(root);
		        // Set the Scene to the Stage
		        stage2.setScene(scene);

		        // Set the Title to the Stage
		        stage2.setTitle("Main Scene");
		        stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
		            public void handle(WindowEvent we) {
		        		Platform.exit();
		                System.exit(0);
		            }
		        });
		        scene.getRoot().requestFocus();
		        stage2.show();
		        
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
			
		}
		else 
		{
			if (verifIP(ip.getText())==true) {
				message = "PSEUDO OBLIGATOIRE !!!";
			}
			if (pseudo.getText()!="") {
				message = "ADRESSE IP INCORRECTE!";
			}
			else 
				message = "REMPLIR TOUS LES CHAMPS!";
			FXMLLoader fxmlLoader2;
			try { // message d'erreur
				fxmlLoader2 = new FXMLLoader(new File("ressources/erreur.fxml").toURI().toURL());
			    fxmlLoader2.setControllerFactory(controllerClass -> new errorController(message));
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
		    catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
		}
	}
	
	@FXML
	private void highscore(MouseEvent event) {
		if (verifIP(ip.getText())==true){
			try
		    {
				//chargement page highscore
			    FXMLLoader fxmlLoader2 = new FXMLLoader(new File("ressources/highscores.fxml").toURI().toURL());
			    fxmlLoader2.setControllerFactory(controllerClass -> new HighScoreController(ip.getText()));
			    VBox root1 = (VBox) fxmlLoader2.load();
			    Stage stage2 = new Stage();
			    Scene scene2 = new Scene(root1);
			    stage2.setScene(scene2);
			    stage2.initModality(Modality.APPLICATION_MODAL);
			    stage2.initStyle(StageStyle.UNDECORATED);
			    stage2.setTitle("HighScores");
			    scene2.getRoot().requestFocus();
			    stage2.showAndWait();
				}
		    
	    	catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			message = "SAISIR UNE ADRESSE IP!";
			FXMLLoader fxmlLoader2;
			try { //message d'erreur
				fxmlLoader2 = new FXMLLoader(new File("ressources/erreur.fxml").toURI().toURL());
			    fxmlLoader2.setControllerFactory(controllerClass -> new errorController(message));
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
		    catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}     
		}
			
	    
	}
		
	@FXML 
	private void creditsClik(MouseEvent event) {
	    try
	    { //load crédits
		    FXMLLoader fxmlLoader2 = new FXMLLoader(new File("ressources/credits.fxml").toURI().toURL());     
		    VBox root1 = (VBox) fxmlLoader2.load();
		    Stage stage2 = new Stage();
		    Scene scene2 = new Scene(root1);
		    stage2.setScene(scene2);
		    stage2.initModality(Modality.APPLICATION_MODAL);
		    stage2.initStyle(StageStyle.UNDECORATED);
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
		
		//R�cup�ration de l'id du container cliqu� : 
		Label test = (Label)event.getSource();
		test.setStyle(null);
	}
	
	@FXML
	private void entered(MouseEvent event) {
		
		//R�cup�ration de l'id du container cliqu� : 
		Label test = (Label)event.getSource();
		test.setStyle("-fx-background-color:#ffc200;");
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getIP() {
		return ip.getText();
	}
	
	
	

     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     

     
}
