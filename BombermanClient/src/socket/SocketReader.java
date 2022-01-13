package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import gameobject.attribute.GameObject;

public class SocketReader implements Runnable{
	
	private GameClient client;
	private MessageReceived messageReceivedMap;
	private MessageReceived messageReceivedId;
	private List<GameObject> gameObjectList;
	private GameObject enemy1;
	private GameObject enemy2;
	private GameObject enemy3;
	
	public SocketReader(GameClient client, List<GameObject> gameObjectList, MessageReceived messageReceivedMap, MessageReceived messageReceivedId, GameObject enemy1, GameObject enemy2, GameObject enemy3) {
		this.client = client;
		this.messageReceivedMap = messageReceivedMap;
		this.messageReceivedId = messageReceivedId;
		this.gameObjectList = gameObjectList;
		this.enemy1 = enemy1;
		this.enemy2 = enemy2;
		this.enemy3 = enemy3;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true) {
				dis = new DataInputStream(client.getInputStream());
				String received = dis.readUTF();
				if(received != null) {
					//System.out.println(received);
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
		message = message.substring(message.indexOf(":") + 1);
		double [] parsedMessage = Arrays.stream(message.split(":")).mapToDouble(Double::parseDouble).toArray();
		double id = parsedMessage[0];
		double x = parsedMessage[1];
		double y = parsedMessage[2];
		
		if(id == 0.0) {
			this.enemy1.setPosX(x);
			this.enemy1.setPosY(y);
		}
		
		if(id == 1.0) {
			this.enemy2.setPosX(x);
			this.enemy2.setPosY(y);
		}
		
		if(id == 2.0) {
			this.enemy3.setPosX(x);
			this.enemy3.setPosY(y);
		}
		//System.out.println("id->"+id+" x->"+x+" y->"+y);
		
	}
}
