package controller;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import gameobject.*;
import gameobject.attribute.GameObject;
import gameobject.bonus.Bonus;
import gamescene.Level;



public class MainController {

	@FXML 
	private Pane RBox;
	
	public Timer gameTimer = new Timer();
	//Déclaration des objets de base 
	public List<GameObject> gameObjectList = new ArrayList<GameObject>();
	public Player player = new Player(gameTimer);
	public Level masterLevel = new Level();
	int[][] level = masterLevel.loadLevel02(); 
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
    				gameObjectList.add(new Brick(gameTimer,x*50.0,y*50));
    			}
    		}
    	}
    	
    	player.setSpeed(5);
    	player.setMaxBomb(1);
    	player.setBombRadius(1);
    	player.fxLayer.toFront();
    	//placer les objets fx
    	for (GameObject object : gameObjectList) {
    		RBox.getChildren().add(object.fxLayer);
    	  	//placer les fx des bonus
        	if(object instanceof Brick) { //prompt when  bonus
    			if(((Brick) object).brickBonus != null) {
    				RBox.getChildren().add(((Brick) object).brickBonus.fxLayer);
    			}
        	}
    	}
  
    	TimerTask task = new TimerTask()
    	{

    	    @Override
    	    public void run()
    	    {
    	    	System.out.println(java.time.LocalTime.now());  
    	    
    	    }
    	};
    	//gameTimer.scheduleAtFixedRate(task,0, 10);
    	
    	

    }
    
  //Gestion des saisies clavier pour d�placements personnage
    
	@FXML
	private void moving(KeyEvent event) {
		System.out.println(event.getCode());
		player.move(event.getCode(), RBox, gameObjectList);
		player.placeBomb(event.getCode(),RBox,gameObjectList);
	}

	@FXML
	private void placeBombImage() {
		System.out.println("franc");
	}
     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     

     

}