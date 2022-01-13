package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import gameobject.Bomb;
import gameobject.attribute.GameObject;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class SocketReader implements Runnable{
	
	private GameClient client;
	private MessageReceived messageReceivedMap;
	private MessageReceived messageReceivedId;
	private List<GameObject> gameObjectList;
	private GameObject [] enemys = new GameObject[3];
	private Timer gameTimer;
	private Pane RBox;
	
	public SocketReader(GameClient client, List<GameObject> gameObjectList, MessageReceived messageReceivedMap, MessageReceived messageReceivedId, GameObject [] enemys, Timer gameTimer, Pane RBox) {
		this.client = client;
		this.messageReceivedMap = messageReceivedMap;
		this.messageReceivedId = messageReceivedId;
		this.gameObjectList = gameObjectList;
		this.enemys = enemys;
		this.gameTimer = gameTimer;
		this.RBox = RBox;
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
					if(received.startsWith("id:")) {
		    			this.messageReceivedId.setMessage(received);
		    		}
					// cas où c'est un mouvement
					if(received.startsWith("move:")) {
						this.moveEnemies(received);
					}
					if(received.startsWith("bomb:")) {
						this.placeBomb(received);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
		//System.out.println("id->"+id+" x->"+x+" y->"+y);
		
	}
	
	public void placeBomb(String message) {
		//Parsing
		message = message.substring(message.indexOf(":") + 1);
		double [] parsedMessage = Arrays.stream(message.split(":")).mapToDouble(Double::parseDouble).toArray();
		int id = (int)parsedMessage[0];
		double x = parsedMessage[1];
		double y = parsedMessage[2];
		int bombRadius = (int)parsedMessage[3];
		
		//Pose de la bombe
		Bomb bomb = new Bomb(this.gameTimer, bombRadius);
		
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
		//RBox.getChildren().add(bomb.fxLayer);
		bomb.startBomb(gameObjectList);
	}
}
