package socket;

import java.io.DataOutputStream;
import java.net.Socket;

public class SocketWriter implements Runnable{
	
	private Socket[] clients;
	
	public SocketWriter(Socket[] clients) {
		this.clients = clients;
	}

	public void send(String string, int id) { //send to all except sender
		for(int i = 0; i<this.clients.length; i++) {
			if(clients[i] != null && id != i) {
				Socket client = clients[i];
				try {
					DataOutputStream dos = new DataOutputStream(client.getOutputStream());
					dos.writeUTF(string);
				} catch (Exception e) {
					
				}
			}
		}
	}
	public void send(String string) { //send to all
		for(int i = 0; i<this.clients.length; i++) {
				Socket client = clients[i];
				try {
					DataOutputStream dos = new DataOutputStream(client.getOutputStream());
					dos.writeUTF(string);
				} catch (Exception e) {
					
				}
		}
	}
	
	public void sendMap(String string, Socket client) {
		try {
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			dos.writeUTF(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendIndex(int id, Socket client) {
		try {
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			dos.writeUTF("id:"+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("SocketWriter is running");
	}


}
