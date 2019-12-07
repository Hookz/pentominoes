package Tetris;

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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;

public class Tetris{
    public static boolean enableBot = false;
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
    public static Random rand = new Random(89832);
    public static int nextPiece;
    public static int nextRot;
    public static boolean start = true;
    public static boolean aboutToCollide=false;
    public static boolean collided=false;
    public static boolean AI=false;
    public static Timer timer;
    public static boolean training=false;
    private static int writerIterator = 0;
    private static BufferedWriter writer;

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

    public static void wipeField(int[][] field){
        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i],-1);
        }
    }

    public static void instantiateNewPiece(boolean ten){
        curPos[0]=0;
        curPos[1]=0;
        if(!ten){
            getNewPiece();
        }
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
        System.out.println();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(1+m[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

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

    //cw = clock wise
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

    public static boolean movePiece(boolean right){//returns true if moved
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
    public static int dropPiece(boolean ten) {
        int cr=0;
        int [] nextPos=curPos.clone();;
        nextPos[1]++;
        while(!checkCollision(nextPos,curPieceRotation)){
            curPos[1]++;
            nextPos[1]++;
            tempField = copyField(field);
            addPiece();
        }
        cr=movePieceDown(ten);
        return cr;
    }

    public static int[] arrayCopy(int [] old){
        int[]n = new int[old.length];
        for(int i=0;i<old.length;i++){
            n[i]=old[i];
        }
        return n;
    }

    /***
     * Step function slowly moving piece down
     */
    public static int movePieceDown(boolean ten) { //ten is true when the piece gets moved down tentatively therefore the UI doesn't update and we don't call gameOver
        int cr=-1;
        if(!aboutToCollide){
            int [] temPos=curPos.clone();;
            temPos[1] += 1;
            if(!checkCollision(temPos,curPieceRotation)) {
                curPos[1] += 1;
                tempField = copyField(field);
                addPiece();
                temPos[1] += 1;
                if(checkCollision(temPos,curPieceRotation)) aboutToCollide=true;
            } else {
                canMove=false;
                collided = true;
                if(curPos[1]<5){
                    if(!ten) gameOver();
                    cr=-2;
                } else {
                    field=copyField(tempField);
                    cr=rowElimination(ten);
                    //runBot();
                }
                instantiateNewPiece(ten);
            }
            if(!ten&&!training){
                gameWrapper.ui.setState(tempField);
            }
        }
        else aboutToCollide=false;
        return cr;
    }

    /***
     * Function eliminating all rows that are full and updating the score accordingly
     */
    public static int rowElimination(boolean ten) {
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
        if(!ten && !training) gameWrapper.score.setText(gameWrapper.number(score));
        return consecutive;
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

    /***
     * Starts the execution of the bot, if selected to be enabled
     */
    public static void runBot() {

        if(enableBot){
            if(botType=="G") {
//                Phase2.Tetris.Gbot.train();
                Phase2.Tetris.Gbot.Play();
            }
            if(botType.equals("Q")){
                //use copyField because of pass by value (otherwise the blocks would become invisible
                //TODO why is it not using V2?
                //Qbot.findBestPlaceToPlace();
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //initialize field and tempField
        field = new int[fieldWidth][fieldHeight];
        tempField = new int[fieldWidth][fieldHeight];
        wipeField(field);
        tempField = copyField(field);

        //initialize and open the GUI


        //we instantiate a new piece, "start=true" has the function generate 2 pieces instead of 1
        start = true;
        instantiateNewPiece(false);

        //initiate the timer for the game
        timer = new Timer();
        timer.schedule(new GameTimer(), 0, 500);

        gameWrapper = new GameWrapper(fieldWidth, fieldHeight-hiddenRows, 50);

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
        //runBot();
    }
}