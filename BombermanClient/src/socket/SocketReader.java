package socket;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReader implements Runnable{
	
	private GameClient client;
	private MessageReceived messageReceived;
	
	public SocketReader(GameClient client,MessageReceived messageReceived) {
		this.client = client;
		this.messageReceived = messageReceived;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true) {
				dis = new DataInputStream(client.getInputStream());
				messageReceived.setMessage(dis.readUTF());
				System.out.println(messageReceived.getMessage());
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
