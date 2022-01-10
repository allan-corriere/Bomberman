package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketReader implements Runnable{
	
	private Socket client;
	private int identifier;
	private SocketWriter sw;
	
	public SocketReader(Socket client, int id, SocketWriter sw) {
		this.client = client;
		this.identifier = id;
		this.sw = sw;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true) {
				dis = new DataInputStream(client.getInputStream());
				String receive = dis.readUTF();
				if(receive != null) {
					//System.out.println(this.identifier+":"+receive);
					this.sw.send(this.identifier+":"+receive, this.identifier);
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

}
