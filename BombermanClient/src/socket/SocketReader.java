package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gameobject.Bomb;
import gameobject.Enemy;
import gameobject.attribute.GameObject;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SocketReader implements Runnable{
	
	private Socket client;
	private SocketWriter sw;
	private MessageReceived messageReceivedMap;
	private MessageReceived messageReceivedId;
	private MessageReceived messageReceivedPlayStatus;
	private List<GameObject> gameObjectList;
	private Enemy [] enemys = new Enemy[3];
	private Timer gameTimer;
	private Pane RBox;
	//specific aux methodes
	private Text mainMessage;
	private List<String> enemysUsername = new ArrayList<String>();
	private GameObject lastEnemy;
	private int enemyCount;

	public SocketReader(Socket client, SocketWriter sw, List<GameObject> gameObjectList, MessageReceived messageReceivedMap, MessageReceived messageReceivedId, MessageReceived messageReceivedPlayStatus, Enemy [] enemys, Timer gameTimer, Pane RBox, Text mainMessage) {
		this.client = client;
		this.sw = sw;
		this.messageReceivedMap = messageReceivedMap;
		this.messageReceivedId = messageReceivedId;
		this.messageReceivedPlayStatus = messageReceivedPlayStatus;
		this.gameObjectList = gameObjectList;
		this.enemys = enemys;
		this.gameTimer = gameTimer;
		this.RBox = RBox;
		this.mainMessage = mainMessage;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true) {
				dis = new DataInputStream(client.getInputStream());
				String received = dis.readUTF();
				if(received != null) {
					System.out.println(received);
					//cas où c'est la map
					if(received.startsWith("map(")) {
		    			this.messageReceivedMap.setMessage(received);
		    		}
					// cas où c'est l'id
					else if(received.startsWith("id:")) {
		    			this.messageReceivedId.setMessage(received);
		    		}
					// cas où c'est un mouvement
					else if(received.startsWith("move:")) {
						this.moveEnemies(received);
					}
					//bombe a placer
					else if(received.startsWith("bomb:")) {
						this.placeBomb(received);
					}
					//bonus a enlever (recuperer par un enemy)
					else if(received.startsWith("bonus:")) {
						this.deleteBonus(received);
					}
					//pseudo des autres joueurs
					else if(received.startsWith("playerinfo:")) {
						this.playerInfo(received);
					}
					//condition départ du jeu
					else if(received.startsWith("gamestart:")) {
						this.startGame(received);
					}
					//fin de partie
					else if(received.startsWith("gameover:")) {
						this.endGame(received);
					}
					//joueur déconnecté du serveur
					else if(received.startsWith("dead:")) {
						this.death(received);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Connection close by server");
		} finally {
			try {
				if(dis != null) {
					dis.close();
				}
				if(client != null) {
					client = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void moveEnemies(String message) {
		int idPlayer = Integer.parseInt(messageReceivedId.getMessage().split(":")[1]);
		message = message.substring(message.indexOf(":") + 1);
		double [] parsedMessage = Arrays.stream(message.split(":")).mapToDouble(Double::parseDouble).toArray();
		int id = (int)parsedMessage[0];
		double x = parsedMessage[1];
		double y = parsedMessage[2];
		if(idPlayer == 0) {
			if(id == 1) {
				enemys[0].setPosition(x,y);
			}else if(id == 2) {
				enemys[1].setPosition(x,y);
			}else if(id == 3) {
				enemys[2].setPosition(x,y);
			}
		}else if(idPlayer == 1) {
			if(id == 0) {
				 enemys[0].setPosition(x,y);
			}else if(id == 2) {
				enemys[1].setPosition(x,y);
			}else if(id == 3) {
				enemys[2].setPosition(x,y);
			}
		}else if(idPlayer == 2) {
			if(id == 0) {
				 enemys[0].setPosition(x,y);
			}else if(id == 1) {
				enemys[1].setPosition(x,y);
			}else if(id == 3) {
				enemys[2].setPosition(x,y);
			}
		}else if(idPlayer == 3) {
			if(id == 0) {
				 enemys[0].setPosition(x,y);
			}else if(id == 1) {
				enemys[1].setPosition(x,y);
			}else if(id == 2) {
				enemys[2].setPosition(x,y);
			}
		}
	}
	
	public void placeBomb(String message) {
		//Parsing
		message = message.substring(message.indexOf(":") + 1);
		double [] parsedMessage = Arrays.stream(message.split(":")).mapToDouble(Double::parseDouble).toArray();
		double x = parsedMessage[1];
		double y = parsedMessage[2];
		int bombRadius = (int)parsedMessage[3];
		
		//Pose de la bombe
		Bomb bomb = new Bomb(this.gameTimer, bombRadius, mainMessage, sw);
		
		bomb.setPosX(x);
		bomb.setPosY(y);
		
		bomb.fxLayer.toFront();

		gameObjectList.add(bomb);
		bomb.fxLayer.setVisible(false);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				RBox.getChildren().add(bomb.fxLayer);
			}
		});
		bomb.startBomb(gameObjectList);
	}
	
	public void deleteBonus(String message) {
		message = message.substring(message.indexOf(":") + 1);
		double [] parsedMessage = Arrays.stream(message.split(":")).mapToDouble(Double::parseDouble).toArray();
		double x = parsedMessage[0];
		double y = parsedMessage[1];
			
		GameObject found = null;
		
		for(GameObject object : this.gameObjectList) {
			if(object.getPosX() == x && object.getPosY() == y) {
				found = object;
				break;
			}
		}
		if(found != null) {
			found.fxLayer.setVisible(false);
			this.gameObjectList.remove(found);
		}
	}
	
	public void playerInfo(String message) {
		enemysUsername.add(message.split(":")[1]);

		if(enemysUsername.size() == 4) {
			for (Enemy object : enemys) {
				for(int i= 1; i < 5; i++) { //set usernames enemys
					if(object.getPlayerNumber() == i) {
						object.setUserName(enemysUsername.get(i-1));
					}
				}
			}
		}
	}
	
	public void startGame(String message) {
		int num = Integer.parseInt(message.split(":")[1]);
		if(num != 0) {
			mainMessage.setText("La partie commence dans\n"+num);
		}else {
			mainMessage.setText("C'est parti !!!");
			this.messageReceivedPlayStatus.setMessage("start"); // les joueurs peuvent bouger
			Timer t = new Timer();
			TimerTask tgo = new TimerTask() {
	    		
				@Override
				public void run() {
					mainMessage.setVisible(false);
				}
	    	};
	    	t.schedule(tgo, 1000);
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() { //player mort
				mainMessage.setTextAlignment(TextAlignment.CENTER);
		        mainMessage.layoutXProperty().bind(RBox.widthProperty().subtract(mainMessage.prefWidth(-1)).divide(2));
		        mainMessage.layoutYProperty().bind(RBox.heightProperty().subtract(mainMessage.prefHeight(-1)).divide(2));
			}
		});
	}
	
	public void endGame(String message) {
		String result = message.split(":")[1];
		if(result.equals("win")) {
			mainMessage.setText(mainMessage.getText()+"\n appuyez sur entrée pour revenir au menu");
		}else if(result.equals("draw")) {
			mainMessage.setText("Match nul !\n appuyez sur entrée pour revenir au menu");
		}
		this.messageReceivedPlayStatus.setMessage("end"); // les joueurs ne peuvent plus bouger
		
		try {
		        client.close();
		        System.out.println("Connection with server ended");
		    } catch (IOException e) { /* failed */ }
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() { //player mort
				mainMessage.setTextAlignment(TextAlignment.CENTER);
				mainMessage.layoutXProperty().bind(RBox.widthProperty().subtract(mainMessage.prefWidth(-1)).divide(2));
				mainMessage.layoutYProperty().bind(RBox.heightProperty().subtract(mainMessage.prefHeight(-1)).divide(2));
			}
		});
	}
	
	public void death(String message) { //enleve les enemis déconnectés
		int result = (Integer.parseInt(message.split(":")[1])+1);
		GameObject toRemove =null;
		enemyCount = 0;
		for(GameObject object : this.gameObjectList) {
			if(object instanceof Enemy) {
				enemyCount += 1;
				if(((Enemy) object).getPlayerNumber() == result) {
					toRemove = object;	
				}
				
			}
		}
		if(toRemove != null) {
			toRemove.fxLayer.setVisible(false);
			gameObjectList.remove(toRemove);
		}else { //si l'enemi est déconnecté après sa mort
			enemyCount +=1;
		}
		//main message
		if(mainMessage.isVisible()) { //si message affiché = joueur mort
			if(enemyCount-1 == 1) {
				for(GameObject listenemy : gameObjectList) {
					if(listenemy instanceof Enemy) {
						lastEnemy = listenemy;
					}
				}
				Platform.runLater(new Runnable() { //win enemy
					@Override
					public void run() { //player dead
						mainMessage.setVisible(true);
						mainMessage.setText("Vous êtes mort !\n Le joueur n°"+((Enemy)lastEnemy).getPlayerNumber()+" "+((Enemy)lastEnemy).getUserName()+" remporte la partie");
					}
				});
			}else if(enemyCount-1 != 0) { // -1 enemy
				Platform.runLater(new Runnable() {
					@Override
					public void run() { //player mort
						mainMessage.setVisible(true);
						mainMessage.setText("Vous êtes mort !\n"+"Il reste "+(enemyCount-1)+" joueurs en vie");
					}
				});
			}
		}else { //player toujours en vie
			if(enemyCount-1 == 0) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						mainMessage.setVisible(true);
						mainMessage.setText("Vous avez gagné !!!");
					}
				});
			}
		}
		
	}
	
}
