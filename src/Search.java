import java.util.Random;

public class Search
{
    public static int horizontalGridSize = 5;
    public static int verticalGridSize = 6;
    public static int area = horizontalGridSize*verticalGridSize;
    public static UI ui;
    public static char[] input = {'W','I','Y','T','V','L'};
    public static boolean lpdone = false;

    public static void createWindow(){
        ui = new UI(horizontalGridSize, verticalGridSize, 50);
        LandingPage.startWindow.dispose();
        search();
    }
    // Helper function which starts the brute force algorithm
    public static void search() {
        if (input.length != horizontalGridSize*verticalGridSize / 5) {
            System.out.println("Not possible to find a solution");
        } else {
            // Initialize an empty board
            int[][] field = new int[horizontalGridSize][verticalGridSize];

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    // -1 in the state matrix corresponds to empty square
                    // Any positive number identifies the ID of the pentomino
                    field[i][j] = -1;
                }
            }
            //Start brute force
            bruteForce(field);
        }
    }

    private static int characterToID(char character) {
    	int pentID = -1;
    	if (character == 'X') {
    		pentID = 0;
    	} else if (character == 'I') {
    		pentID = 1;
    	} else if (character == 'Z') {
    		pentID = 2;
    	} else if (character == 'T') {
    		pentID = 3;
    	} else if (character == 'U') {
    		pentID = 4;
     	} else if (character == 'V') {
     		pentID = 5;
     	} else if (character == 'W') {
     		pentID = 6;
     	} else if (character == 'Y') {
     		pentID = 7;
    	} else if (character == 'L') {
    		pentID = 8;
    	} else if (character == 'P') {
    		pentID = 9;
    	} else if (character == 'N') {
    		pentID = 10;
    	} else if (character == 'F') {
    		pentID = 11;
    	}
    	return pentID;
    }

    //TODO replace bruteForce with recursions
    private static void bruteForce( int[][] field){
    	Random random = new Random();
    	boolean solutionFound = false;

    	while (!solutionFound) {
    		solutionFound = true;

      	//Empty board again to find a solution
  			for (int i = 0; i < field.length; i++) {
  				for (int j = 0; j < field[i].length; j++) {
  					field[i][j] = -1;
  				}
  			}


    		//Put all pentominoes with random rotation/inversion on a random position on the board
    		for (int i = 0; i < input.length; i++) {

    			//Choose a pentomino and randomly rotate/inverse it
    			int pentID = characterToID(input[i]);
    			int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
    			int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

    			//Randomly generate a position to put the pentomino on the board
    			int x;
    			int y;
    			if (horizontalGridSize < pieceToPlace.length) {
    				//this particular rotation of the piece is too long for the field
    				x=-1;
    			} else if (horizontalGridSize == pieceToPlace.length) {
    				//this particular rotation of the piece fits perfectly into the width of the field
    				x = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				x = random.nextInt(horizontalGridSize-pieceToPlace.length+1);
    			}

    			if (verticalGridSize < pieceToPlace[0].length) {
    				//this particular rotation of the piece is too high for the field
    				y=-1;
    			} else if (verticalGridSize == pieceToPlace[0].length) {
    				//this particular rotation of the piece fits perfectly into the height of the field
    				y = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				y = random.nextInt(verticalGridSize-pieceToPlace[0].length+1);
    			}

    			//If there is a possibility to place the piece on the field, do it
    			if (x >= 0 && y >= 0) {
	    			addPiece(field, pieceToPlace, pentID, x, y);
	    		}
    		}


    		for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if(field[i][j] == -1) solutionFound = false;
				}
			}

  			try{
  				Thread.sleep(200);
  				ui.setState(field);
  			//	System.out.println("Solution found");
  			} catch (InterruptedException ie){
    			//display the field
    			System.out.println("Solution not found");
    			break;
    		}
            if (solutionFound) {
                ui.setState(field);
                System.out.println("Solution found");
                break;
            }
    	}
    }



    // Adds a pentomino to the position on the field (overriding current board at that position)
    private static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }


    // Main function. Needs to be executed to start the brute force algorithm
    public static void main(String[] args)
    {
        char[] args2 = new char[args.length];
        for(int i=0;i<args2.length;i++)
        {
            args2[i]=args[i].charAt(0);
        }
        //createWindow(5,6);
		LandingPage.createWindow();
        while(!lpdone){
        	System.out.println("Work in progress");
		}
        System.out.println("Done!");
        System.out.println(input);
        createWindow();
    }
}
