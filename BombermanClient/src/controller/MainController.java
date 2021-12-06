package controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import gameobject.Player;
import gameobject.Wall;

public class MainController {

	@FXML 
	private ImageView playerLayout;
	@FXML
	private ImageView wall1Layout;
	
	@FXML 
	private Pane RBox;
	
	//Déclaration des objets de base 
	
	Player player = new Player();
	Wall wall1 = new Wall();
	
    // Add a public no-args constructor
    public MainController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	//Liaison des objets avec leurs visuel 
    	player.fxLayer = playerLayout;
    	player.setSpeed(5);
    	wall1.fxLayer = wall1Layout;
    	wall1.setPosX(wall1.fxLayer.getLayoutX());
    	wall1.setPosY(wall1.fxLayer.getLayoutY());
    }
    
  //Gestion des saisies clavier pour déplacements personnage
    
	@FXML
	private void moving(KeyEvent event) {			
		player.move(event.getCode());
	}

     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     

     

}