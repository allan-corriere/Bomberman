package controller;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import gameobject.*;
import gameobject.attribute.GameObject;
import gamescene.Level;



public class MainController {

	@FXML 
	private Pane RBox;
	
	//Déclaration des objets de base 
	public List<GameObject> gameObjectList = new ArrayList<GameObject>();
	
	public Player player = new Player();
	public Level masterLevel = new Level();
	int[][] level = masterLevel.loadLevel01(); 
    // Add a public no-args constructor
    public MainController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	//parcours du level
    	
    	//ajout des gameobject à la liste
    	player.setPosX(50.0);
    	player.setPosY(50.0);
    	gameObjectList.add(player);
    	//gameObjectList.add(wall1);
    	int column = level.length;
    	for(int y =0; y < column; y++) {
    		for(int x=0; x < level[y].length; x++) {
    			if(level[y][x] == 1) {
    				gameObjectList.add(new Wall(x*50.0,y*50));
    			}
    			if(level[y][x] == 2) {
    				gameObjectList.add(new Brick(x*50.0,y*50));
    			}
    		}
    	}
    	
    	player.setSpeed(5);
    	//placer les objets fx
    	for (GameObject object : gameObjectList) {
    		RBox.getChildren().add(object.fxLayer);
    	}
    	//RBox.getChildren().add(wall1.fxLayer);
    

    }
    
  //Gestion des saisies clavier pour d�placements personnage
    
	@FXML
	private void moving(KeyEvent event) {			
		player.move(event.getCode(), gameObjectList);
		player.placeBomb(event.getCode(),RBox,gameObjectList);
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