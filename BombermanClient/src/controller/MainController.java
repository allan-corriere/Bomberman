package controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import gameobject.Bomb;
import gameobject.Player;
import gameobject.Wall;

public class MainController {

	@FXML 
	private ImageView playerLayout;

	@FXML 
	private ImageView bombLayout;
	
	
	@FXML 
	private Pane RBox;
	
	//D�claration des objets de base 
	
	Player player = new Player();
	Wall wall1 = new Wall();
	Wall wall2 = new Wall(50.0,100.0);
	Bomb bomb = new Bomb();
	
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
    	
    	//placer les objets fx
    	RBox.getChildren().add(wall1.fxLayer);
    	RBox.getChildren().add(wall2.fxLayer);
    	bomb.fxLayer = bombLayout;
    	bomb.fxLayer.setVisible(false);
    }
    
  //Gestion des saisies clavier pour d�placements personnage
    
	@FXML
	private void moving(KeyEvent event) {			
		player.move(event.getCode());
		player.placeBomb(bomb, event.getCode());
	}

	@FXML
	private void placeBombImage() {
		
	}
     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     

     

}