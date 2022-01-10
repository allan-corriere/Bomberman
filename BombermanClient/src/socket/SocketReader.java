package socket;

import java.io.DataInputStream;
import java.io.IOException;

public class SocketReader implements Runnable{
	
	private GameClient client;
	
	public SocketReader(GameClient client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true) {
				dis = new DataInputStream(client.getInputStream());
				String receive = dis.readUTF();
				System.out.println(receive);
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
