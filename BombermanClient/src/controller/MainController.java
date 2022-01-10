package controller;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import gameobject.*;
import gameobject.attribute.GameObject;
import gameobject.bonus.BombNumberBonus;
import gameobject.bonus.BombPowerBonus;
import gameobject.bonus.Bonus;
import gameobject.bonus.PlayerSpeedBonus;
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
    	
    	//traitement des données level envoyés par le serveur
    	String receivedMessage = "map(15,15):1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,2,2,2,3,2,2,2,2,2,0,0,1,1,0,1,2,1,2,1,2,1,2,1,4,1,0,1,1,2,2,5,2,2,2,2,3,2,2,2,2,2,1,1,2,1,2,1,2,1,2,1,4,1,2,1,4,1,1,2,2,2,2,3,2,2,2,2,2,2,5,3,1,1,3,1,2,1,2,1,3,1,2,1,2,1,2,1,1,3,2,5,4,2,4,2,5,5,2,4,2,2,1,1,2,1,2,1,2,1,2,1,2,1,2,1,5,1,1,2,2,3,2,2,2,2,4,2,2,2,5,2,1,1,5,1,2,1,2,1,2,1,2,1,2,1,2,1,1,2,2,4,3,2,2,2,2,2,3,2,4,2,1,1,0,1,2,1,5,1,2,1,2,1,4,1,0,1,1,0,0,2,3,2,2,2,2,2,2,4,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,";
    	if(receivedMessage.startsWith("map(")){
    		String mapSizeText = receivedMessage.substring(receivedMessage.indexOf("(") + 1, receivedMessage.indexOf(")"));
    		int [] mapSize = Arrays.stream(mapSizeText.split(",")).mapToInt(Integer::parseInt).toArray();
    		String dataText = receivedMessage.split(":")[1];
    		int [] level = Arrays.stream(dataText.substring(0, dataText.length() - 1).split(",")).mapToInt(Integer::parseInt).toArray();
    		System.out.println(level.length);
    		int totalRow = mapSize[0];
    		int totalColumn = mapSize[1];
    		int currentRow = 0;
    		int currentColumn = 0;
    		
    		for(int i = 0; i < totalRow*totalColumn; i++) {
    			if(level[i] == 1) {
    				gameObjectList.add(new Wall(currentRow*50.0,currentColumn*50));
    			}
    			else if(level[i] == 2 || level[i] == 3 || level[i] == 4 || level[i] == 5) {
    				Bonus brickBonus = null;
    			
    			    if(level[i] == 3) {
    			    	brickBonus = new PlayerSpeedBonus(currentRow*50.0,currentColumn*50);
    			    }
    			    else if(level[i] == 4) {
    			    	brickBonus = new BombPowerBonus(currentRow*50.0,currentColumn*50);
    			    }
    			    else if(level[i] == 5) {
    			    	brickBonus = new BombNumberBonus(currentRow*50.0,currentColumn*50);
    			    }
    				gameObjectList.add(new Brick(gameTimer,currentRow*50.0,currentColumn*50,brickBonus));
    				
    				
    			}
    			System.out.println(currentRow);
    			System.out.println(currentColumn);
    			currentRow ++;
    			if(currentRow >= totalRow) {
    				currentRow = 0;
    				currentColumn ++;
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
	private void KeyPressed(KeyEvent event) {
		System.out.println("pressed"+event.getCode());
		player.move(event.getCode(), RBox, gameObjectList);
		
	}
	
	@FXML
	private void KeyReleased(KeyEvent event) {
		System.out.println("relaché"+event.getCode());
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