package player;
import java.net.Socket;

public class Player {
	
	private String userName;
	private int numberOfPlayer;
	public Player() {
		
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getNumberOfPlayer() {
		return numberOfPlayer;
	}
	public void setNumberOfPlayer(int numberOfPlayer) {
		this.numberOfPlayer = numberOfPlayer;
	}
	
}
