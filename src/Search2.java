import java.util.Random;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;

public class Search2{
  public static final int horizontalGridSize = 5;
  public static final int verticalGridSize = 6;

  public static final char[] input = {'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F'};

  // Static UI class to display the board
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

    //sort the array
    Arrays.sort(inputIDs);

    // Initialize an empty board
    int[][] field = new int[horizontalGridSize][verticalGridSize];

    for(int i = 0; i < field.length; i++){
      for(int j = 0; j < field[i].length; j++){
        // -1 in the state matrix corresponds to empty square
        // Any positive number identifies the ID of the pentomino
      	field[i][j] = -1;
      }
    }

    //Start brute force
    //bruteForce(field);

    //recursion
    ArrayList<Integer> emptyArrayList = new ArrayList<>();
    System.out.println(Arrays.toString(inputIDs));
    recursive(field, inputIDs, emptyArrayList, inputIDs[0], 0);
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

  private static void recursive(int[][] field, int[] givenPentominoes, ArrayList<Integer> usedPentominoes, int currentID, int currentRotation){
    System.out.println("givenPentominoes: " + Arrays.toString(givenPentominoes));
    System.out.println("usedPentominoes: " + Arrays.toString(usedPentominoes.toArray()));
    System.out.println("\n\nField:");
    System.out.println(Arrays.deepToString(field));
    System.out.println("currentID = " + currentID);
    System.out.println("currentRotation = " + currentRotation);
    printTime();

    System.out.println("givenPentominoes[givenPentominoes.length-1] = " + givenPentominoes[givenPentominoes.length-1]);

    //TODO check when all options have been exhausted
    if(fieldIsFull(field)){
      //you found the solution, show it
      ui.setState(field);
      System.out.println("Solution found");
    } else if (usedPentominoes.size()==givenPentominoes.length){
      //check if there are still pentominoes or rotations left
      System.out.println("There's no solution");
    } else {
      System.out.println("Inside part 3");
      //if there isn't a solution
      try {
        Thread.sleep(10);
        ui.setState(field);
      } catch (InterruptedException ie){
          System.out.println("Error while trying to display steps for finding solution");
      }

      //prevents it from crashing by trying a pentomino that doesn't exist
      if(currentID < givenPentominoes[givenPentominoes.length-1]){
        System.out.println("Inside part 4");
        System.out.println("currentID" + currentID);
        //if this pentomino has already been used, skip it now (but also check if there's a next one and if not start at 0)
        if(usedPentominoes.contains(currentID)){
          if(currentID == givenPentominoes.length-1){
            recursive(field, givenPentominoes, usedPentominoes, 0, 0);
          } else {
            recursive(field, givenPentominoes, usedPentominoes, ++currentID, 0);
          }
        }

        //if the block is 'chosen' with this rotation
        //cannot be done in the recursive call itself, since add returns a boolean
        usedPentominoes.add(currentID);
        recursive(addPentomino(field, currentID, currentRotation), givenPentominoes, usedPentominoes, ++currentID, 0);

        //if this rotation doesn't work
        //go back one step by removing the last used pentomino from the field and remove it from used items

        //-2 since you just added the current one already
        //if there was a previous element, remove it
        if(usedPentominoes.size()>0){
          int lastUsedPentomino = usedPentominoes.get(usedPentominoes.size()-2);
          for(int i=0; i<field.length; i++){
            for(int j=0; j<field[0].length; j++){
              if(field[i][j] == lastUsedPentomino){
                field[i][j] = -1;
              }
            }
          }

          //remove the last item, that you just added but isn't needed anymore
          usedPentominoes.remove(Integer.valueOf(usedPentominoes.size()-2));
        }

        //if not all rotations have been tried, try them
        if(currentRotation < PentominoDatabase.data[currentID].length-1){
          currentRotation++;
          recursive(addPentomino(field, currentID, currentRotation), givenPentominoes, usedPentominoes, currentID, currentRotation);
        }

        //if the block isn't chosen at all
        if(currentID == givenPentominoes.length-1){
          recursive(field, givenPentominoes, usedPentominoes, 0, 0);
        } else {
          recursive(field, givenPentominoes, usedPentominoes, ++currentID, 0);
        }
      }
    }
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
    System.out.println("Mutation " + mutation + " out of " + PentominoDatabase.data[pentID].length);
    System.out.println(Arrays.deepToString(PentominoDatabase.data[pentID]));
    int placeX = 0;
    int placeY = 0;

    //check where the pentomino has to be added
    for(int i=0; i<field.length; i++){
      //go trough every row
      for(int j=0; j<field[i].length; j++){
        //go trough every item in the row
        if(field[i][j] == -1){
          //the next block has to fill this tile
          placeX = j;
          placeY = i;
          System.out.println("PlaceX = " + placeX);
          System.out.println("PlaceY = " + placeY);

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

    //check if it doesn't go over the 'edge'
    int widthLeft = horizontalGridSize-placeX+1;
    int heightLeft = verticalGridSize-placeY+1;

    System.out.println("WidthLeft = " + widthLeft);
    System.out.println("pieceToPlace[0].length = " + pieceToPlace[0].length);
    if (widthLeft >= pieceToPlace[0].length) {
      //it could fit
      possibleToPlace = true;
      System.out.println("Fits width");
    } else {
      possibleToPlace = false;
      System.out.println("Does NOT fit width");
    }

    if (heightLeft >= pieceToPlace.length && possibleToPlace){
      //it could fit
      possibleToPlace = true;
      System.out.println("Fits height");
    } else {
      possibleToPlace = false;
      System.out.println("Does NOT fit height");
    }

    //check if it overlaps
    //pieceToPlace.length+placeX-1 = the width of the piece + the starting point
    int tmpX = 0;
    int tmpY = 0;

    if(possibleToPlace){
      for(int i = placeY; i < pieceToPlace.length+placeY; i++){ // loop over Y position of pentomino
        for (int j = placeX; j < pieceToPlace[0].length+placeX; j++){ // loop over X position of pentomino
          System.out.println("To place: " + pieceToPlace[tmpY][tmpX]);
          System.out.println("tmpY: " + tmpY + " tmpX: " + tmpX);
          System.out.println("Field: " + field[i][j]);
          System.out.println("i: " + i + " j: " + j);
          if(field[i][j] != -1){
            if(pieceToPlace[tmpY][tmpX] != 0){
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

    //TODO [optimization] check if it creates an unfillable hole
    //an unfillable hole would be a hole that is smaller than 5 blocks or that can't be filled with the blocks that are left

    //If there is a possibility to place the piece on the field, do it
    tmpX = 0;
    tmpY = 0;
    boolean firstCellCovered = false;

    if(possibleToPlace){
      for(int i = placeY; i < pieceToPlace.length+placeY; i++){ // loop over Y position of pentomino
        for (int j = placeX; j < pieceToPlace[0].length+placeX; j++){ // loop over X position of pentomino
          //if pieceToPlace actually has a block in that spot, place it on the field
          if(pieceToPlace[tmpY][tmpX] != 0){
            //check if the first piece that you try to fill in is actually being filled
            if(i == placeY && j == placeX){
              firstCellCovered = true;
            }

            if(firstCellCovered){
              System.out.println("If it fits, it sits " + field[i][j] + " " + pieceToPlace[tmpY][tmpX]);
              field[i][j] = pentID;

              //DEBUG
              try{
                Thread.sleep(0);
              } catch (InterruptedException ie){
                //display the field
              }
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
    search();
  }
}
