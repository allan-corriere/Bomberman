import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
	private int port;
	private String hostName;
	private String playerName;
	private Socket socket;
	private PrintWriter out;
	
	public Connection(int port, String hostName, String playerName) {
		this.port = port;
		this.hostName = hostName;
		this.playerName = playerName;
	}
	
	public void connect() throws Exception {
		this.socket = new Socket(this.hostName, this.port);
		this.out = new PrintWriter(this.socket.getOutputStream(), true);
	}
	
	public void sendPositionData(int x, int y) throws Exception {
		this.out.println(this.playerName + ":" + x + ":" +y);
	}
	
	public void closeConnection() throws Exception {
		this.out.close();
		this.socket.close();
	}
}
