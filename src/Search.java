import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



public class Search
{
	public static int horizontalGridSize = 12;
	public static int verticalGridSize = 5;
	public static int area=horizontalGridSize*verticalGridSize;
	public static ArrayList<ArrayList<Integer>> supMat;
	public static char[] input = {'P','X','F','V','W','Y','T','Z','U','N','L','I'};
	public static ArrayList<ArrayList<Boolean>> solRows = new ArrayList<ArrayList<Boolean>>();
	public static ArrayList<String> tempArr= new ArrayList<>();
	public static ArrayList<String> solArr= new ArrayList<>();
	// Static UI class to display the board
	public static UI ui;
	public static boolean flag=false;
	public static int chosenAlgorithm = 1;
	public static boolean lpdone = false;

	public static void createWindow(){
		ui = new UI(horizontalGridSize, verticalGridSize, 50);
		LandingPage.startWindow.dispose();
		search();
	}
	// Helper function which starts the brute force algorithm
	public static void search() {
		// Initialize an empty board
		int[][] field = new int[horizontalGridSize][verticalGridSize];
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
			ArrayList<ArrayList<Boolean>> matrix = buildMatrix(field);
			algorithmX(matrix, new ArrayList<ArrayList<Integer>>());

			tempArr = new ArrayList<>();
			solArr = new ArrayList<>();

		/*System.out.println("supmat");
		for (int i = 0; i < supMat.size(); i++) {
			System.out.println(Arrays.toString(supMat.get(i).toArray()));
		}
		System.out.println();*/
			//supMat.remove(0);

			for (int i = 0; i < supMat.get(0).get(0); i++) {
				tempArr.add("" + i);
			}

			System.out.println();
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
				int col = -2;
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

			ui.setState(field);
		}
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



	// Adds a pentomino to the position on the field (overriding current board at that position)
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

	//takes the pentomino character and outputs the unique integer ID for it
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

	//TO DO
	public static ArrayList<ArrayList<Node>> arrToDL(ArrayList<ArrayList<Boolean>> matrix){
		Node header = new Node();
		ArrayList<ArrayList<Node>> DLLMatrix=new ArrayList<ArrayList<Node>>();
		return DLLMatrix;
	}

	// Adds a pentomino to the position on the field (overriding current board at that position)
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

	public static int algorithmX(ArrayList<ArrayList<Boolean>> matrix, ArrayList<ArrayList<Integer>> suppMat){

		/*System.out.println("2");
		for (int i = 0; i < matrix.size(); i++) {
			System.out.println(Arrays.toString(matrix.get(i).toArray()));
		}
		System.out.println();*/

		int minC=1000000;
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



	//takes the possibilities matrix and outputs a solution for that matrix
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

		/*System.out.println("2");
		for (int i = 0; i < matrix1.size(); i++) {
			System.out.println(Arrays.toString(matrix.get(i).toArray()));
		}
		System.out.println();
		System.out.println(Arrays.toString(rowDel.toArray()));*/
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
		/*System.out.println("2");
		for (int i = 0; i < matrix.size(); i++) {
			System.out.println(Arrays.toString(matrix.get(i).toArray()));
		}
		System.out.println();*/

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

		/*System.out.println("s0");
		for (int i = 0; i < suppMat.size(); i++) {
			System.out.println(Arrays.toString(suppMat.get(i).toArray()));
		}
		System.out.println();*/

		suppMat1.add(sm);
		return algorithmX(matrix1,suppMat1);
	}

	public static void deleteRow(int index, ArrayList<ArrayList<Boolean>> m){
		m.remove(index);
	}

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

	//returns a matrix with all possible positions for all pentominoes in the grid
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

	// Main function. Needs to be executed to start the brute force algorithm
	public static void main(String[] args)
	{
		LandingPage.createWindow();
		while(!lpdone){
			System.out.print("i");
		}
		System.out.println("Done!");
		createWindow();
	}
}
