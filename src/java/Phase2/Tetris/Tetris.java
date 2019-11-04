package Phase2.Tetris;
import General.PentominoDatabase;
import Phase1.UI;

import java.util.Arrays;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*TODO
    Implement game class

    Implement board class

    Implement AI class
*/

public class Tetris{
    public static int fieldWidth;
    public static int fieldHeight;
    public static int blocks;
    public static int[][] field;
    public static int[][] tempField;
    private static boolean keys[]=new boolean[65536];
    public static int curPiece;
    public static int curPieceRotation=0;
    public static int curPos[]=new int[2];
    public static GameWrapper gameWrapper;
    public static boolean upPressed=false;
    public static boolean downPressed=false;
    public static boolean rightPressed=false;
    public static boolean leftPressed=false;
    public static boolean spacePressed=false;
    public static boolean gameOver=false;
    public static int score = 0;

    public static void step(){
        if(leftPressed) movePiece(false);
        if(rightPressed) movePiece(true);
        if(upPressed) rotatePiece(false);
        if(downPressed) rotatePiece(true);
        if(spacePressed) dropPiece();
        gameWrapper.ui.setState(tempField);
    }

    public static void wipeField(int[][] field){
        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i],-1);
        }
    }

    public static void instantiateNewPiece(){
        getNewPiece();
        int[][] pieceToPlace = PentominoDatabase.data[curPiece][curPieceRotation];
        curPos[0]=0;
        curPos[1]=4-pieceToPlace[0].length;
        addPiece();
        //printMatrix(pieceToPlace);
    }

    public static void addPiece(){
        int[][] pieceToPlace = PentominoDatabase.data[curPiece][curPieceRotation];
        for(int i = 0; i < pieceToPlace.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < pieceToPlace[i].length; j++) // loop over y position of pentomino
            {
                if (pieceToPlace[i][j] == 1){
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
                    tempField[curPos[0] + j][curPos[1] + i] = curPiece;
                }
            }
        }
        //printMatrix(tempField);
        //System.out.println();
    }

    public static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(1+m[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void getNewPiece(){ //TODO Max randomize the return between 0 and 11
        curPiece = 0;
        curPieceRotation=0; // don't touch!
    }

    public static void rotatePiece(boolean cw){//TODO Lindalee change the pieceRotation variable to the right transformation (check the PentominoDatabase class)
        int pieceRotation=0;
        curPieceRotation=pieceRotation;
    }

    public static void movePiece(boolean right){

    }

    public static boolean checkCollision(int move,int rotation){ //0:right 1:left 2:down
        int[] temPos=new int[2];
        int temRot=curPieceRotation;
        System.arraycopy(curPos, 0, temPos, 0, 2);
        if (move==0) temPos[0]=curPos[0]+1;
        else if (move==1) temPos[0]=curPos[0]-1;
        else if (move==2) temPos[1]=curPos[1]+1;
        if(rotation!=curPieceRotation){
            temPos[0]=curPos[0];
            temRot=rotation;
        }

        int[][] pieceToPlace = PentominoDatabase.data[curPiece][curPieceRotation];
        if(pieceToPlace.length+temPos[1]>fieldHeight||temPos[0]<0||temPos[0]>fieldWidth-pieceToPlace[0].length) {
            return true;
        }else{
            for (int i = 0; i < pieceToPlace.length; i++) { // loop over x position of pentomino
                for (int j = 0; j < pieceToPlace[i].length; j++) { // loop over y position of pentomino
                    /*System.out.println(tempField[temPos[0] +j][temPos[1] + i-1]);
                    System.out.print(temPos[0] +j);
                    System.out.print(" ");
                    System.out.println(temPos[1] + i);*/
                    if (pieceToPlace[i][j] == 1){
                        //System.out.print(tempField[temPos[0] +j][temPos[1] + i-1]);
                        if(field[temPos[0]+j][temPos[1] + i]!=-1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void dropPiece(){//TODO Drago Drop piece to the bottom

    }

    public static void movePieceDown(){
        if(!checkCollision(2,curPieceRotation)) {
            curPos[1] += 1;
            tempField = copyField(field);
            addPiece();
            gameWrapper.ui.setState(tempField);
        } else {
            int[][] pieceToPlace = PentominoDatabase.data[curPiece][curPieceRotation];
            if(curPos[1]+pieceToPlace.length<5){
                gameOver=true;
                wipeField(field);
                wipeField(tempField);
            }
            field=copyField(tempField);
            instantiateNewPiece();
        }
    }

    private void checkRows(){
        //check if there's a full row, if there is use removeRows
    }

    private void removeRows(int row){
        //remove this row, update the score and move all above rows down by the amount of full rows
    }

    private static int[][] copyField(int[][] f0){
        int [][] f1=new int[fieldWidth][fieldHeight];
        for(int i=0;i<fieldWidth;i++){
            for(int j=0;j<fieldHeight;j++){
                f1[i][j]=f0[i][j];
            }
        }
        return f1;
    }

    public static void main(String[] args){
        fieldWidth = 5;
        fieldHeight = 20;
        gameWrapper = new GameWrapper(fieldWidth, fieldHeight-5, 50);
        blocks = 5;
        field = new int[fieldWidth][fieldHeight];
        tempField = new int[fieldWidth][fieldHeight];
        wipeField(field);
        tempField = copyField(field);
        gameWrapper.window.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        leftPressed=true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightPressed=true;
                        break;
                    case KeyEvent.VK_UP:
                        upPressed=true;
                        break;
                    case KeyEvent.VK_DOWN:
                        downPressed=true;
                        break;
                    case KeyEvent.VK_SPACE:
                        spacePressed=true;
                        break;
                }
                step();
            }
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        leftPressed=false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightPressed=false;
                        break;
                    case KeyEvent.VK_UP:
                        upPressed=false;
                        break;
                    case KeyEvent.VK_DOWN:
                        downPressed=false;
                        break;
                    case KeyEvent.VK_SPACE:
                        spacePressed=false;
                        break;
                }
            }
            public void keyTyped(KeyEvent e) {
            }
        });
        instantiateNewPiece();
        Timer timer = new Timer();
        timer.schedule(new GameTimer(), 0, 500);
    }
}
