package Phase1;

import General.PentominoDatabase;

import java.util.Arrays;

public class DLX {
    public static int horizontalGridSize = 5;
    public static int verticalGridSize = 12;
    public static int area=horizontalGridSize*verticalGridSize;
    public static char[] input = {'X','I','Z','T','U','V','W','Y','L','P','N','F'};
    public static UI ui;
    public static int[][] field;

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

    public static void wipeField(int[][] field){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = -1;
            }
        }
    }

    public static void main(String[] args){
        ui = new UI(horizontalGridSize, verticalGridSize, 50);
        field = new int[horizontalGridSize][verticalGridSize];
        int[][]matrix=buildMatrix(field);
        Node header=matToDL(matrix);
    }

    private static Node matToDL(int[][]matrix) {
        Node H=new Node();
        return H;
    }

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

    public static int[][] buildMatrix(int[][] field) {
        int pentN=input.length;
        int[][] matrix = new int[10000][(horizontalGridSize*verticalGridSize)+pentN];
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
                                matrix[row][pentN + onePos[w][0] + (onePos[w][1] * horizontalGridSize)] = pentID+1;
                            }
                            matrix[row][map[pentID]] = pentID+1;
                            wipeField(field);
                            row++;
                        }
                    }
                }
            }
        }

        int[][] matrix1=new int[row][matrix[0].length];
        for (int k = 0; k < row; k++) {
            for (int l = 0; l < matrix[k].length; l++) {
                matrix1[k][l]=matrix[k][l];
            }
        }

        for (int i = 0; i < matrix1.length; i++) {
            System.out.println(Arrays.toString(matrix1[i]));
        }

        return matrix1;
    }
}
