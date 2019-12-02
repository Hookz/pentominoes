package Phase2.Tetris;
import Tetris.Tetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Gbot {
    private static int populationSize=100;
    private static int tournamentSize=5;
    private static int parNo = 8;
    private static int gamesN = 30;
    private static double[][] genomes=new double[populationSize][parNo];
    private static double[][] top10=new double[10][parNo];
    private static int currentGenome=0;
    private static int generation=0;
    private static double mutationRate=0.1;
    private static double mutationStep=0.01;
    private static double mutationRate2=0.05;
    private static double mutationStep2=3;
    private static int writerIterator = 0;
    private static BufferedWriter writer;
    private static File file;
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

    public static double[] arrayCopy(double[] old){
        double[]n = new double[old.length];
        for(int i=0;i<old.length;i++){
            n[i]=old[i];
        }
        return n;
    }

    //Creates the initial population of genomes, each with random genes TO DO: Lindalee
    public static void initPopulation() {
        for(int i = 0; i<populationSize ; i ++) {
            double[] gen = {Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5, Math.random() * 0.5, Math.random() - 0.5,Math.random()-0.5,0};
            genomes[i] = gen;
            if(i<top10.length)top10[i]=gen.clone();
        }
        //genomes[0]=new double[]{3.6974782858162163, 0.020477199087742037, -0.23918114942987156, -0.11603246371208442, -1.6252129706895644, -0.6811390204242889, 1.044093597983097, 4160.0};
    }
    public static void Play() {
        Tetris.training = false;
        double []gen1={3.6974782858162163, 0.020477199087742037, -0.23918114942987156, -0.11603246371208442, -1.6252129706895644, -0.6811390204242889, 1.044093597983097, 4160.0}; //best individual
        //double []gen1={2.883322384854915, 0.48480348209139357, -2.7311401426486226, 0.7769037846237963, -4.525753498240361, -4.223996415025829, 1.088329351302793, 3803.3333333333335}; //best individual

        genomes[0] = gen1;
        currentGenome=0;
        makePlay(true);
    }

    public static void train() {
        LocalDate myObj = LocalDate.now();
        file = new File("src/resources/topIndividuals"+myObj.toString()+".txt");
        Tetris.training = true;
        initPopulation();
        while(true){
            //System.out.println(generation);
            evalPopulation();
            getNextGen();
        }
        /*try {
            Thread.sleep(100);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }*/
    }

    public static void updateTop10(){

        for(int i=0;i<top10.length;i++){
            if(genomes[0][7]>top10[i][7]){
                top10[top10.length-1]=genomes[0].clone();
                break;
            }
        }
        java.util.Arrays.sort(top10, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(b[7], a[7]);
            }
        });

        for(int i=0;i<top10.length;i++)
            System.out.println(Arrays.toString(top10[i]));
    }

    //Evaluates the next individual in the population. If there is none, evolves the population TO DO: Lindalee
    private static void evalPopulation() {
        State s1=new State();
        for(int i=0;i<populationSize;i++){
            System.out.print("-");
            loadState(s1);
            currentGenome=i;
            makePlay(false);

        }
        System.out.println();
    }

    //Creates the new population using the best individuals from the last TO DO: Lindalee
    private static void getNextGen() {
        generation++;
        geneSort(genomes);

        updateTop10();

        try {
            writer = new BufferedWriter(new FileWriter(file,true));
            writer.write(Arrays.toString(genomes[0])+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for(int i=0;i<5;i++)
//            System.out.println(Arrays.toString(genomes[i]));
        /*for(int i=0;i<genomes.length;i++){
            System.out.println(Arrays.toString(genomes[i]));
        }
        System.out.println();*/
        int k = 0;
        double[][] tempGene = new double [populationSize][parNo];
        int[]parents=new int[2];
        int elites=(int)(genomes.length/50.0);
        for (int i = 0; i < elites; i++) {
            tempGene[i]=genomes[i];
        }
        for (int i = elites; i < populationSize; i++) {
            parents=tournamentSelection(tournamentSize);
            tempGene[i]=mate(parents[0], parents[1]);
        }
        genomes = tempGene;
    }

    public static int[] tournamentSelection(int tSize){
        int[] parents={0,0};
        double bestScore=0;
        int bestInd=0;
        int a=0;
        for (int i = 0; i < tSize; i++) {
            a=(int)(Math.random()*populationSize);
            if(genomes[a][7]>bestScore){
                bestScore=genomes[a][7];
                bestInd=a;
            }
        }
        parents[0]=bestInd;
        bestScore=0;
        bestInd=0;
        for (int i = 0; i < tSize; i++) {
            a=(int)(Math.random()*populationSize);
            if(genomes[a][7]>bestScore){
                bestScore=genomes[a][7];
                bestInd=a;
            }
        }
        parents[1]=bestInd;
        //System.out.println(Arrays.toString(parents));
        return parents;
    }

    //Returns a child from 2 individuals TO DO: Lindalee
    private static double[] mate(int mom, int dad) {
        int crossover = (int)Math.random()*6;
        double[] child = new double[parNo];
        for(int i = 0; i < crossover;i++){
            child[i] = genomes[dad][i];
        }
        for(int i = crossover; i < child.length -1;i++){
            child[i] = genomes[mom][i];
        }
        child[7]= 0;

        for(int i = 0;i < child.length-1; i++) {
            if (Math.random() < mutationRate) {
                double mut=Math.random() * mutationStep * 2.0;
                child[i] = child[i] + mut - mutationStep;
            }
            if (Math.random() < mutationRate2) {
                double mut=Math.random() * mutationStep2 * 2.0;
                child[i] = child[i] + mut - mutationStep2;
            }
            if (Math.random() < mutationRate) {
                child[i] = (genomes[mom][i] + genomes[dad][i])/2;
            }
        }
        return child;
    }

    private static int[] getBestMove1() { //Returns an array of possible moves
        State s1=new State();
        ArrayList<Integer[]> possibleMoves= new ArrayList<Integer[]>();
        Integer[] move = new Integer[7]; //rotation,translation,rating
        double[] algorithm = new double[6];
        int [] rot= new int[]{1,2,2,4,4,4,4,4,4,4,4,4};
        for (int i = 0; i < rot[Tetris.curPiece]; i++) { //for each possible rotation
            loadState(s1);
            for (int t = 0; t < Tetris.fieldWidth; t++) {
                loadState(s1);
                boolean moved=true;
                for (int k = 0; k < i; k++) Tetris.rotatePiece(true);
                for (int k = 0; k < Tetris.fieldWidth; k++){
                    Tetris.movePiece(false);
                }
                for (int k = 0; k < t; k++){
                    if(!Tetris.movePiece(true)) moved=false;
                }
                if(!moved){
                    break;
                }
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

                rating1 += algorithm[0] * genomes[currentGenome][0];
                rating1 += algorithm[1] * genomes[currentGenome][1];
                rating1 += algorithm[2] * genomes[currentGenome][2];
                rating1 += algorithm[3] * genomes[currentGenome][3];
                rating1 += algorithm[4] * genomes[currentGenome][4];
                rating1 += algorithm[5] * genomes[currentGenome][5];
                if(bestMoveNext!=null && i==bestMoveNext[0] && t==bestMoveNext[1]) rating1*=genomes[currentGenome][6];

                State s2=new State();
                for (int j = 0; j < rot[Tetris.nextPiece]; j++) { //for each possible rotation
                    for (int l = 0; l < Tetris.fieldWidth; l++) {
                        Tetris.field = copyField(s2.oldField);
                        Tetris.score = s2.oldScore;
                        Tetris.curPiece = Tetris.nextPiece;
                        Tetris.curPieceRotation = Tetris.nextRot;
                        Tetris.curPos = s2.oldPiecePos;
                        moved=true;
                        for (int k = 0; k < j; k++) Tetris.rotatePiece(true);
                        for (int k = 0; k < Tetris.fieldWidth; k++){
                            Tetris.movePiece(false);
                        }
                        for (int k = 0; k < l; k++){
                            if(!Tetris.movePiece(true)) moved=false;
                        }
                        if(!moved){
                            break;
                        }
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
                        possibleMoves.add(move.clone());

                        /*try {
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException ex){
                            Thread.currentThread().interrupt();
                        }*/
                    }
                }
            }
        }
        loadState(s1);

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

    static class State{
        int[][] oldField=new int[Tetris.fieldWidth][Tetris.fieldHeight];
        int oldScore;
        int oldPiece;
        int oldNextPiece;
        int oldPieceRotation;
        int oldNextPieceRotation;
        int oldPiecePos[]=new int[2];
        public State(){
            oldField = copyField(Tetris.field);
            oldScore=Tetris.score;
            oldPiece=Tetris.curPiece;
            oldNextPiece=Tetris.nextPiece;
            oldPieceRotation=Tetris.curPieceRotation;
            oldNextPieceRotation = Tetris.nextRot;
            oldPiecePos=Tetris.curPos.clone();
        }
    }

    public static void loadState(State s){
        Tetris.field = copyField(s.oldField);
        Tetris.score = s.oldScore;
        Tetris.curPiece = s.oldPiece;
        Tetris.nextPiece = s.oldNextPiece;
        Tetris.curPieceRotation = s.oldPieceRotation;
        Tetris.nextRot = s.oldNextPieceRotation;
        Tetris.curPos=s.oldPiecePos.clone();
    }

    public static void makePlay(boolean play) { //Makes the next move based on the genome TODO: Sam
        if(play){
            while(true) {
                int[] bestMove = getBestMove1();
                for (int i = 0; i < bestMove[0]; i++) Tetris.rotatePiece(true);
                for (int i = 0; i < Tetris.fieldWidth; i++) Tetris.movePiece(false);
                for (int i = 0; i < bestMove[1]; i++) Tetris.movePiece(true);
                int cr = -1;
                while (cr == -1) {
                    cr = Tetris.movePieceDown(false);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (cr == -2) {
                    scores.add(Tetris.lastScore);
                    games++;
                    //System.out.println(games);
                }
            }
        }else{
            while(games<gamesN){
                int[]bestMove=getBestMove1();
                for (int i = 0; i < bestMove[0]; i++) Tetris.rotatePiece(true);
                for (int i = 0; i < Tetris.fieldWidth; i++) Tetris.movePiece(false);
                for (int i = 0; i < bestMove[1]; i++) Tetris.movePiece(true);
                int cr=-1;
                while(cr==-1){
                    cr=Tetris.dropPiece(false);
            /*try {
                Thread.sleep(5);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }*/
                }
                if(cr==-2){
                    scores.add(Tetris.lastScore);
                    games++;
                    //System.out.println(games);
                }
            }
            Integer sum = 0;
            if(!scores.isEmpty()) {
                for (Integer mark : scores) {
                    sum += mark;
                }
                double avg=sum.doubleValue() / scores.size();
                genomes[currentGenome][7]=avg;
                //System.out.println(avg);
                scores= new ArrayList<Integer>();
            }
        }

        Tetris.wipeField(Tetris.field);
        Tetris.tempField = copyField(Tetris.field);
        Tetris.instantiateNewPiece(false);
        Tetris.start = true;
        games = 0;
        bestMoveNext = new int[3];
    }

    private static double getCumHeight() {
        int cumHeight=0;
        int [] l = new int [Tetris.fieldWidth];
        for (int i = 0; i < l.length; i++) l[i]=20;
        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l[i]=20-j;
                    break;
                }
            }
        }
        for(int i=0;i<l.length;i++){
            if(l[i]==20)l[i]=0;
            cumHeight+=l[i];
        }
        return cumHeight;
    }

    private static double getHoles() {
        int holes=0;
        int [] l = new int [Tetris.fieldWidth];
        for (int i = 0; i < l.length; i++) l[i]=20;
        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l[i]=j;
                    break;
                }
            }
        }
        for(int i=0;i<Tetris.fieldWidth;i++){
            for(int j=l[i];j<Tetris.fieldHeight;j++){
                if(Tetris.field[i][j]==-1) holes++;
            }
        }
        return holes;
    }

    private static double getRoughness() {
        int roughness=0;
        int [] l = new int [Tetris.fieldWidth];
        for (int i = 0; i < l.length; i++) l[i]=20;
        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l[i]=20-j;
                    break;
                }
            }
        }
        for(int i=0;i<l.length;i++) {
            if (l[i] == 20) l[i] = 0;
        }
        for(int i=0;i<l.length-1;i++){
            roughness+=Math.abs(l[i]-l[i+1]);
        }
        return roughness;
    }

    private static double getRelHeight() {
        int [] l = new int [Tetris.fieldWidth];
        for (int i = 0; i < l.length; i++) l[i]=20;
        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    l[i]=20-j;
                    break;
                }
            }
        }
        for(int i=0;i<l.length;i++) {
            if (l[i] == 20) l[i] = 0;
        }
        int max=0;
        int min=20;
        for(int i=0;i<l.length;i++) {
            if(l[i]>max)max=l[i];
            else if(l[i]<min)min=l[i];
        }
        return (int)(max-min);
    }

    private static double getHeight() { //TODO: Sam
        int height=0;
        int [] l = new int [Tetris.fieldWidth];
        for (int i = 0; i < l.length; i++) l[i]=20;
        for (int i = 0; i < Tetris.fieldWidth; i++) {
            for (int j = 0; j < Tetris.fieldHeight; j++) {
                if(Tetris.field[i][j]!=-1){
                    height=20-j;
                    break;
                }
            }
        }
        return height;
    }

    public static void geneSort(double [][] gen){
        java.util.Arrays.sort(gen, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(b[7], a[7]);
            }
        });
    }
}
