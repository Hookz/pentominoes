package Phase2.Tetris;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Gbot {
    private int movesNumber;
    private int movesLimit;
    private int populationSize;
    private static double[][] genomes=new double[1][5];
    private static int currentGenome=0;
    private int generation;
    private double[] moveParameters;
    private double mutationRate;
    private double mutationStep;
    public static int games=0;
    private static ArrayList<Integer> scores = new ArrayList<Integer>();
    public static int[] bestMoveNext;

    public static int[][] copyField(int[][] f0) {
        int[][] f1 = new int[Tetris.fieldWidth][Tetris.fieldHeight];
        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                f1[i][j] = f0[i][j];
            }
        }
        return f1;
    }

    public static Integer[] arrayCopy(Integer [] old){
        Integer[]n = new Integer[old.length];
        for(int i=0;i<old.length;i++){
            n[i]=old[i];
        }
        return n;
    }

    public static int[] arrayCopy(int[] old){
        int[]n = new int[old.length];
        for(int i=0;i<old.length;i++){
            n[i]=old[i];
        }
        return n;
    }

    //Creates the initial population of genomes, each with random genes TODO: Lindalee
    public static void initPopulation() {
        //double []gen={Math.random(),Math.random(),Math.random(),Math.random(),Math.random(),Math.random()};
        double []gen={0.658,-0.191,-0.460,-0.306,0.023,-0.049,0.35}; //Siraj's weights
        genomes[0]=gen;
    }

    //Evaluates the next individual in the population. If there is none, evolves the population TODO: Lindalee
    private void evalIndividual() {
    }

    //Creates the new population using the best individuals from the last TODO: Lindalee
    private void getNextGen() {
    }

    //Returns a child from 2 individuals TODO: Lindalee
    private void mate() {
    }

    private static int[] getBestMove() throws IOException { //Returns an array of possible moves
        int[][] oldField = copyField(Tetris.field);
        int oldScore=Tetris.score;
        int oldPiece=Tetris.curPiece;
        int oldPieceRotation=Tetris.curPieceRotation;
        ArrayList<Integer[]> possibleMoves= new ArrayList<Integer[]>();

        Integer[] move = new Integer[3]; //rotation,translation,rating
        double[] algorithm = new double[6]; //rowsCleared, weightedHeight, cumulativeHeight, relativeHeight, holes, roughness
        for (int i = 0; i < 4; i++) { //for each possible rotation
            for (int t = 0; t < Tetris.fieldWidth; t++) {
                Tetris.field = copyField(oldField);
                Tetris.score = oldScore;
                Tetris.curPiece = oldPiece;
                Tetris.curPieceRotation = oldPieceRotation;
                for (int k = 0; k < i; k++) Tetris.rotatePiece(true);
                for (int k = 0; k < t; k++) Tetris.movePiece(true);
                int er = Tetris.dropPiece(true);
                int rating = 0;
                if(er==-2){
                    rating-=500;
                    er=0;
                }
                if(er==-1) er=0;

                algorithm[0] = er;
                algorithm[1] = Math.pow(getHeight(), 1.5);
                algorithm[2] = getCumHeight();
                algorithm[3] = getRelHeight();
                algorithm[4] = getHoles();
                algorithm[5] = getRoughness();

                rating+= algorithm[0] * genomes[currentGenome][0];
                rating+= algorithm[1] * genomes[currentGenome][1];
                rating+= algorithm[2] * genomes[currentGenome][2];
                rating+= algorithm[3] * genomes[currentGenome][3];
                rating+= algorithm[4] * genomes[currentGenome][4];
                rating+= algorithm[5] * genomes[currentGenome][5];

                        move[0] = i;
                        move[1] = t;
                        move[2] = rating;
                        possibleMoves.add(arrayCopy(move));
                    }
                }
        Tetris.field=copyField(oldField);
        Tetris.score=oldScore;
        Tetris.curPiece=oldPiece;
        Tetris.curPieceRotation=oldPieceRotation;
        int maxR=-10000;
        int maxMove=0;
        for (int i = 0; i < possibleMoves.size(); i++) {
            if(possibleMoves.get(i)[2]>maxR) {
                maxR=possibleMoves.get(i)[2];
                maxMove=i;
            }
        }

        //System.out.println(Arrays.toString(possibleMoves.get(maxMove)));
        int[] bestMove=new int[3];
        bestMove[0]=possibleMoves.get(maxMove)[0];
        bestMove[1]=possibleMoves.get(maxMove)[1];
        bestMove[2]=possibleMoves.get(maxMove)[2];
        return bestMove;
    }

    private static int[] getBestMove1() throws IOException { //Returns an array of possible moves
        int[][] oldField = copyField(Tetris.field);
        int oldScore=Tetris.score;
        int oldPiece=Tetris.curPiece;
        int oldNextPiece=Tetris.nextPiece;
        int oldPieceRotation=Tetris.curPieceRotation;
        int oldNextPieceRotation = Tetris.nextRot;
        int oldPiecePos[]=arrayCopy(Tetris.curPos);
        ArrayList<Integer[]> possibleMoves= new ArrayList<Integer[]>();

        Integer[] move = new Integer[7]; //rotation,translation,rating
        double[] algorithm = new double[6]; //rowsCleared, weightedHeight, cumulativeHeight, relativeHeight, holes, roughness
        for (int i = 0; i < 4; i++) { //for each possible rotation
            for (int t = 0; t < Tetris.fieldWidth; t++) {
                Tetris.field = copyField(oldField);
                Tetris.score = oldScore;
                Tetris.curPiece = oldPiece;
                Tetris.nextPiece=oldNextPiece;
                Tetris.curPieceRotation = oldPieceRotation;
                Tetris.nextRot = oldNextPieceRotation;
                Tetris.curPos=arrayCopy(oldPiecePos);
                for (int k = 0; k < i; k++) Tetris.rotatePiece(true);
                for (int k = 0; k < t; k++) Tetris.movePiece(true);
                int er = Tetris.dropPiece(true);
                int rating1 = 0;
                if(er==-2){
                    rating1-=500;
                    er=0;
                }
                if(er==-1) er=0;

                algorithm[0] = er;
                algorithm[1] = Math.pow(getHeight(), 1.5);
                algorithm[2] = getCumHeight();
                algorithm[3] = getRelHeight();
                algorithm[4] = getHoles();
                algorithm[5] = getRoughness();
                if(bestMoveNext!=null && i==bestMoveNext[0] && t==bestMoveNext[1]) rating1+=genomes[currentGenome][6];
                rating1 += algorithm[0] * genomes[currentGenome][0];
                rating1 += algorithm[1] * genomes[currentGenome][1];
                rating1 += algorithm[2] * genomes[currentGenome][2];
                rating1 += algorithm[3] * genomes[currentGenome][3];
                rating1 += algorithm[4] * genomes[currentGenome][4];
                rating1 += algorithm[5] * genomes[currentGenome][5];

                int[][] oldField2 = copyField(Tetris.field);
                int oldScore2=Tetris.score;
                int oldPiece2=Tetris.curPiece;
                int oldNextPiece2=Tetris.nextPiece;
                int oldPieceRotation2=Tetris.curPieceRotation;
                int oldNextPieceRotation2 = Tetris.nextRot;
                int oldPiecePos2[]=arrayCopy(Tetris.curPos);

                if ((Tetris.curPos[0]!=oldPiecePos[0]&&Tetris.curPos[1]!=oldPiecePos[1])||Tetris.curPieceRotation!=oldPieceRotation) {
                    for (int j = 0; j < 4; j++) { //for each possible rotation
                        for (int l = 0; l < Tetris.fieldWidth; l++) {
                            Tetris.field = copyField(oldField2);
                            Tetris.score = oldScore2;
                            Tetris.curPiece = Tetris.nextPiece;
                            Tetris.curPieceRotation = Tetris.nextRot;
                            Tetris.curPos = oldPiecePos2;
                            for (int k = 0; k < j; k++) Tetris.rotatePiece(true);
                            for (int k = 0; k < l; k++) Tetris.movePiece(true);
                            er = Tetris.dropPiece(true);
                            int rating2 = 0;
                            if (er == -2) {
                                rating2 -= 500;
                                er = 0;
                            }
                            if (er == -1) er = 0;

                            algorithm[0] = er;
                            algorithm[1] = Math.pow(getHeight(), 1.5);
                            algorithm[2] = getCumHeight();
                            algorithm[3] = getRelHeight();
                            algorithm[4] = getHoles();
                            algorithm[5] = getRoughness();

                            rating2 += algorithm[0] * genomes[currentGenome][0];
                            rating2 += algorithm[1] * genomes[currentGenome][1];
                            rating2 += algorithm[2] * genomes[currentGenome][2];
                            rating2 += algorithm[3] * genomes[currentGenome][3];
                            rating2 += algorithm[4] * genomes[currentGenome][4];
                            rating2 += algorithm[5] * genomes[currentGenome][5];

                            move[0] = i;
                            move[1] = t;
                            move[2] = j;
                            move[3] = l;
                            move[4] = rating1;
                            move[5] = rating2;
                            move[6] = rating1 + rating2;
                            possibleMoves.add(arrayCopy(move));
                        }
                    }
                }
            }
        }
        Tetris.field = copyField(oldField);
        Tetris.score = oldScore;
        Tetris.curPiece = oldPiece;
        Tetris.nextPiece = oldNextPiece;
        Tetris.curPieceRotation = oldPieceRotation;
        Tetris.nextRot = oldNextPieceRotation;
        Tetris.curPos=arrayCopy(oldPiecePos);

        int maxR=-10000;
        int maxMove=0;
        for (int i = 0; i < possibleMoves.size(); i++) {
            if(possibleMoves.get(i)[6]>maxR) {
                maxR=possibleMoves.get(i)[6];
                maxMove=i;
            }
        }

        int[] bestMove=new int[3];
        bestMove[0]=possibleMoves.get(maxMove)[0];
        bestMove[1]=possibleMoves.get(maxMove)[1];
        bestMove[2]=possibleMoves.get(maxMove)[6];

        bestMoveNext=new int[3];
        bestMoveNext[0]=possibleMoves.get(maxMove)[2];
        bestMoveNext[1]=possibleMoves.get(maxMove)[3];
        bestMoveNext[2]=possibleMoves.get(maxMove)[5];
        return bestMove;
    }

    public static void makeMove() throws IOException {
        int[][] oldField = copyField(Tetris.field);
        int oldScore=Tetris.score;
        int oldPiece=Tetris.curPiece;
        int oldPieceRotation=Tetris.curPieceRotation;
        int[]bestMove=getBestMove1();
        for (int i = 0; i < bestMove[0]; i++) Tetris.rotatePiece(true);
        for (int i = 0; i < bestMove[1]; i++) Tetris.movePiece(true);
        int cr=-1;
        while(cr==-1){
            cr=Tetris.movePieceDown(false);
            try {
                Thread.sleep(50);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
        if(cr==-2){
            scores.add(Tetris.lastScore);
            games++;
            //System.out.println(games);
        }
        if(games<100){
            makeMove();
        } else {

            Integer sum = 0;
            if(!scores.isEmpty()) {
                for (Integer mark : scores) {
                    sum += mark;
                }
                System.out.println(sum.doubleValue() / scores.size());
            }
        }
        return;
    }

    private static double getCumHeight() {
        int cumHeight=0;
        int [] l = new int [Tetris.fieldWidth];
        ArrayList<ArrayList<Integer>> l1 = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < Tetris.fieldWidth; i++) l1.add(new ArrayList<Integer>());

        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l1.get(i).add(j);
                }
            }
        }
        for (int k = 0; k < l1.size(); k++) {
            if(l1.get(k).size()>0) l[k]=20-Collections.min(l1.get(k));
            else l[k]=0;
        }
        for(int i=0;i<l.length;i++){
            cumHeight+=l[i];
        }
        return cumHeight;
    }

    private static double getHoles() {
        int holes=0;
        int [] l = new int [Tetris.fieldWidth];
        ArrayList<ArrayList<Integer>> l1 = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < Tetris.fieldWidth; i++) l1.add(new ArrayList<Integer>());

        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l1.get(i).add(j);
                }
            }
        }
        for (int k = 0; k < l1.size(); k++) {
            if(l1.get(k).size()>0) l[k]=Collections.min(l1.get(k));
            else l[k]=20;
        }
        for(int i=0;i<Tetris.fieldWidth;i++){
            for(int j=0;j<Tetris.fieldHeight;j++){
                if(j>l[i]&&Tetris.field[i][j]==-1) holes++;
            }
        }
        return holes;
    }

    private static double getRoughness() {
        int roughness=0;
        int [] l = new int [Tetris.fieldWidth];
        ArrayList<ArrayList<Integer>> l1 = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < Tetris.fieldWidth; i++) l1.add(new ArrayList<Integer>());

        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l1.get(i).add(j);
                }
            }
        }
        for (int k = 0; k < l1.size(); k++) {
            if(l1.get(k).size()>0) l[k]=Collections.min(l1.get(k));
            else l[k]=20;
        }
        for(int i=0;i<l.length-1;i++){
            roughness+=Math.abs(l[i]-l[i+1]);
        }
        return roughness;
    }

    private static double getRelHeight() {
        ArrayList<Integer> l = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> l1 = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < Tetris.fieldWidth; i++){
            l1.add(new ArrayList<Integer>());
            l.add(20);
        }

        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l1.get(i).add(j);
                }
            }
        }
        for (int k = 0; k < l1.size(); k++) {
            if(l1.get(k).size()>0) l.set(k,Collections.min(l1.get(k)));
            else l.set(k,20);
        }
        return (int)(Collections.max(l)-Collections.min(l));
    }

    private static double getHeight() { //TODO: Sam
        ArrayList<Integer> l=new ArrayList<Integer>();
        l.add(0);
        for(int i=0;i<Tetris.fieldWidth;i++){
            for(int j=0;j<Tetris.fieldHeight;j++){
                if(Tetris.field[i][j]!=-1) l.add(20-j);
            }
        }
        return (int)Collections.max(l);
    }
}
