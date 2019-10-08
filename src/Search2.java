import java.util.Random;
import java.util.Arrays;

public class Search2{
  public static final int horizontalGridSize = 10;
  public static final int verticalGridSize = 6;

  public static final char[] input = { 'W', 'Y', 'I', 'T', 'Z', 'L'};

  // Static UI class to display the board
  //TODO check what 50 (size) does and if this really is a static value
  public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

  //get available pentominoes and store them in a static final
  public static int[] inputIDs = new int[input.length];

  Search2(){
    for(int i=0; i<input.length; i++){
      int tmpID = characterToID(input[i]);
      inputIDs[i] = tmpID;
    }
  }

  // Helper function which starts the brute force algorithm
  public static void search(){
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

    //TODO test recursion
    //Start brute force
    //bruteForce(field);
    //recursive(field, pentID, mutation)
    recursive(field, inputIDs[0], 0);
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

  private static boolean recursive(int[][] field, int pentID, int mutation){
    if(fieldIsFull(field)){
      //you found the solution, show it
      //TODO why is there a try catch?
      try{
        Thread.sleep(100);
        ui.setState(field);
        System.out.println("Solution found");
      } catch (InterruptedException ie){
        //display the field
        ui.setState(field);
        System.out.println("Solution found");
      }

      return true;
    } else {
      //if there isn't a solution
      //TODO remove after debugging
      try{
        Thread.sleep(100);
        ui.setState(field);
      } catch (InterruptedException ie){
        //display the field
        ui.setState(field);
      }

      //if not all mutations have been tried, try the next one
      //TODO only use the given pentominoes
      //TODO Note that it can never start with: X, Y, L or F since these all create an infillable cell
      if(mutation < PentominoDatabase.data[pentID].length-1){
        recursive(addPentomino(field, pentID, mutation), pentID, mutation++);
      } else if(pentID < 11){
        //if all mutations have been tried, try the next pentomino
        recursive(addPentomino(field, pentID, mutation), pentID++, mutation);
      }
    }

    return false;
  }

  public static boolean fieldIsFull(int[][] field){
    boolean isNotFull = false;

    for(int i=0; i<field.length; i++){
      for(int j=0; j<field[i].length; j++){
        if(field[i][j] == -1){
          isNotFull = true;
        }
      }
    }

    return !isNotFull;
  }

  private static int[][] addPentomino(int[][] field, int pentID, int mutation){
    int placeX = 0;
    int placeY = 0;

    //check where the pentomino has to be added
    for(int i=0; i<field.length; i++){
      //go trough every row
      for(int j=0; j<field[i].length; j++){
        //go trough every item in the row
        if(field[i][j] == -1){
          //the next block has to fill this tile
          placeX = i;
          placeY = j;
        }
      }
    }

    //get the pentomino shape
    int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

    //first check if this pentomino can even be added
    //it can be added if it doesn't: overlap with other pentominoes, goes over the borders and creates an unfillable hole
    boolean possibleToPlace = true;

    //if at any point it becomes clear that the block can't be placed, move on
    while(possibleToPlace){
      //check if it doesn't go over the 'edge'
      int widthLeft = horizontalGridSize-placeX;
      int heightLeft = verticalGridSize-placeY;

      if (widthLeft >= pieceToPlace.length) {
        //it could fit
        possibleToPlace = true;
      } else {
        possibleToPlace = false;
      }

      if (heightLeft >= pieceToPlace[0].length){
        //it could fit
        possibleToPlace = true;
      } else {
        possibleToPlace = false;
      }

      //check if it overlaps
      //pieceToPlace.length+placeX-1 = the width of the piece + the starting point - 1 since you count the starting tile twice
      int tmpX = 0;
      int tmpY = 0;

      for(int i = placeY; i < pieceToPlace.length+placeY-1; i++){ // loop over Y position of pentomino

        for (int j = placeX; j < pieceToPlace[0].length+placeX-1; j++){ // loop over X position of pentomino
          if (field[tmpY][tmpX] != -1){
            //there's overlap
            System.out.println("OVERLAP");
            possibleToPlace = false;
          }

          tmpX++;
        }
        tmpY++;
      }

      //check if it creates an unfillable hole
      //With the current algorithm, unfillable holes are automatically seen as invalid.

      //If there is a possibility to place the piece on the field, do it
      tmpX = 0;
      tmpY = 0;

      for(int i = placeY; i < pieceToPlace.length+placeY-1; i++){ // loop over Y position of pentomino

        for (int j = placeX; j < pieceToPlace[0].length+placeX-1; j++){ // loop over X position of pentomino
          field[tmpY][tmpX] = pentID;

          tmpX++;
        }
        tmpY++;
      }
    }

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
    for(int i = 0; i < piece.length; i++){ // loop over x position of pentomino{
      for (int j = 0; j < piece[i].length; j++){ // loop over y position of pentomino{
        if (piece[i][j] == 1){
          // Add the ID of the pentomino to the board if the pentomino occupies this square
          field[x + i][y + j] = pieceID;
        }
      }
    }
  }

  // Main function. Needs to be executed to start the brute force algorithm
  public static void main(String[] args){
    //TODO ask for the desired size of the board and for available pentominoes

    search();
  }
}
