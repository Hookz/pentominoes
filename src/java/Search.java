import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * Class starting the execution of the main program
 */
public class Search
{
	/**
	 * It holds the value of the horizontal size of the grid
	 */
	public static int horizontalGridSize = 6;
	/**
	 * It holds the value of the vertical size of the grid
	 */
	public static int verticalGridSize = 5;
	/**
	 * It holds the value of the area of the grid (horizontal size * vertical size)
	 */
	public static int area=horizontalGridSize*verticalGridSize;
	/**
	 * Input values for the search() method
	 */
	public static char[] input = {'P','X','F','V','W','Y','T','Z','U','N','L','I'};
	public static ArrayList<ArrayList<Integer>> supMat=new ArrayList<ArrayList<Integer>>();
	public static ArrayList<ArrayList<Boolean>> solRows = new ArrayList<ArrayList<Boolean>>();
	public static ArrayList<String> tempArr= new ArrayList<>();
	public static ArrayList<String> solArr= new ArrayList<>();
	/**
	 * Static UI class to display the board
 	 */
	public static UI ui;
	/**
	 * Flag checking if a solution has already been found
	 */
	public static boolean flag=false;
	/**
	 * A flag that holds the value that corresponds to the algorithm used to solve the puzzle
	 */
	public static int chosenAlgorithm = 1;
	/**
	 * Flag checking if the LandingPage and ChoosePieces classes have already been terminated.
	 * <br>
	 * Until it is the case, the Search class waits for their termination.
	 */
	public static boolean lpdone = false;

	/**
	 * A method responsible for creating the window with the game solver, that is being displayed after all settings are inputted by the user
	 */
	public static void createWindow(){
		ui = new UI(horizontalGridSize, verticalGridSize, 50);
		LandingPage.startWindow.dispose();
		search();
	}

	/**
	 * Helper function that starts the solving process with the user-chosen algorithm
	 */
	public static void search() {
		System.out.println(horizontalGridSize);
		System.out.println(verticalGridSize);
		System.out.println(Arrays.toString(input));
		// Initialize an empty board
		int[][] field = new int[horizontalGridSize][verticalGridSize];
		int[][] solutionField = new int[horizontalGridSize][verticalGridSize];
		wipeField(field);

		if (input.length != horizontalGridSize*verticalGridSize / 5) {
			System.out.println("Not possible to find a solution");
			return;
		}

		//do the bruteforce
		if(chosenAlgorithm==0){
			bruteForce(field);
		}

		//do AlgorithmX
		if(chosenAlgorithm==1) {
			//create matrix from possibilities
			ArrayList<ArrayList<Boolean>> matrix = buildMatrix(field);
			//execute order 66
			algorithmX(matrix, supMat);
			//process the support matrix to get the solution out
			if(supMat.size()>0){
				solutionField=processSolution(matrix,field);
				UI ui = new UI(horizontalGridSize, verticalGridSize, 50);
				ui.setState(solutionField);
			} else {
				System.out.println("no solution for this matrix");
			}
		}
	}

	private static int[][] processSolution(ArrayList<ArrayList<Boolean>> matrix, int[][] field){
		for (int i = 0; i < supMat.get(0).get(0); i++) {
			tempArr.add("" + i);
		}

		for (int i = 0; i < supMat.size(); i++) {
			solArr.add(tempArr.get(supMat.get(i).get(1)));
			for (int j = supMat.get(i).size() - 1; j > 1; j--) {
				tempArr.remove((int) supMat.get(i).get(j));
			}
		}

		for (int i = 0; i < solArr.size(); i++) {
			solRows.add(matrix.get(Integer.parseInt(solArr.get(i))));
		}
		wipeField(field);

		for (int i = 0; i < solRows.size(); i++) {
			int col = -1;
			for (int j = 0; j < input.length; j++) {
				if (solRows.get(i).get(j)) col = characterToID(input[j]);
			}
			for (int j = input.length; j < solRows.get(0).size(); j++) {
				if (solRows.get(i).get(j)) {
					int n = j - input.length;
					int x = n % horizontalGridSize;
					int y = (int) ((n / horizontalGridSize));
					field[x][y] = col;
				}
			}
		}
		return field;
	}

	/**
	 * Attempts to solve the puzzle by randomly placing game pieces on board
	 * @param field: the game field
	 */
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
					bruteForceAddPiece(field, pieceToPlace, pentID, x, y);
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


	/**
	 * Adds a pentomino to the position on the field (overriding current board at that position)
	 * @param field the field to put the game piece on
	 * @param piece array holding the currently considered piece's state
	 * @param pieceID the ID of piece currently looked at
	 * @param x helper variable keeping track of the position in the field array
	 * @param y helper variable keeping track of the position in the field array
 	 */
	private static void bruteForceAddPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
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

	/**
	 *
	 * @param character: the character to be converted
	 * @return the numeric representation of pentomino
	 */
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

	/**
	 * Adds a pentomino to the position on the field (overriding current board at that position) - non-burte-force method
	 * @param field the field to put the game piece on
	 * @param piece array holding the currently considered piece's state
	 * @param pieceID the ID of piece currently looked at
	 * @param x helper variable keeping track of the position in the field array
	 * @param y helper variable keeping track of the position in the field array
	 */
	public static int[][] addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
		int[][] matrix = new int[5][2];
		int n=0;
		for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
		{
			for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
			{
				if (piece[i][j] == 1)
				{
					// Add the ID of the pentomino to the board if the pentomino occupies this square
					field[x + i][y + j] = pieceID;
					matrix[n][0]=x + i;
					matrix[n][1]=y + j;
					n++;
				}
			}
		}

		return matrix;
	}

	/**
	 * Turns the pentomino problem into an exact cover problem and solves it by iterating over the matrix of possible position of any pentomino
	 */
	public static int algorithmX(ArrayList<ArrayList<Boolean>> matrix, ArrayList<ArrayList<Integer>> suppMat){
		int minC=12*30*8;
		int sumC=0;
		int indC=0;
		int n=0;
		int smallSum=0;
		int b=0;

		if(matrix.size()==0||matrix.get(0).size()==1){
			supMat=suppMat;
			if(supMat.size()==(horizontalGridSize*verticalGridSize)/5)flag = true;
			return 0;
		} else {
			//else look for the min sum column, "indC" is the index of this column
			for (int i = 0; i < matrix.get(0).size(); i++) {
				n = 0;
				for (int j = 0; j < matrix.size(); j++) {
					if (matrix.get(j).get(i)) {
						sumC++;
					}
					n++;
				}
				if (sumC < minC) {
					minC = sumC;
					indC = i;
				}
				sumC = 0;
			}

			if (minC == 0) {
				return 1;
			} else {
				//for every row where indC has a 1
				b=0;
				if(!flag){
					for (int r = 1; r < matrix.size() ; r++) {
						if (matrix.get(r).get(indC)) {
							b+=algorithmX(matrix,r,indC,suppMat);
						}
					}
				}
				return b;
			}
		}
	}


	/**
	 * Takes the possibilities matrix and outputs a solution for that matrix
	 * @param matrix the possibilities matrix
	 * @param row currently considered row
	 * @param col currently considered column
	 * @param suppMat support matrix
	 * @return integer
	 */
	public static int algorithmX(ArrayList<ArrayList<Boolean>> matrix,int row,int col, ArrayList<ArrayList<Integer>> suppMat) {

		ArrayList<Integer> rowDel = new ArrayList<>();
		ArrayList<Integer> colDel = new ArrayList<>();
		rowDel = new ArrayList<>();
		colDel = new ArrayList<>();
		//for every row where indC has a 1
		ArrayList<ArrayList<Boolean>> matrix1 = new ArrayList<ArrayList<Boolean>>();
		for (int i = 0; i < matrix.size(); i++) {
			ArrayList<Boolean> n = new ArrayList<>();
			for (int j = 0; j < matrix.get(i).size(); j++) {
				n.add(matrix.get(i).get(j));
			}
			matrix1.add(n);
		}

		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(0).size(); j++) {
				if(matrix.get(i).get(j)) {
					if (i==row&&!colDel.contains(j)) {
						colDel.add(j);
					}
					if (matrix.get(row).get(j)&&!rowDel.contains(i)) {
						rowDel.add(i);
					}
				}
			}
		}

		if(rowDel.size()==matrix1.size()&&colDel.size()!=matrix1.get(0).size()){
			matrix1 = new ArrayList<ArrayList<Boolean>>();
			matrix1.add(new ArrayList<>(Arrays.asList(false,true)));
		} else {
			for (int i = rowDel.size() - 1; i > -1; i--) {
				int rowN = rowDel.get(i);
				deleteRow(rowN, matrix1);
			}

			for (int i = colDel.size() - 1; i > -1; i--) {
				int colN = colDel.get(i);
				deleteColumn(colN, matrix1);
			}
		}

		ArrayList<Integer> sm=new ArrayList<Integer>();
		sm.add(matrix.size());
		sm.add(row);
		for (int i = 0; i < rowDel.size(); i++) {
			sm.add(rowDel.get(i));
		}

		ArrayList<ArrayList<Integer>> suppMat1 = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < suppMat.size(); i++) {
			ArrayList<Integer> n = new ArrayList<>();
			for (int j = 0; j < suppMat.get(i).size(); j++) {
				n.add(suppMat.get(i).get(j));
			}
			suppMat1.add(n);
		}

		suppMat1.add(sm);
		return algorithmX(matrix1,suppMat1);
	}

	/**
	 *
	 * @param index: index of the row to be deleted
	 * @param m: the array to delete the row from
	 */
	public static void deleteRow(int index, ArrayList<ArrayList<Boolean>> m){
		m.remove(index);
	}

	/**
	 *
	 * @param index: index of the column to be deleted
	 * @param m: the array to delete the column from
	 */
	public static void deleteColumn(int index, ArrayList<ArrayList<Boolean>> m){
		for (int i = 0; i < m.size(); i++) {
			m.get(i).remove(index);
		}
	}

	//sets all squares in the field to -1
	public static void wipeField(int[][] field){
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j] = -1;
			}
		}
	}

	/**
	 * Builds the matrix
	 * @param field: the field to build the base matrix for
	 * @return matrix with all possible placements of all chosen pentominoes
	 */
	public static ArrayList<ArrayList<Boolean>> buildMatrix(int[][] field) {
		int pentN=input.length;
		boolean[][] matrix = new boolean[10000][(horizontalGridSize*verticalGridSize)+pentN];
		int[][] onePos;
		int pentID = 0;
		int mutation = 0;
		int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
		int x, y = 0;
		int row = 0;
		int n=0;
		//for every pentomino
		int[]map=new int[12];
		for (int i = 0; i < input.length; i++) {
			map[characterToID(input[i])]=n;
			n++;
		}

		for (int i = 0; i < input.length; i++) {
			pentID = characterToID(input[i]);
			//for every mutation
			for (int j = 0; j < PentominoDatabase.data[pentID].length; j++) {
				mutation = j;
				pieceToPlace = PentominoDatabase.data[pentID][mutation];
				wipeField(field);
				for (int k = 0; k < field.length; k++) {
					for (int l = 0; l < field[k].length; l++) {
						x = k;
						y = l;
						if (horizontalGridSize < pieceToPlace.length || x > horizontalGridSize - pieceToPlace.length) {
							//this particular rotation of the piece is too long for the field
							x = -1;
						} else if (horizontalGridSize == pieceToPlace.length) {
							//this particular rotation of the piece fits perfectly into the width of the field
							x = 0;
						}

						if (verticalGridSize < pieceToPlace[0].length || y > verticalGridSize - pieceToPlace[0].length) {
							//this particular rotation of the piece is too high for the field
							y = -1;
						} else if (verticalGridSize == pieceToPlace[0].length) {
							//this particular rotation of the piece fits perfectly into the height of the field
							y = 0;
						}

						//If there is a possibility to place the piece on the field, do it
						if (x >= 0 && y >= 0) {
							onePos = addPiece(field, pieceToPlace, pentID, x, y);
							for (int w = 0; w < onePos.length; w++) {
								matrix[row][pentN + onePos[w][0] + (onePos[w][1] * horizontalGridSize)] = true;
							}
							matrix[row][map[pentID]] = true;
							wipeField(field);
							row++;
						}
					}
				}
			}
		}
		/*System.out.println("matrix");
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
		System.out.println();
		System.out.println();
		System.out.println();*/
		Boolean[][] matrix1=new Boolean[matrix.length][matrix[0].length];
		for (int k = 0; k < matrix.length; k++) {
			for (int l = 0; l < matrix[k].length; l++) {
				matrix1[k][l]=matrix[k][l];
			}
		}

		ArrayList<ArrayList<Boolean>> list= new ArrayList<>();
		for (int i = 0; i < row; i++) {
			ArrayList<Boolean> arl = new ArrayList<>(Arrays.asList(matrix1[i]));
			list.add(arl);
		}

		return list;
	}

	/**
	 * Main function. Needs to be executed to start any algorithm
	 * @param args default parameter, not used by the program
	 */
	public static void main(String[] args)
	{
		LandingPage.createWindow();
		while(!lpdone){
			System.out.print("");
		}
		System.out.println("Done!");
		createWindow();
	}
}
