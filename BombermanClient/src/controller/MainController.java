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
import gameobject.actions.ThreadMoving;
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
	public GameObject [] enemys = new GameObject[3];
	public Level masterLevel = new Level();
	public int[][] level = masterLevel.loadLevel02(); 
	private int totalRow = 0;
	private int totalColumn = 0;
    // Add a public no-args constructor
    public MainController() {
    }
     
    @FXML
    private void initialize() {
    	
    	
		//creation des ennemis
    	enemys[0] = new GameObject(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));
    	enemys[1] = new GameObject(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));
    	enemys[2] = new GameObject(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));


    	// Connexion au serveur
    	try {
			GameClient client = new GameClient("localhost", 65432, "Osloh");    	
	    	//lancement de la connexion
			new Thread(new SocketReader(client, gameObjectList, messageReceivedMap, messageReceivedId, enemys, gameTimer, RBox)).start();
			
			this.sw = new SocketWriter(client);
			new Thread(this.sw).start();
			
			//this.sw = new TestSW(client);
			//this.sw.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	    	
    	//traitement des données level envoyées par le serveur

    	while(messageReceivedMap.getMessage() == "") {
    		continue;
    	}
    	while(messageReceivedId.getMessage() == "") {
    		continue;
    	}
    	createMap();
    	setPlayers();

    	

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
    	
    	//moving event
//    	try {
//    		new Thread(new ThreadMoving(player, RBox, gameObjectList)).start();
//    	}catch (Exception e) {
//			e.printStackTrace();
//		}
    }
    
  //Gestion des saisies clavier pour d�placements personnage
    
	@FXML
	private void KeyPressed(KeyEvent event) {
		player.movePress(event.getCode(), RBox, gameObjectList);
		player.resetLayer(event.getCode());
		
	}
	
	@FXML
	private void KeyReleased(KeyEvent event) {
		player.movingRelease(event.getCode());
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

    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     
    private void createMap() {
		String mapSizeText = messageReceivedMap.getMessage().substring(messageReceivedMap.getMessage().indexOf("(") + 1, messageReceivedMap.getMessage().indexOf(")"));
		int [] mapSize = Arrays.stream(mapSizeText.split(",")).mapToInt(Integer::parseInt).toArray();
		String dataText = messageReceivedMap.getMessage().split(":")[1];
		int [] level = Arrays.stream(dataText.substring(0, dataText.length() - 1).split(",")).mapToInt(Integer::parseInt).toArray();
		totalRow = mapSize[0];
		totalColumn = mapSize[1];
		int currentRow = 0;
		int currentColumn = 0;
		
		//lecture des informations de la map et création
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
    }
    private void setPlayers() {
    	//traitement des joueurs
    	this.player = new Player(gameTimer, sw);
    	gameObjectList.add(player);
		int id = Integer.parseInt(messageReceivedId.getMessage().split(":")[1]);
		System.out.println("Test lol "+id);
		if(id==0) {
			player.setPosition(50, 50);
			enemys[0].setPosition((totalColumn*50)-100, 50);
			enemys[1].setPosition(50, (totalRow*50)-100);
			enemys[2].setPosition((totalColumn*50)-100, (totalRow*50)-100);
		} else if(id==1) {
			enemys[0].setPosition(50, 50);
			player.setPosition((totalColumn*50)-100, 50);
			enemys[1].setPosition(50, (totalRow*50)-100);
			enemys[2].setPosition((totalColumn*50)-100, (totalRow*50)-100);
		} else if(id==2) {
			enemys[0].setPosition(50, 50);
			enemys[1].setPosition((totalColumn*50)-100, 50);
			player.setPosition(50, (totalRow*50)-100);
			enemys[2].setPosition((totalColumn*50)-100, (totalRow*50)-100);
		} else if(id==3) {
			enemys[0].setPosition(50, 50);
			enemys[1].setPosition((totalColumn*50)-100, 50);
			enemys[2].setPosition(50, (totalRow*50)-100);
			player.setPosition((totalColumn*50)-100, (totalRow*50)-100);
		}
		
		for (GameObject object : enemys) {	
	    	gameObjectList.add(object);
		}
    }
     

}