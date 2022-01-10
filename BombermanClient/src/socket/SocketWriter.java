package socket;

import java.io.DataOutputStream;

public class SocketWriter implements Runnable{
	
	private GameClient client;
	
	public SocketWriter(GameClient client) {
		this.client = client;
	}

	public void send(String string) {
		try {
			DataOutputStream dos = new DataOutputStream(this.client.getOutputStream());
			dos.writeUTF(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("SocketWriter is running");
	}

}
