import java.util.Random;

public class Search
{
  public static final int horizontalGridSize = 5;
  public static final int verticalGridSize = 6;

  public static final char[] input = { 'W', 'Y', 'I', 'T', 'Z', 'L'};

  // Static UI class to display the board
  public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

  // Helper function which starts the brute force algorithm
  public static void search()
  {
    // Initialize an empty board
    int[][] field = new int[horizontalGridSize][verticalGridSize];

    for(int i = 0; i < field.length; i++)
    {
      for(int j = 0; j < field[i].length; j++)
      {
        // -1 in the state matrix corresponds to empty square
        // Any positive number identifies the ID of the pentomino
      	field[i][j] = -1;
      }
    }
    //Start brute force
    bruteForce(field);
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

  private static boolean recursive(int[][] field, int pentId, int mutation){
    //TODO write fieldIsFull
    if(fieldIsFull()){
      //you found the solution, show it
      try{
        Thread.sleep(100);
        ui.setState(field);
        System.out.println("Solution found");
      } catch (InterruptedException ie){
        //display the field
        ui.setState(field);
        System.out.println("Solution found");
        break;
      }

      return true;
    } else {
      //if there isn't a solution

      //if not all mutations have been tried, try the next one
      //TODO write addPentomino
      if(lastMutation < PentominoDatabase.data[pentID].length-1){
        recursive(addPentomino(field, pentId, mutation), pentId, mutation++);
      } else if(pentID < 11){
          //if all mutations have been tried, try the next pentomino
          recursive(addPentomino(field, pentId, mutation), pentId++, mutation);
        }
      }

      return false;
    }
  }

  private static void addPentomino(field, pentId, mutation){
    int placeX = 0;
    int placeY = 0;
    boolean found = false;

    //check where the pentomino has to be added
    for(int i=0; i<field.length; i++){
      //go trough every row
      for(int j=0; j<field[i].length; j++){
        //go trough every item in the row
        if(field[i][j] == 0){
          //the next block has to fill this tile
          found = true;
          placeX = i;
          placeY = j;
        }
      }
    }

    //get the pentomino shape
    int[][] pieceToPlace = PentominoDatabase.data[pentId][mutation];

    //first check if this pentomino can even be added
    //it can be added if it doesn't: overlap with other pentominoes, goes over the borders and creates an unfillable hole
    boolean possibleToPlace = false;

    //check if it doesn't go over the 'edge'
    int widthLeft = horizontalGridSize-x;
    int heightLeft = verticalGridSize-y;

    if (widthLeft >= pieceToPlace.length) {
      //it could fit
      possibleToPlace = true;
    } else {
      possibleToPlace = false;
    }

    if (heightLeft >= pieceToPlace[0].length)
      //it could fit
      possibleToPlace = true;
    } else {
      possibleToPlace = false;
    }

    //check if it overlaps


    //check if it creates an unfillalbe hole

    /*
    //If there is a possibility to place the piece on the field, do it
    if (possibleToPlace) {
      addPiece(field, pieceToPlace, pentID, x, y);
    }*/

    return field;
  }

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

     	if (solutionFound) {
  			try{
  				Thread.sleep(100);
  				ui.setState(field);
  				System.out.println("Solution found");
  			} catch (InterruptedException ie){
    			//display the field
    			ui.setState(field);
    			System.out.println("Solution found");
    			break;
    		}
      }
	  }
  }

  // Adds a pentomino to the position on the field (overriding current board at that position)
  public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y){
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
      search();
  }
}
