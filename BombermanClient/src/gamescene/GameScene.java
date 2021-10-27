package gamescene;

import gameobject.Bomb;
import gameobject.Brick;
import gameobject.Player;
import gameobject.Wall;
import gameobject.bonus.Bonus;

public class GameScene {
	private Player[] playerTab;
	private Wall[] wallTab;
	private Brick[] brickTab;
	private Bomb[] bombTab;
	private Bonus[] bonusTab;
	
	
	public GameScene() {
		
	}
	
	//getters and setters
	public Player[] getPlayerTab() {
		return playerTab;
	}
	public void setPlayerTab(Player[] playerTab) {
		this.playerTab = playerTab;
	}
	public Wall[] getWallTab() {
		return wallTab;
	}
	public void setWallTab(Wall[] wallTab) {
		this.wallTab = wallTab;
	}
	public Brick[] getBrickTab() {
		return brickTab;
	}
	public void setBrickTab(Brick[] brickTab) {
		this.brickTab = brickTab;
	}
	public Bomb[] getBombTab() {
		return bombTab;
	}
	public void setBombTab(Bomb[] bombTab) {
		this.bombTab = bombTab;
	}
	public Bonus[] getBonusTab() {
		return bonusTab;
	}
	public void setBonusTab(Bonus[] bonusTab) {
		this.bonusTab = bonusTab;
	}
	
	
	
}
