package Phase2.Tetris;
import java.util.Arrays;

/*TODO
    Implement game class

    Implement board class

    Implement AI class
*/

public class Tetris{
    public static void main(String[] args){
        int fieldWidth = 5;
        int fieldHeight = 15;
        int blocks = 5;
        int[][] field = new int[fieldWidth][fieldHeight];

        //fill field
        for(int i=0; i<blocks; i++){
            field = addBlock(field, fieldWidth, fieldHeight);
        }

        //give scores
        for(int i = 0; i<fieldHeight; i++){
            for(int j = 0; j<fieldWidth; j++){
                int amountOfBlocksSurrounding = 0;
                int amountOfBlocksInRow = 0;

                //only check field that aren't filled (you don't want to put a score in a cell that has already been taken)
                if(field[i][j] != -1){
                    //check surroundings
                    //check same layer, but exclude the cell itself
                    if(j>1){
                        if(field[i][j-1]==-1) amountOfBlocksSurrounding++;
                    }
                    if(j<fieldWidth-1){
                        if(field[i][j+1]==-1) amountOfBlocksSurrounding++;
                    }

                    //check layer below
                    if(i<fieldHeight-1){

                        if(j>1){
                            if(field[i+1][j-1]==-1) amountOfBlocksSurrounding++;
                        }

                        if(field[i+1][j]==-1) amountOfBlocksSurrounding++;

                        if(j<fieldWidth-1){
                            if(field[i+1][j+1]==-1) amountOfBlocksSurrounding++;
                        }
                    }

                    //get the amount of blocks in this row
                    for(int k=0; k<fieldWidth; k++){
                        if(field[i][k] == -1){
                            amountOfBlocksInRow++;
                        }
                    }

                    //update score
                    //multiply the surrounding blocks by the height and add a default 'bonus' based on the layer
                    field[i][j] = amountOfBlocksSurrounding * (5*(i+1)) + 25*(i) + amountOfBlocksInRow * (3*(i) + 3) + 1;
                }

            }
        }

        //print field
        for(int i=0; i<fieldHeight; i++){
            for(int j=0; j<fieldWidth; j++){
                System.out.print(String.format("%4d", field[i][j]));
            }
            System.out.println();
        }

    }

    static public int[][] addBlock(int[][] field, int fieldWidth, int fieldHeight){
        int randomX = (int)(Math.random()*fieldWidth);
        int randomY = (int)(Math.random()*fieldHeight);

        if(field[randomY][randomX] != -1){
            field[randomY][randomX] = -1;
        } else {
            addBlock(field, fieldWidth, fieldHeight);
        }

        return field;
    }

}
