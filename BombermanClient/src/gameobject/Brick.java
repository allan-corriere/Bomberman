package gameobject;

import java.io.File;
import java.util.Timer;

import gameobject.attribute.DestructableObject;
import gameobject.attribute.GameObject;
import gameobject.attribute.UnmovableObject;
import gameobject.bonus.BombNumberBonus;
import gameobject.bonus.BombPowerBonus;
import gameobject.bonus.Bonus;
import gameobject.bonus.PlayerSpeedBonus;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Brick extends GameObject implements DestructableObject, UnmovableObject {
	private Image image = new Image(new File("ressources/breakable_brick.png").toURI().toString());
	public Bonus brickBonus;

	public Brick(Timer gameTimer) {
		this.gameTimer = gameTimer;
		fxLayer = new ImageView(image);
		this.setPosX(50.0);
		this.setPosY(50.0);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
		BonusCreation();	
	}
	
	public Brick(Timer gameTimer, double posX, double posY) {
		this.gameTimer = gameTimer;
		fxLayer = new ImageView(image);
		this.setPosX(posX);
		this.setPosY(posY);
		fxLayer.setFitHeight(50.0);
		fxLayer.setFitWidth(50.0);
		BonusCreation();
		
	}
	
	static int findCeil(int arr[], int r, int l, int h)
	{
	    int mid;
	    while (l < h)
	    {
	        mid = l + ((h - l) >> 1); // Same as mid = (l+h)/2
	        if(r > arr[mid])
	            l = mid + 1;
	        else
	            h = mid;
	    }
	    return (arr[l] >= r) ? l : -1;
	}
	 

	static int myRand(int arr[], int freq[], int n)
	{
	    // Create and fill prefix array
	    int prefix[] = new int[n], i;
	    prefix[0] = freq[0];
	    for (i = 1; i < n; ++i)
	        prefix[i] = prefix[i - 1] + freq[i];
	 
	    // prefix[n-1] is sum of all frequencies.
	    // Generate a random number with
	    // value from 1 to this sum
	    int r = ((int)(Math.random()*(323567)) % prefix[n - 1]) + 1;
	 
	    // Find index of ceiling of r in prefix array
	    int indexc = findCeil(prefix, r, 0, n - 1);
	    return arr[indexc];
	}
	
	public void BonusCreation() {
		//generate bonuses
		int choiceTab[] = {0, 1, 2, 3};
	    int freq[] = {70, 10, 10, 10};
	    
	    int choice = myRand(choiceTab, freq, choiceTab.length);
	    // 0 = no bonus
	    // 1 = player speed
	    // 2 = bomb radius
	    // 3 = bomb number
	    if(choice == 0) {
	    	brickBonus = null;
	    }
	    if(choice == 1) {
	    	brickBonus = new PlayerSpeedBonus(gameTimer, this.getPosX(), this.getPosY());
	    }
	    if(choice == 2) {
	    	brickBonus = new BombPowerBonus(gameTimer, this.getPosX(), this.getPosY());
	    }
	    if(choice == 3) {
	    	brickBonus = new BombNumberBonus(gameTimer, this.getPosX(), this.getPosY());
	    }

	    System.out.println(choice);
	}
	
	public Bonus getBrickBonus() {
		return brickBonus;
	}

	public void setBrickBonus(Bonus brickBonus) {
		this.brickBonus = brickBonus;
	}
	
}
