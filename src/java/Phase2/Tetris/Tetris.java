package Phase2.Tetris;
import General.PentominoDatabase;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
    public static Phase2.Tetris.GameWrapper gameWrapper;
    public static boolean canMove=false;
    public static boolean upPressed=false;
    public static boolean downPressed=false;
    public static boolean rightPressed=false;
    public static boolean leftPressed=false;
    public static boolean spacePressed=false;
    public static boolean gameOver=false;
    public static int score = 0;
    public static int nextPiece = (int)(12 * Math.random());
    public static Random rand = new Random(21370);
    public static boolean enableBot = true;
    public static String botType = "Q";
    public static boolean aboutToCollide=false;

    public static void step(){
        if(canMove){
            if(leftPressed) movePiece(false);
            if(rightPressed) movePiece(true);
            if(upPressed) rotatePiece(false);
            if(downPressed) rotatePiece(true);
            if(spacePressed) dropPiece();
        }
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
        curPos[1]=5-(pieceToPlace.length);
        addPiece();
        canMove=true;
    }

    public static void addPiece(){
        int[][] pieceToPlace = PentominoDatabase.data[curPiece][curPieceRotation];
        for(int i = 0; i < pieceToPlace.length; i++){ // loop over x position of pentomino
            for (int j = 0; j < pieceToPlace[i].length; j++){ // loop over y position of pentomino
                if (pieceToPlace[i][j] == 1){
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
                    tempField[curPos[0] + j][curPos[1] + i] = curPiece;
                }
            }
        }
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
            curPiece = nextPiece;
            nextPiece = (int)(12 * Math.random());
            curPieceRotation=0;
    }

    public static void rotatePiece(boolean cw){
        int pieceRotation=curPieceRotation;
        if(cw) {
            if(pieceRotation <4) {
                if (curPieceRotation < 3) {
                    pieceRotation++;
                }
                if (curPieceRotation ==3) {
                    pieceRotation = 0;
                }
            }
            if(pieceRotation>3){
                if(curPieceRotation <7){
                    pieceRotation++;
                }
                if(curPieceRotation ==7){
                    pieceRotation = 4;
                }
            }
        }
        if(!cw) {
            if(pieceRotation <4) {
                if (curPieceRotation > 0) {
                    pieceRotation--;
                }
                if (curPieceRotation ==0) {
                    pieceRotation = 3;
                }
            }
            if(pieceRotation>3){
                if(curPieceRotation >3){
                    pieceRotation--;
                }
                if(curPieceRotation ==4){
                    pieceRotation = 7;
                }
            }
        }
        int[] temPos = arrayCopy(curPos);
        int[][] pieceToPlace = PentominoDatabase.data[curPiece][pieceRotation];
        if(pieceToPlace.length!=pieceToPlace[0].length){
            temPos[0]+=pieceToPlace[0].length-pieceToPlace.length;
        }
        if(temPos[0]<0) temPos[0]=0;
        if(temPos[0]+pieceToPlace[0].length>fieldWidth) temPos[0]=fieldWidth-pieceToPlace[0].length;
        if(!checkCollision(temPos,pieceRotation)){
            curPos=temPos;
            curPieceRotation=pieceRotation;
            tempField=copyField(field);
            addPiece();
        }
    }

    public static void movePiece(boolean right){
        int dir=0;
        if (!right) dir=1;
        int [] temPos=arrayCopy(curPos);
        int[][] pieceToPlace = PentominoDatabase.data[curPiece][curPieceRotation];
        if(right&&curPos[0]+pieceToPlace[0].length<fieldWidth) temPos[0]+=1;
        if(!right&&curPos[0]>0) temPos[0]-=1;

        if(!checkCollision(temPos,curPieceRotation)){
            curPos=temPos;
            tempField=copyField(field);
            addPiece();
        }
    }

    public static boolean checkCollision(int [] nextPos, int nextRot){

        int[][] pieceToPlace = PentominoDatabase.data[curPiece][nextRot];
        if(pieceToPlace.length+nextPos[1]>fieldHeight||nextPos[0]<0||nextPos[0]>fieldWidth-pieceToPlace[0].length) {
            return true;
        } else {
            for (int i = 0; i < pieceToPlace.length; i++) { // loop over x position of pentomino
                for (int j = 0; j < pieceToPlace[i].length; j++) { // loop over y position of pentomino
                    if (pieceToPlace[i][j] == 1){
                        if(field[nextPos[0]+j][nextPos[1] + i]!=-1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void dropPiece(){
        int [] nextPos=arrayCopy(curPos);
        nextPos[1]++;
        while(!checkCollision(nextPos,curPieceRotation)){
            curPos[1]++;
            nextPos[1]++;
            tempField = copyField(field);
            addPiece();
        }
        field=copyField(tempField);
        rowElimination();
        instantiateNewPiece();
        gameWrapper.ui.setState(tempField);
    }

    public static int[] arrayCopy(int [] old){
        int[]n = new int[old.length];
        for(int i=0;i<old.length;i++){
            n[i]=old[i];
        }
        return n;
    }

    public static void movePieceDown(){
        if(!aboutToCollide){
            int [] temPos=arrayCopy(curPos);
            temPos[1] += 1;
            if(!checkCollision(temPos,curPieceRotation)) {
                curPos[1] += 1;
                tempField = copyField(field);
                addPiece();
                temPos[1] += 1;
                if(checkCollision(temPos,curPieceRotation)) aboutToCollide=true;
            } else {
                canMove=false;
                if(curPos[1]<5){
                    gameOver=true;
                    score=0;
                    wipeField(field);
                    wipeField(tempField);
                }
                field=copyField(tempField);
                rowElimination();
                instantiateNewPiece();
                runBot();
            }
            gameWrapper.ui.setState(tempField);
        }
        else aboutToCollide=false;
    }

    public static void rowElimination() {
        int consecutive=0;
        for(int i = field[0].length - 1; i >= 0; i--) {
            int cntr = 0;
            boolean fullRow = false;
            // Count amount of non -1 entries in row i
            for(int j = 0; j < field.length; j++) {
                if (field[j][i] != -1)
                    cntr++;
            }
            // Check for full row
            if (cntr == field.length){
                fullRow = true;
                consecutive++;
                score+=100*consecutive;
            }

            if (fullRow) {
                // Move all rows above full row i down one row
                for(int k = i; k >= 0; k--) {
                    if (k == 0) {
                        for (int m = 0; m < field.length; m++)
                            field[m][k] = -1;
                    }
                    else {
                        for (int m = 0; m < field.length; m++)
                            field[m][k] = field[m][k-1];
                    }
                }
                // This statement is needed for full rows that are stacked on top of each other
                i++;
            }
        }
        gameWrapper.score.setText(gameWrapper.number(score));
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

    public static void runBot(){
        //TODO
        if(enableBot){
            if(botType.equals("Q")){
                //use copyField because of pass by value (otherwise the blocks would become invisible
                Phase2.Tetris.Qbot.genRewards(copyField(Tetris.field), Tetris.fieldHeight, Tetris.fieldWidth);
            }
        }
    }

    public static void main(String[] args){
        fieldWidth = 5;
        fieldHeight = 20;
        gameWrapper = new Phase2.Tetris.GameWrapper(fieldWidth, fieldHeight-5, 50);
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
        timer.schedule(new Phase2.Tetris.GameTimer(), 0, 500);

        //Run the bot
        runBot();
    }
}
