import java.util.Random;
import java.util.Arrays;
import java.util.Date;

public class Search2{
  public static final int horizontalGridSize = 5;
  public static final int verticalGridSize = 6;

  public static final char[] input = {'W', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F'};

  // Static UI class to display the board
  //TODO check what 50 (size) does and if this really is a static value
  public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

  //get available pentominoes and store them in a static final
  public static int[] inputIDs = new int[input.length];

  public static void printTime(){
    Date date = new Date();
    System.out.println(date.toString());
  }

  // Helper function which starts the search for answers
  public static void search(){
    //'translate' given input
    for(int i=0; i<input.length; i++){
      int tmpID = characterToID(input[i]);
      inputIDs[i] = tmpID;
    }

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

    //TODO why use random?
    //Random random = new Random();
    //int randomIndex = random.nextInt(12);
    //recursive(field, inputIDs[randomIndex], 0);

    //Start brute force
    //bruteForce(field);
    //TODO test recursion
    //recursive(field, pentID, mutation)
    System.out.println(Arrays.toString(inputIDs));
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

  //TODO rewrite to use int[][] PentominoesLeft with [pentID[mutation]]
  /*
  private static boolean recursive2(int[][] field, int[][] pentominoesLeft){
    //TODO fix that the starting pentomino isn't varied (it always is the first given value, which doesn't always produce the right result)
    System.out.println("\n\nField:");
    System.out.println(Arrays.deepToString(field));
    System.out.println("PentID = " + pentID);
    System.out.println("Mutation = " + mutation);
    printTime();

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
      try{
        Thread.sleep(100);
        ui.setState(field);
      } catch (InterruptedException ie){
        //display the field
        Thread.sleep(100);
        ui.setState(field);
      }

      //if not all mutations have been tried, try the next one
      //TODO [on it] only use the given pentominoes
      //TODO Note that it can never start with: X, Y, L or F since these all create an infillable cell. Some rotations won't work either (like with W and F)
      System.out.println("Mutations available for this piece = " + PentominoDatabase.data[pentID].length);
      if(mutation < PentominoDatabase.data[pentID].length-1){
        System.out.println("Update pentomino mutation");
        System.out.println(Arrays.deepToString(PentominoDatabase.data[pentID]));
        recursive(addPentomino(field, pentID, mutation), pentID, ++mutation);
      } else if(pentID < 11){
        System.out.println("Update pentomino ID");
        System.out.println(pentID);
        //if all mutations have been tried, try the next pentomino
        recursive(addPentomino(field, pentID, mutation), ++pentID, 0);
      }
    }

    System.out.println("There's no solution");
    return false;
  } */



  private static boolean recursive(int[][] field, int pentID, int mutation){
    //TODO fix that the starting pentomino isn't varied (it always is the first given value, which doesn't always produce the right result)
    System.out.println("\n\nField:");
    System.out.println(Arrays.deepToString(field));
    System.out.println("PentID = " + pentID);
    System.out.println("Mutation = " + mutation);
    printTime();

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
      try{
        Thread.sleep(100);
        ui.setState(field);
      } catch (InterruptedException ie){
        //display the field
        ui.setState(field);
      }

      //if not all mutations have been tried, try the next one
      //TODO only use the given pentominoes
      //TODO Note that it can never start with: X, Y, L or F since these all create an infillable cell. Some rotations won't work either (like with W and F)
      System.out.println("Mutations available for this piece = " + PentominoDatabase.data[pentID].length);
      if(mutation < PentominoDatabase.data[pentID].length-1){
        System.out.println("Update pentomino mutation");
        System.out.println(Arrays.deepToString(PentominoDatabase.data[pentID]));
        recursive(addPentomino(field, pentID, mutation), pentID, ++mutation);
      } else if(pentID < 11){
        System.out.println("Update pentomino ID");
        System.out.println(pentID);
        //if all mutations have been tried, try the next pentomino
        recursive(addPentomino(field, pentID, mutation), ++pentID, 0);
      }
    }

    System.out.println("There's no solution");
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

  //return the board with the Pentomino added
  private static int[][] addPentomino(int[][] field, int pentID, int mutation){
    System.out.println("PentominoID = " + pentID);
    System.out.println("Mutation = " + mutation);
    int placeX = 0;
    int placeY = 0;

    //check where the pentomino has to be added
    for(int i=0; i<field.length; i++){
      //go trough every row
      for(int j=0; j<field[i].length; j++){
        //go trough every item in the row
        System.out.println("Field " + field[i][j]);
        if(field[i][j] == -1){
          //the next block has to fill this tile
          placeX = j;
          placeY = i;

          //make the loop stop
          i=field.length-1;
          j=field[i].length-1;

          System.out.println("PlaceX = " + placeX + " PlaceY=" + placeY);
        }
      }
    }

    //get the pentomino shape
    int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
    System.out.println("PieceToPlace:");
    System.out.println(Arrays.deepToString(pieceToPlace));

    //first check if this pentomino can even be added
    //it can be added if it doesn't: overlap with other pentominoes, goes over the borders or creates an unfillable hole
    boolean possibleToPlace = true;

    //if at any point it becomes clear that the block can't be placed, move on
    while(possibleToPlace){
      //check if it doesn't go over the 'edge'
      int widthLeft = horizontalGridSize-placeX-1;
      int heightLeft = verticalGridSize-placeY-1;

      if (widthLeft >= pieceToPlace.length) {
        //it could fit
        possibleToPlace = true;
        System.out.println("Fits width");
      } else {
        possibleToPlace = false;
      }

      if (heightLeft >= pieceToPlace[0].length & possibleToPlace){
        //it could fit
        possibleToPlace = true;
        System.out.println("Fits height");
      } else {
        possibleToPlace = false;
      }

      //check if it overlaps
      //pieceToPlace.length+placeX-1 = the width of the piece + the starting point
      int tmpX = 0;
      int tmpY = 0;

      //TODO rewrite/check
      if(possibleToPlace){
        for(int i = placeY; i < pieceToPlace.length+placeY-1; i++){ // loop over Y position of pentomino
          System.out.println("pieceToPlace.length+placeY");
          System.out.println(pieceToPlace.length+placeY);
          System.out.println("placeY");
          System.out.println(placeY);
          for (int j = placeX; j < pieceToPlace[0].length+placeX-1; j++){ // loop over X position of pentomino
            if(pieceToPlace[tmpY][tmpX] != 0){
              if (field[i][j] != -1){
                //there's overlap
                System.out.println("OVERLAP");
                possibleToPlace = false;
                i = pieceToPlace.length+placeY-1;
                j = pieceToPlace[0].length+placeX-1;
              }
            }

            tmpX++;
          }
          tmpX=0;
          tmpY++;
        }
      }

      //TODO check if it creates an unfillable hole
      //an unfillable hole would be a hole that is smaller than 5 blocks or that can't be filled with the blocks that are left

      //If there is a possibility to place the piece on the field, do it
      tmpX = 0;
      tmpY = 0;
      boolean firstCellCovered = false;

      //TODO check why this if effects the code while it already being checked by the while
      if(possibleToPlace){
        for(int i = placeY; i < pieceToPlace.length+placeY; i++){ // loop over Y position of pentomino
          for (int j = placeX; j < pieceToPlace[0].length+placeX; j++){ // loop over X position of pentomino
            //if pieceToPlace actually has a block in that spot, place it on the field
            if(pieceToPlace[tmpY][tmpX] != 0){
              //check if the first piece that you try to fill in is actually being filled
              if(tmpY == placeY && tmpX == placeX){
                firstCellCovered = true;
              }
              if(firstCellCovered){
                field[tmpY][tmpX] = pentID;
                System.out.println("If it fits, it sits");
              } else {
                possibleToPlace = false;
                i=pieceToPlace.length+placeY-1;
                j=pieceToPlace[0].length+placeX-1;
              }

            }
            tmpX++;
          }
          tmpX=0;
          tmpY++;
        }
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
