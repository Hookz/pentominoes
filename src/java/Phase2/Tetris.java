package Phase2;

/*
I had to use

--module-path "C:\Program Files\Java\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml

for configurations -> VM options for both programs

to get javaFX working

JavaFX is used for the audio
 */

import General.PentominoDatabase;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

/**
 * Main class used for running the Tetris (Pentris) environment.
 */
public class Tetris{
    public static boolean enableBot = true;
    public static String botType = "G";

    public static int fieldWidth=5;
    public static int fieldHeight=20;
    public static final int hiddenRows=5;

    public static int[][] field;
    public static int[][] tempField;
    private static boolean keys[]=new boolean[65536];
    public static int curPiece;
    public static int curPieceRotation=0;
    public static int curPos[]=new int[2];
    public static GameWrapper gameWrapper;
    public static boolean canMove=false;
    public static boolean upPressed=false;
    public static boolean downPressed=false;
    public static boolean rightPressed=false;
    public static boolean leftPressed=false;
    public static boolean spacePressed=false;
    public static boolean go=false;
    public static int score = 0;
    public static int lastScore = 0;
    public static int seed = (int)(Math.random()*10000);
    public static Random rand = new Random(seed);
    public static int nextPiece;
    public static int nextRot;
    public static boolean start = true;
    public static boolean aboutToCollide=false;
    public static boolean collided=false;
    public static Timer timer;
    public static boolean training=false;
    private static int writerIterator = 0;
    private static BufferedWriter writer;
    private static boolean done = false;
    public static boolean gmsdone = false;

    public static final Object waitingObject = new Object();

    /**
     * Method performing steps of the game
     * @throws IOException
     */
    public static void step() throws IOException {
        if(canMove){
            if(leftPressed) movePiece(false);
            if(rightPressed) movePiece(true);
            if(upPressed) rotatePiece(false);
            if(downPressed) rotatePiece(true);
            if(spacePressed) dropPiece(false);
        }
        if(!training)gameWrapper.ui.setState(tempField);
        int[] temPos=curPos.clone();;
        temPos[1] += 1;
        if(checkCollision(temPos,curPieceRotation)&&collided){
            collided=false;
            aboutToCollide=true;
        }
    }

    /**
     * Method clearing out the field
     * @param field: field to be wiped (all field values reassigned to -1)
     */
    public static void wipeField(int[][] field){
        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i],-1);
        }
    }

    /**
     * Method instantiating a new piece
     * @param tentative: parameter defining if the action is performed tentatively
     */
    public static void instantiateNewPiece(boolean tentative){
        curPos[0]=0;
        curPos[1]=0;
        if(!tentative){
            getNewPiece();
        }
        addPiece();
        canMove=true;
    }

    /**
     * Method adding the current piece to the temporary field "tempField"
     */
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

    /**
     * Method printing matrix to command line for debugging
     * @param m: matrix to be printed to command line
     */
    public static void printMatrix(int[][] m) {
        System.out.println();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(1+m[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Method generating a new piece
     */
    public static void getNewPiece(){
        if (start) {
            curPiece = (int)(12 * rand.nextDouble());
            curPieceRotation=(int)(rand.nextDouble()*PentominoDatabase.data[curPiece].length);
            start = false;
        } else {
            curPiece=nextPiece;
            curPieceRotation=nextRot;
        }
        nextPiece=(int)(12 * rand.nextDouble());
        nextRot=(int)(rand.nextDouble()*PentominoDatabase.data[curPiece].length);
    }

    /**
     * Method executed in case of gameOver, resetting all parameters
     */
    public static void gameOver() {
        try {
            writer = new BufferedWriter(new FileWriter("scores.csv",true));
            writer.write(++writerIterator+","+score+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        go=true;
        start=true;
        lastScore=score;
        score=0;
        wipeField(field);
        wipeField(tempField);
    }

    /**
     * Method for rotating the current game piece
     * @param cw: parameter true when rotating clockwise and false when rotating counter-clockwise
     */
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
        int[] temPos = curPos.clone();;
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

    /**
     * Method for moving piece left / right
     * @param right: true when moving right, false when moving left
     * @return true if piece was moved, false otherwise
     */
    public static boolean movePiece(boolean right){
        int dir=0;
        if (!right) dir=1;
        int [] temPos=curPos.clone();
        int[][] pieceToPlace = PentominoDatabase.data[curPiece][curPieceRotation];
        if(right){
            if(curPos[0]+pieceToPlace[0].length<fieldWidth){
                temPos[0]+=1;
            } else return false;
        }
        if(!right){
            if(curPos[0]>0){
                temPos[0]-=1;
            } else return false;
        }
        if(!checkCollision(temPos,curPieceRotation)){
            curPos=temPos;
            tempField=copyField(field);
            addPiece();
        } else return false;
        return true;
    }

    /***
     * Function checking if a collision is going to occur
     * @param nextPos: next position of current piece
     * @param nextRot: next rotation of current piece
     * @return true if collision occurs, else false
     */
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

    /***
     * Drops the current piece to the lowest level in current position x
     */
    public static int dropPiece(boolean tentative) {
        int clearedRows=0;
        int [] nextPos=curPos.clone();;
        nextPos[1]++;
        while(!checkCollision(nextPos,curPieceRotation)){
            curPos[1]++;
            nextPos[1]++;
            tempField = copyField(field);
            addPiece();
        }
        clearedRows=movePieceDown(tentative);
        return clearedRows;
    }

    /**
     * Method for copying arrays
     * @param old: old array
     * @return new copy of the old array
     */
    public static int[] arrayCopy(int [] old){
        int[]n = new int[old.length];
        for(int i=0;i<old.length;i++){
            n[i]=old[i];
        }
        return n;
    }

    /**
     * Method checking how many rows get cleared when moving the piece down
     * @param tentative: true when piece moved down tentatively (without UI updates), false otherwise
     * @return number of consecutively removed rows
     */
    public static int movePieceDown(boolean tentative) {
        int clearedRows=-1;
        if(!aboutToCollide){
            int [] temPos=curPos.clone();;
            temPos[1] += 1;
            if(!checkCollision(temPos, curPieceRotation)) {
                curPos[1] += 1;
                tempField = copyField(field);
                addPiece();
                temPos[1] += 1;
                if(checkCollision(temPos, curPieceRotation)) aboutToCollide=true;
            } else {
                canMove=false;
                collided = true;
                if(curPos[1]<5){
                    if(!tentative) gameOver();
                    clearedRows=-2;
                } else {
                    field=copyField(tempField);
                    clearedRows=rowElimination(tentative);
                    //runBot();
                }
                instantiateNewPiece(tentative);
            }
            if(!tentative&&!training){
                gameWrapper.ui.setState(tempField);
            }
        }
        else aboutToCollide=false;
        return clearedRows;
    }

    /**
     * Method eliminating all rows that are full and updating the score accordingly
     * @param tentative: true when row eliminated tentatively, false otherwise
     * @return number of consecutively removed rows
     */
    public static int rowElimination(boolean tentative) {
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
        if(!tentative && !training) gameWrapper.score.setText(gameWrapper.number(score));
        return consecutive;
    }

    /**
     * Method for copying field
     * @param f0: field to be copied
     * @return a copy of field f0
     */
    public static int[][] copyField(int[][] f0){
        int [][] f1=new int[fieldWidth][fieldHeight];
        for(int i=0;i<fieldWidth;i++){
            for(int j=0;j<fieldHeight;j++){
                f1[i][j]=f0[i][j];
            }
        }
        return f1;
    }

    /**
     * Starts the execution of the bot, if selected to be enabled
     * @throws InterruptedException
     */
    public static void runBot() throws InterruptedException {

        if(enableBot){
            if(botType.equals("G")) {
//                Phase2.Tetris.Gbot.train();
                Gbot.Play();
            }
            if(botType.equals("Q")){
                //use copyField because of pass by value (otherwise the blocks would become invisible
                try {
                    while(!done) {
                        Qbot.run();
                        Thread.sleep(1000);
                    }
                }
                catch (IOException exception) {System.out.println("Error: input/output exception");}
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GameModeSelector.createWindow();
        synchronized (waitingObject){
            while(!gmsdone){
                try{
                    waitingObject.wait();
                }
                catch (InterruptedException e) {
                    // treat interrupt as exit request
                    break;
                }
            }
        }
        //initialize field and tempField
        field = new int[fieldWidth][fieldHeight];
        tempField = new int[fieldWidth][fieldHeight];
        wipeField(field);
        tempField = copyField(field);
        gameWrapper = new GameWrapper(fieldWidth, fieldHeight-hiddenRows, 50);

        //initialize and open the GUI


        //we instantiate a new piece, "start=true" has the function generate 2 pieces instead of 1
        start = true;
        instantiateNewPiece(false);

        //initiate the timer for the game
        timer = new Timer();
        timer.schedule(new GameTimer(), 0, 500);

        //initialize key listeners
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
                try {
                    step();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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


        //Run the bot
        runBot();
    }
}