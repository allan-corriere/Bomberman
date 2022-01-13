package controller;
import java.io.File;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import socket.GameClient;
import socket.MessageReceived;
import socket.SocketReader;
import socket.SocketWriter;
import gameobject.*;
import gameobject.attribute.GameObject;
import gameobject.bonus.BombNumberBonus;
import gameobject.bonus.BombPowerBonus;
import gameobject.bonus.Bonus;
import gameobject.bonus.PlayerSpeedBonus;
import gamescene.Level;



public class MainController {
	
	private SocketWriter sw;
	private MessageReceived messageReceivedMap = new MessageReceived();
	private MessageReceived messageReceivedId = new MessageReceived();

	@FXML 
	private Pane RBox;
	
	public Timer gameTimer = new Timer();
	//Déclaration des objets de base 
	public List<GameObject> gameObjectList = new ArrayList<GameObject>();
	public Player player;// = new Player(gameTimer, sw);
	public Level masterLevel = new Level();
	int[][] level = masterLevel.loadLevel02(); 
    // Add a public no-args constructor
    public MainController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	System.out.println("yess");
    	
    	GameObject enemy1 = new GameObject(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));
    	GameObject enemy2 = new GameObject(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));
    	GameObject enemy3 = new GameObject(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));
    	
    	// Connexion au serveur
    	try {
			GameClient client = new GameClient("localhost", 65432, "Osloh");
			
			new Thread(new SocketReader(client, gameObjectList, messageReceivedMap, messageReceivedId, enemy1, enemy2, enemy3, gameTimer, RBox)).start();
			
			this.sw = new SocketWriter(client);
			new Thread(this.sw).start();
			
			//this.sw = new TestSW(client);
			//this.sw.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	this.player = new Player(gameTimer, sw);
    
    	//parcours du level
    	//ajout des gameobject à la liste
//    	player.setPosX(50.0);
//    	player.setPosY(50.0);
    	gameObjectList.add(player);
    	//gameObjectList.add(wall1);
    	int totalRow = 0;
    	int totalColumn = 0;
    	while(messageReceivedMap.getMessage() == "") {
    		continue;
    	}
    	while(messageReceivedId.getMessage() == "") {
    		continue;
    	}
    	//traitement des données level envoyés par le serveur
    		String mapSizeText = messageReceivedMap.getMessage().substring(messageReceivedMap.getMessage().indexOf("(") + 1, messageReceivedMap.getMessage().indexOf(")"));
    		int [] mapSize = Arrays.stream(mapSizeText.split(",")).mapToInt(Integer::parseInt).toArray();
    		String dataText = messageReceivedMap.getMessage().split(":")[1];
    		int [] level = Arrays.stream(dataText.substring(0, dataText.length() - 1).split(",")).mapToInt(Integer::parseInt).toArray();
    		totalRow = mapSize[0];
    		totalColumn = mapSize[1];
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
    			currentRow ++;
    			if(currentRow >= totalRow) {
    				currentRow = 0;
    				currentColumn ++;
    			}
    			
    		}
    	
    	//traitement de l'id
    		int id = Integer.parseInt(messageReceivedId.getMessage().split(":")[1]);
    		System.out.println("Test lol "+id);
    		if(id==0) {
    			player.setPosX(50);
    			player.setPosY(50);
    		} else if(id==1) {
    			player.setPosX((totalColumn*50)-100);
    			player.setPosY(50);
    			enemy1.setPosX(50);
    			enemy1.setPosY(50);
    		} else if(id==2) {
    			player.setPosX(50);
    			player.setPosY((totalRow*50)-100);
    			enemy1.setPosX(50);
    			enemy1.setPosY(50);
    			enemy2.setPosX((totalColumn*50)-100);
    			enemy2.setPosY(50);

    		} else if(id==3) {
    			player.setPosX((totalColumn*50)-100);
    			player.setPosY((totalRow*50)-100);
    			enemy1.setPosX(50);
    			enemy1.setPosY(50);
    			enemy2.setPosX((totalColumn*50)-100);
    			enemy2.setPosY(50);
    			enemy3.setPosX(50);
    			enemy3.setPosY((totalRow*50)-100);

    		}
    		
    	gameObjectList.add(enemy1);
    	gameObjectList.add(enemy2);
    	gameObjectList.add(enemy3);
    	
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
		player.resetLayer(event.getCode());
		
	}
	
	@FXML
	private void KeyReleased(KeyEvent event) {
		System.out.println("relaché"+event.getCode());
		player.placeBomb(event.getCode(),RBox,gameObjectList);
		player.resetLayer(event.getCode());		
	}

	@FXML
	private void explosion() {
		//Animation de l'explosion : 
		Image explosionImage1 = new Image(new File("ressources/Bombes/fire1.png").toURI().toString());

		ImageView Explode = new ImageView(explosionImage1);
		GameObject randomObject = gameObjectList.get(0);
		Pane root = (Pane) randomObject.fxLayer.getParent();
		root.getChildren().add(Explode);

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