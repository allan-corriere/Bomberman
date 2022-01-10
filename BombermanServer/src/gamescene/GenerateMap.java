package gamescene;


public class GenerateMap {
	public Level masterLevel = new Level();
	public String mapToSend;
	
	public GenerateMap() {
		int[][] level = masterLevel.loadLevel02(); 
		/*
		 map(xLength,YLength):x,y,z,y,x,x,y,x,y,

		    0 = empty
		    1 = wall
		    2 = brick vide
		    3 = brick bonus speed
		    4 = brick bonus power
		    5 = brick bonus number 
		*/
		mapToSend = "map("+level.length+","+level[0].length+"):";
		
			
		int column = level.length;
    	for(int y =0; y < column; y++) {
    		for(int x=0; x < level[y].length; x++) {
    			if(level[y][x] == 0) {
    				mapToSend += "0,";
    			}
    			else if(level[y][x] == 1) {
    				mapToSend += "1,";
    			}
    			else if(level[y][x] == 2) {
    				mapToSend +=BrickCreation()+",";
    			}
    		}
    	}
    	
    	System.out.println(mapToSend);
	}
	
	//to generate random
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
	 
	//to generate random
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
	
	public int BrickCreation() {
		//generate bonuses
		int choiceTab[] = {2, 3, 4, 5};
	    int freq[] = {70, 10, 10, 10};
	    
	    int choice = myRand(choiceTab, freq, choiceTab.length);
	    // 2 = no bonus
	    // 3 = player speed
	    // 4 = bomb radius
	    // 5 = bomb number
	    return choice;
	   

	}
}
