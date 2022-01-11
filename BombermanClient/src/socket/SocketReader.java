package socket;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReader implements Runnable{
	
	private GameClient client;
	private MessageReceived messageReceivedMap;
	private MessageReceived messageReceivedId;
	
	public SocketReader(GameClient client, MessageReceived messageReceivedMap, MessageReceived messageReceivedId) {
		this.client = client;
		this.messageReceivedMap = messageReceivedMap;
		this.messageReceivedId = messageReceivedId;
	}
	
	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true) {
				dis = new DataInputStream(client.getInputStream());
				String received = dis.readUTF();
				if(received != null) {
					//cas où c'est la map
					if(received.startsWith("map(")) {
		    			this.messageReceivedMap.setMessage(received);
		    		}
					// cas où c'est l'id
					if(received.startsWith("id:")) {
		    			this.messageReceivedId.setMessage(received);
		    		}
				}
				//System.out.println(messageReceived.getMessage());
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

	
	

}
