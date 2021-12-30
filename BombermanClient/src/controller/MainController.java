package controller;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import gameobject.*;
import gameobject.attribute.GameObject;



public class MainController {

	@FXML 
	private Pane RBox;
	
	//Déclaration des objets de base 
	public List<GameObject> gameObjectList = new ArrayList<GameObject>();
	
	public Player player = new Player();
	Wall wall1 = new Wall();
	Wall wall2 = new Wall(50.0,100.0);
	Wall wall3 = new Wall(150.0,0.0);
	Wall wall4 = new Wall(150.0,0.0);
	Wall wall5 = new Wall(150.0,50.0);
	Wall wall6 = new Wall(150.0,150.0);
	Wall wall7 = new Wall(100.0,150.0);
	Wall wall8 = new Wall(50.0,150.0);
	Wall wall9 = new Wall(00.0,150.0);
	
    // Add a public no-args constructor
    public MainController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	//ajout des gameopbject à la liste
    	gameObjectList.add(player);
    	gameObjectList.add(wall1);
    	gameObjectList.add(wall2);
    	gameObjectList.add(wall3);
    	gameObjectList.add(wall4);
    	gameObjectList.add(wall5);
    	gameObjectList.add(wall6);
    	gameObjectList.add(wall7);
    	gameObjectList.add(wall8);
    	gameObjectList.add(wall9);
    	
    	player.setSpeed(5);
    	System.out.println(RBox);
    	//placer les objets fx
    	RBox.getChildren().add(player.fxLayer);
    	RBox.getChildren().add(wall1.fxLayer);
    	RBox.getChildren().add(wall2.fxLayer);
    	RBox.getChildren().add(wall3.fxLayer);
    	RBox.getChildren().add(wall4.fxLayer);
    	RBox.getChildren().add(wall5.fxLayer);
    	RBox.getChildren().add(wall6.fxLayer);
    	RBox.getChildren().add(wall7.fxLayer);
    	RBox.getChildren().add(wall8.fxLayer);
    	RBox.getChildren().add(wall9.fxLayer);

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