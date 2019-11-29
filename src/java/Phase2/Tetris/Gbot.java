package Phase2.Tetris;

import General.PentominoDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Gbot {

    private double score;
    private double speed;
    private int movesNumber;
    private int movesLimit;
    private int populationSize=50;
    private static double[][] genomes=new double[1][7];
    private int elites=10;
    private static int currentGenome=0;
    private int generation;
    private double[] moveParameters;
    private double mutationRate;
    private double mutationStep;


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

    //Creates the initial population of genomes, each with random genes TO DO: Lindalee
    public  void initPopulation() {
        for(int i = 0; i<populationSize ; i ++) {
            double[] gen = {Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5, Math.random() * 0.5, Math.random() - 0.5,Math.random()-0.5,0};
            //double []gen={0.658,-0.191,-0.460,-0.306,0.023,-0.049}; //Siraj's weights
            genomes[i] = gen;
        }
        evalIndividual();
    }

    //Evaluates the next individual in the population. If there is none, evolves the population TO DO: Lindalee
    private void evalIndividual() {
        currentGenome++;
        if(currentGenome == genomes.length){
            getNextGen();
        }
        movesNumber = 0;
        makeMove();
    }

    //Creates the new population using the best individuals from the last TO DO: Lindalee
    private void getNextGen() {
        generation++;
        geneSort(genomes);


    }

    //Returns a child from 2 individuals TO DO: Lindalee
    private double[] mate(int mom , int dad ) {
        int crossover = (int)Math.random()*5;
        double[] child = new double[7];
        for(int i = 0; i < crossover;i++){
            child[i] = genomes[dad][i];
        }
        for(int i = crossover; i < child.length -1;i++){
            child[i] = genomes[mom][i];
        }
        child[7]= 0;

        for(int i = 0;i < child.length-1; i++) {
            if (Math.random() < mutationRate) {
                child[i] = child[i] + Math.random() * mutationStep * 2 - mutationStep;
            }
        }
        return child;
    }

    private static int[] getBestMove() { //Returns an array of possible moves TODO: Sam
        int[][] oldField = copyField(Tetris.field);
        int oldScore=Tetris.score;
        int oldPiece=Tetris.curPiece;
        int oldPieceRotation=Tetris.curPieceRotation;
        ArrayList<Integer[]> possibleMoves= new ArrayList<Integer[]>();
        int curPiece = Tetris.curPiece;
        int curPieceRotation = Tetris.curPieceRotation;
        Integer[] move = new Integer[3]; //rotation,translation,rating
        double[] algorithm = new double[6]; //rowsCleared, weightedHeight, cumulativeHeight, relativeHeight, holes, roughness
        for (int i = 0; i < PentominoDatabase.data[Tetris.curPiece].length; i++) { //for each possible rotation
            int[][] ptp = PentominoDatabase.data[Tetris.curPiece][i];
            for (int t = 0; t < Tetris.fieldWidth; t++) {
                Tetris.field = copyField(oldField);
                Tetris.score=oldScore;
                Tetris.curPiece=oldPiece;
                Tetris.curPieceRotation=oldPieceRotation;
                for (int k = 0; k < i; k++) Tetris.rotatePiece(true);
                if (t > 0) {
                    for (int k = 0; k < t; k++) Tetris.movePiece(true);
                }
                int er = Tetris.dropPiece(true);
                algorithm[0] = er;
                algorithm[1] = Math.pow(getHeight(), 1.5);
                algorithm[2] = getCumHeight();
                algorithm[3] = getRelHeight();
                algorithm[4] = getHoles();
                algorithm[5] = getRoughness();
                int rating = 0;

                rating+=algorithm[0]*genomes[currentGenome][0];
                rating+=algorithm[1]*genomes[currentGenome][1];
                rating+=algorithm[2]*genomes[currentGenome][2];
                rating+=algorithm[3]*genomes[currentGenome][3];
                rating+=algorithm[4]*genomes[currentGenome][4];
                rating+=algorithm[5]*genomes[currentGenome][5];
                move[0]=i;
                move[1]=t;
                move[2]=rating;
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

    public static void makeMove() { //Makes the next move based on the genome TODO: Sam
        int[][] oldField = copyField(Tetris.field);
        int oldScore=Tetris.score;
        int oldPiece=Tetris.curPiece;
        int oldPieceRotation=Tetris.curPieceRotation;
        int[]bestMove=getBestMove();
        for (int i = 0; i < bestMove[0]; i++) Tetris.rotatePiece(true);
        for (int i = 0; i < bestMove[1]; i++) Tetris.movePiece(true);
        int cr=-1;
        while(cr==-1){
            cr=Tetris.movePieceDown(false);
            try {
                Thread.sleep(20);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
        makeMove();
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
