package socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import gamescene.StartGame;
import player.Player;

public class SocketReader implements Runnable{
	private Player player;
	private Socket client;
	private int identifier;
	private SocketWriter sw;
	private StartGame startGame;
	
	public SocketReader(Player player, Socket client, SocketWriter sw, String map, StartGame startGame) {
		this.player = player;
		this.client = client;
		this.identifier = player.getNumberOfPlayer();
		this.sw = sw;
		this.sw.sendMap(map, client);
		this.sw.sendIndex(player.getNumberOfPlayer(), client);
		this.startGame = startGame;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			while(true) {
				dis = new DataInputStream(client.getInputStream());
				String receive = dis.readUTF();
				System.out.println(receive);
				if(receive != null) {
					//System.out.println(this.identifier+":"+receive);
					if(receive.startsWith("move:")) {
						this.sw.send("move:"+this.identifier+":"+receive.substring(receive.indexOf(":") + 1), this.identifier);
					}
					else if(receive.startsWith("bomb:")) {
						this.sw.send("bomb:"+this.identifier+":"+receive.substring(receive.indexOf(":") + 1), this.identifier);
					} 
					else if(receive.startsWith("bonus:")) {
						this.sw.send("bonus:"+receive.substring(receive.indexOf(":") + 1), this.identifier);
					}else if(receive.startsWith("pseudo:")) {
						player.setUserName(receive.split(":")[1]);
					}else if(receive.startsWith("playerdeath:")) {
						this.player.setAlive(false);
					}
				}
			}
		} catch (IOException e) {
			player.setAlive(false);
			System.err.println("client failed, disconnected");
			if(startGame.isCurrentlyPlaying()) {
				this.sw.send("dead:"+player.getNumberOfPlayer(), this.identifier);
			}
			if(player.getUserName() == null) {
    			player.setUserName("notnull");
    		}
			//e.printStackTrace();
		} finally {
			try {
				if(dis != null) {
					dis.close();
				}
				if(client != null) {
					client = null;
				}
			} catch (IOException e) {
				
			}
		}
	}

}
