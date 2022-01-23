package socket;

import java.io.DataOutputStream;
import java.net.Socket;

public class SocketWriter implements Runnable{
	
	private Socket client;
	
	public SocketWriter(Socket client) {
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
