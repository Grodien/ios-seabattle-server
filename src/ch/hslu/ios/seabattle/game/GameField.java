package ch.hslu.ios.seabattle.game;

import java.util.Random;

public class GameField
    {

     public static final int SIZE = 10; 
     public static final int SMALL_SHIP_COUNT = 0;
     public static final int MEDIUM_SHIP_COUNT = 4;
     public static final int BIG_SHIP_COUNT = 3;
     public static final int HUGE_SHIP_COUNT = 2;
     public static final int ULTIMATE_SHIP_COUNT = 0;

     public static final int VALUE_FREE = 0;
     public static final int VALUE_SHIP = 1;
     public static final int VALUE_FREE_HIT = 2;
     public static final int VALUE_SHIP_HIT = 3;
     
     private static final int SHIP_PLACEMENT_MAX_TRYS = 200;
     private static final int RANDOM_PLACEMENT_MAX_TRYS = 10;
     
     private static int [][] fNeighbors = new int[][] { 
    	 new int [] {-1, -1},
    	 new int [] {-1, 0},
    	 new int [] {-1, 1},
    	 new int [] {0, -1},
    	 new int [] {0, 1},
    	 new int [] {1, -1},
    	 new int [] {1, 0},
    	 new int [] {1, 1}};

     private int[][] fField;

     public GameField()
     {
         fField = new int[SIZE][SIZE];
     }
     
     public boolean isValidCoord(int col, int row, int size)
     {
         return row >= 0 && row < size && col >= 0 && col < size;
     }
     
     public int[][] getField()
     {
         return fField;
     }

     public void setCell(int row, int col, int value)
     {
         fField[col][row] = value;
     }

     public boolean createRandomField()
     {
    	 int count = 0;
    	 while (count < RANDOM_PLACEMENT_MAX_TRYS) {
    		 count++;
			
			 // Fill field with zeros.    		 
    		 for (int k = 0; k < SIZE; k++) {
    			 for (int l = 0; l < SIZE; l++) {
    				 fField[l][k] = VALUE_FREE;
    			 }
    		 }
			
			
    		 // Fill ships, start with the Big ones.
    		 Random random = new Random();      
			 boolean success = true;
			 for(int i = 0; i < ULTIMATE_SHIP_COUNT; i++) {
			     success &= fillShipByRandom(random, 8);
		  	 }
			 
			 for(int i = 0; i < HUGE_SHIP_COUNT; i++) {
				 success &= fillShipByRandom(random, 4);
			 }
			
			 for(int i = 0; i < BIG_SHIP_COUNT; i++) {
				 success &= fillShipByRandom(random, 3);
			 }
			
			 for(int i = 0; i < MEDIUM_SHIP_COUNT; i++) {
				 success &= fillShipByRandom(random, 2);
			 }
			
			 for(int i = 0; i < SMALL_SHIP_COUNT; i++) {
				 success &= fillShipByRandom(random, 1);
			 }
			 
			 if (success) {
				 return true;
			 }
    	 }
    	 
    	 return false;
    }
    
    private boolean fillShipByRandom(Random random, int length)
    {
        int tryCount = 0;
        while (tryCount < SHIP_PLACEMENT_MAX_TRYS)
        {
        	tryCount++;
        	
        	// Choose random position
            int x1= Math.abs(random.nextInt()) % (SIZE);
            int y1= Math.abs(random.nextInt()) % (SIZE);
            
            // Choose random direction
            int x2= Math.abs(random.nextInt()) % (2) - 1;
            int y2= Math.abs(random.nextInt()) % (2) - 1;
            
            if(canPlaceShipHere(x1, y1, x1+x2*length, y1+y2*length)
                && (y2==0||x2==0)&&!((x2==0) && (y2==0))
                && isValidCoord(x1+x2*length, y1+y2*length))
            {
                for (int l = 0; l < length; l++)
                {
                    fField[x1 + x2*l][y1+y2*l] = VALUE_SHIP;
                }
                return true;
            }
        }
        
        return false;
    }
        
    boolean areNeighborsFree (int row, int col)
    {
        for (int i = 0; i < fNeighbors.length; i++)
        {
            int dR = fNeighbors[i][0];
            int dC = fNeighbors[i][1];
            if(isValidCoord (row + dR, col + dC))
            {
                if(fField[row + dR][col + dC] == VALUE_SHIP)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canPlaceShipHere (int x1, int y1, int x2, int y2)
    {
        int l = fField[0].length;
        if((x1 < l) && (y1 < l) && (x2 < l) && (y2 < l))
        {
            for(int i = x1; i <= x2; i++)
            {
                if(!areNeighborsFree(i, y1))
                {
                    return false;
                }
            }
            for(int i = x2; i <= x1; i++)
            {
                if(!areNeighborsFree(i, y1))
                {
                    return false;
                }
            }
            for(int i = y1; i <= y2; i++)
            {
                if(!areNeighborsFree(x1, i))
                {
                    return false;
                }
            }
            for(int i = y2; i <= y1; i++)
            {
                if(!areNeighborsFree(x1, i))
                {
                    return false;
                }
            }
            if(!areNeighborsFree(x1, y2))
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        return true;        
    }
    
    static boolean isValidCoord (int col, int row)
    {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
    
    public int getCell(int x, int y) {
    	return fField[y][x];
    }
    
    public boolean hasMoreShips()
    {
        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                if (fField[x][y] == VALUE_SHIP)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public synchronized void printInvisibleFields()
    {
        for (int k = 0; k < SIZE; k++)
        {
            for (int l = 0; l < SIZE; l++)
            {
                if (fField[k][l] == VALUE_SHIP)
                {
                    System.out.print("S ");
                }
                if (fField[k][l] == VALUE_FREE)
                {
                	System.out.print("~ ");
                }
                if (fField[k][l] == VALUE_SHIP_HIT)
                {
                	System.out.print("X ");
                }
                if (fField[k][l] == VALUE_FREE_HIT)
                {
                	System.out.print("0 ");
                }
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }
    
    public String getAsString() {
    	StringBuilder builder = new StringBuilder();
    	
    	for (int x = 0; x < SIZE; x++) {
    		for (int y = 0; y < SIZE; y++) {
    			builder.append(fField[x][y]);
    		}
    	}
    	
    	return builder.toString();
    }
    
    public String getAsHiddenString() {
    	StringBuilder builder = new StringBuilder();
    	
    	for (int x = 0; x < SIZE; x++) {
    		for (int y = 0; y < SIZE; y++) {
    			int value = fField[x][y];
    			if (value == VALUE_SHIP)
    				builder.append(VALUE_FREE);
    			else
    				builder.append(value);
    		}
    	}
    	
    	return builder.toString();
    }
    
    public boolean shipIsDestroyed(int x, int y)
    {
        while((isValidCoord(x+1, y)) 
                && (getCell(x+1, y) != VALUE_FREE)
                && (getCell(x+1, y) != VALUE_FREE_HIT))
        {
            x++;
            if(getCell(x, y) == VALUE_SHIP)
            {
                return false;
            }
        }
        while((isValidCoord(x-1, y)) 
                && (getCell(x-1, y) != VALUE_FREE)
                && (getCell(x-1, y) != VALUE_FREE_HIT))
        {
            x--;
            if(getCell(x, y) == VALUE_SHIP)
            {
                return false;
            }
        }
        while((isValidCoord(x, y-1))
                && (getCell(x, y-1) != VALUE_FREE)
                && (getCell(x, y-1) != VALUE_FREE_HIT))
        {
            y--;
            if(getCell(x, y) == VALUE_SHIP)
            {
                return false;
            }
        }
        while((isValidCoord(x, y+1)) 
                && (getCell(x, y+1) != VALUE_FREE)
                && (getCell(x, y+1) != VALUE_FREE_HIT))
        {
            y++;
            if(getCell(x, y) == VALUE_SHIP)
            {
                return false;
            }
        }
        return true;
        
    }
    
    public void destroyShipSurroundings(int x, int y)
    {
        while(isValidCoord(x+1, y)
                                && getCell(x+1, y) == VALUE_SHIP_HIT)
        {
        	 if(isValidCoord(x, y+1))
             {
                 setCell(x, y+1, VALUE_FREE_HIT);
             }
             if(isValidCoord(x, y-1))
             {
                 setCell(x, y-1, VALUE_FREE_HIT);
             }
            x++;
            if(isValidCoord(x, y+1))
            {
                setCell(x, y+1, VALUE_FREE_HIT);
            }
            if(isValidCoord(x, y-1))
            {
                setCell(x, y-1, VALUE_FREE_HIT);
            }
        }
        if(isValidCoord(x+1, y+1))
        {
            setCell(x+1, y+1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x+1, y-1))
        {
            setCell(x+1, y-1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x+1, y))
        {
            setCell(x+1, y, VALUE_FREE_HIT);
        }
        while(isValidCoord(x-1, y)
                && getCell(x-1, y) == VALUE_SHIP_HIT)
        {
            if(isValidCoord(x, y+1))
            {
                setCell(x, y+1, VALUE_FREE_HIT);
            }
            if(isValidCoord(x, y-1))
            {
                setCell(x, y-1, VALUE_FREE_HIT);
            }
            x--;
            if(isValidCoord(x, y+1))
            {
                setCell(x, y+1, VALUE_FREE_HIT);
            }
            if(isValidCoord(x, y-1))
            {
                setCell(x, y-1, VALUE_FREE_HIT);
            }
        }
        if(isValidCoord(x-1, y+1))
        {
            setCell(x-1, y+1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x-1, y-1))
        {
            setCell(x-1, y-1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x-1, y))
        {
            setCell(x-1, y, VALUE_FREE_HIT);
        }
        while(isValidCoord(x, y+1)
                && getCell(x, y+1) == VALUE_SHIP_HIT)
        {   
            if(isValidCoord(x+1, y))
            {
                setCell(x+1, y, VALUE_FREE_HIT);
            }
            if(isValidCoord(x-1, y))
            {
                setCell(x-1, y, VALUE_FREE_HIT);
            }
            y++;
            if(isValidCoord(x+1, y))
            {
                setCell(x+1, y, VALUE_FREE_HIT);
            }
            if(isValidCoord(x-1, y))
            {
                setCell(x-1, y, VALUE_FREE_HIT);
            }
        }
        if(isValidCoord(x+1, y+1))
        {
            setCell(x+1, y+1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x-1, y+1))
        {
            setCell(x-1, y+1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x, y+1))
        {
            setCell(x, y+1, VALUE_FREE_HIT);
        }
        while(isValidCoord(x, y-1)
                && getCell(x, y-1) == VALUE_SHIP_HIT)
        {  
            if(isValidCoord(x+1, y))
            {
                setCell(x+1, y, VALUE_FREE_HIT);
            }
            if(isValidCoord(x-1, y))
            {
                setCell(x-1, y, VALUE_FREE_HIT);
            }
            y--;
            if(isValidCoord(x+1, y))
            {
                setCell(x+1, y, VALUE_FREE_HIT);
            }
            if(isValidCoord(x-1, y))
            {
                setCell(x-1, y, VALUE_FREE_HIT);
            }
        }
        if(isValidCoord(x+1, y-1))
        {
            setCell(x+1, y-1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x-1, y-1))
        {
            setCell(x-1, y-1, VALUE_FREE_HIT);
        }
        if(isValidCoord(x, y-1))
        {
            setCell(x, y-1, VALUE_FREE_HIT);
        }
    }
}
