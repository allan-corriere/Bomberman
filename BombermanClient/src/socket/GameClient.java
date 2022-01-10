package socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClient extends Socket{
	private String nickname;
	
	public GameClient(String server, int port, String nickname) throws Exception, IOException {
		super(server, port);
		this.nickname = nickname;
	}
	
	
}
