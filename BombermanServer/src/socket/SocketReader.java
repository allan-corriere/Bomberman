package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketReader implements Runnable{
	
	private Socket client;
	private int identifier;
	private SocketWriter sw;
	
	public SocketReader(Socket client, int id, SocketWriter sw, String map) {
		this.client = client;
		this.identifier = id;
		this.sw = sw;
		this.sw.sendMap(map, client);
		this.sw.sendIndex(id, client);
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
					if(receive.startsWith("move:")) {
						this.sw.send("move:"+this.identifier+":"+receive.substring(receive.indexOf(":") + 1), this.identifier);
					}
					if(receive.startsWith("bomb:")) {
						this.sw.send("bomb:"+this.identifier+":"+receive.substring(receive.indexOf(":") + 1), this.identifier);
					} 
					if(receive.startsWith("bonus:")) {
						this.sw.send("bonus:"+receive.substring(receive.indexOf(":") + 1), this.identifier);
					} else {
						this.sw.send("move:"+this.identifier+":"+receive.substring(receive.indexOf(":") + 1), this.identifier);
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

}
