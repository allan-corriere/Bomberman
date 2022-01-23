package controller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private MessageReceived messageReceivedPlayStatus = new MessageReceived();
	private String UserName;

	@FXML 
	private Pane RBox;
	
	
	public Timer gameTimer = new Timer();
	//Déclaration des objets de base 
	public List<GameObject> gameObjectList = new ArrayList<GameObject>();
	public Player player;// = new Player(gameTimer, sw);
	public Enemy [] enemys = new Enemy[3];
	public Level masterLevel = new Level();
	public Text mainMessage = new Text("");

	public int[][] level = masterLevel.loadLevel02(); 
	private int totalRow = 0;
	private int totalColumn = 0;
	private String userName;
	
	private Stage menuDisplay ;
	private MenuController menuController;
	
    public MainController(MenuController menuController, Stage menu,String Username) {
		this.userName=Username;
		this.menuDisplay = menu;
		this.menuController=menuController;
    }
     
    @FXML
    private void initialize() {
    	
    	
		//creation des ennemis
    	enemys[0] = new Enemy(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));
    	enemys[1] = new Enemy(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));
    	enemys[2] = new Enemy(1000, 1000,new ImageView(new Image(new File("ressources/Hero/face0.png").toURI().toString())));


    	// Connexion au serveur
    	try {
			GameClient client = new GameClient("localhost", 65432, "Osloh");    	
	    	//lancement de la connexion
			this.sw = new SocketWriter(client);
			new Thread(this.sw).start();
			
			new Thread(new SocketReader(client, sw, gameObjectList, messageReceivedMap, messageReceivedId, messageReceivedPlayStatus, enemys, gameTimer, RBox, mainMessage)).start();
			
			
			
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
    	this.sw.send("pseudo:"+userName); // envoi du pseudo du joueur au serveur

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
    	mainMessage.setText("En attente de la connexion de tout les joueurs");
        mainMessage.setTextOrigin(VPos.TOP);
        mainMessage.setFont(Font.font(null, FontWeight.BOLD, 25));
        RBox.getChildren().add(mainMessage);mainMessage.setTextAlignment(TextAlignment.CENTER);
        mainMessage.layoutXProperty().bind(RBox.widthProperty().subtract(mainMessage.prefWidth(-1)).divide(2));
        mainMessage.layoutYProperty().bind(RBox.heightProperty().subtract(mainMessage.prefHeight(-1)).divide(2));


    }
    
  //Gestion des saisies clavier pour d�placements personnage
    
	@FXML
	private void KeyPressed(KeyEvent event) {
		//System.out.println("pressed"+event.getCode());
		System.out.println(messageReceivedPlayStatus.getMessage());
		if(player.isAlive() && messageReceivedPlayStatus.getMessage() == "start") {
			player.move(event.getCode(), RBox, gameObjectList);
			player.placeBomb(event.getCode(),RBox,gameObjectList, mainMessage);
		}
		
		else if (event.getCode().equals(KeyCode.ENTER) && messageReceivedPlayStatus.getMessage() == "end"){
			Stage stage =  (Stage) RBox.getScene().getWindow();
			stage.close();
			menuController.setFieldDisable();
			menuDisplay.showAndWait();
		}
	}
	@FXML
	private void KeyReleased(KeyEvent event) {
		//System.out.println("relaché"+event.getCode());
		if(player.isAlive() && messageReceivedPlayStatus.getMessage() == "start") {
			player.resetLayer(event.getCode());	
		}	
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
			enemys[0].setPlayerNumber(2);
			enemys[1].setPosition(50, (totalRow*50)-100);
			enemys[1].setPlayerNumber(3);
			enemys[2].setPosition((totalColumn*50)-100, (totalRow*50)-100);
			enemys[2].setPlayerNumber(4);
		} else if(id==1) {
			enemys[0].setPosition(50, 50);
			enemys[0].setPlayerNumber(1);
			player.setPosition((totalColumn*50)-100, 50);
			enemys[1].setPosition(50, (totalRow*50)-100);
			enemys[1].setPlayerNumber(3);
			enemys[2].setPosition((totalColumn*50)-100, (totalRow*50)-100);
			enemys[2].setPlayerNumber(4);
		} else if(id==2) {
			enemys[0].setPosition(50, 50);
			enemys[0].setPlayerNumber(1);
			enemys[1].setPosition((totalColumn*50)-100, 50);
			enemys[1].setPlayerNumber(2);
			player.setPosition(50, (totalRow*50)-100);
			enemys[2].setPosition((totalColumn*50)-100, (totalRow*50)-100);
			enemys[2].setPlayerNumber(4);
		} else if(id==3) {
			enemys[0].setPosition(50, 50);
			enemys[0].setPlayerNumber(1);
			enemys[1].setPosition((totalColumn*50)-100, 50);
			enemys[1].setPlayerNumber(2);
			enemys[2].setPosition(50, (totalRow*50)-100);
			enemys[2].setPlayerNumber(3);
			player.setPosition((totalColumn*50)-100, (totalRow*50)-100);
		}
		for (Enemy object : enemys) {
	    	gameObjectList.add(object); //add enemys to the gamelist
		}
    }
    
    public void setUserName(String Username) {
    	this.UserName = Username;
    	System.out.println("appel fonction "+Username);
    }
     

}