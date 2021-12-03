package controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    	//Liaison du joueur avec son visuel 
    	player.fxLayer = playerLayout;
    	player.setSpeed(5);
    	wall1.fxLayer = wall1Layout;
    	wall1.setPosX(wall1.fxLayer.getLayoutX());
    	wall1.setPosY(wall1.fxLayer.getLayoutY());
    }
    
  //Gestion des saisies clavier pour déplacements personnage
    
	@FXML
	private void moving(KeyEvent event) {
				
		if (event.getCode()==KeyCode.UP)
		{
			if (player.getPosY()>0)
			{
				//if(player.getPosX()+player.fxLayer.getFitWidth()<wall1.getPosX() && player.getPosX()>wall1.getPosX()+wall1.fxLayer.getFitWidth())
				{
					//if(player.getPosY()>wall1.getPosY()+wall1.fxLayer.getFitHeight())
						player.setPosY(player.getPosY()-player.getSpeed());

						
				}
			}
		}
		
		if (event.getCode()==KeyCode.DOWN)
		{
			if (RBox.getPrefHeight()-player.getPosY()>player.fxLayer.getFitHeight())
			{
				player.setPosY(player.getPosY()+player.getSpeed());
			}
		}
		
		if (event.getCode()==KeyCode.LEFT)
		{
			if (player.getPosX()>0)
				player.setPosX((player.getPosX()-player.getSpeed()));
		}
		
		if (event.getCode()==KeyCode.RIGHT)
		{
			if (RBox.getPrefWidth()-player.getPosX()>player.fxLayer.getFitWidth())
			{
				player.setPosX(player.getPosX()+player.getSpeed());
			}
		}
		
		if (event.getCode()==KeyCode.SPACE)
		{
			System.out.println("space");
			System.out.println(player.fxLayer.getLayoutX());
		}
	}


     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     

     

}