package Phase2.Tetris;

import General.PentominoDatabase;

import java.util.ArrayList;

public class Gbot {
    class Archive {
        int populationSize = 0;
        int currentGen = 0;
        double[][] elites;
        double[][] genomes;
        public Archive(){}
    }

    private double score;
    private double speed;
    private int movesNumber;
    private int movesLimit;
    private int populationSize;
    private double[][] genomes;
    private int currentGenome;
    private int generation;
    private double[] moveParameters;
    private ArrayList<Archive> archive =new ArrayList<Archive>();
    private double mutationRate;
    private double mutationStep;

    public static int[][] copyField(int[][] f0){
        int [][] f1=new int[Tetris.fieldWidth][Tetris.fieldHeight];
        for(int i=0;i<Tetris.fieldWidth;i++){
            for(int j=0;j<Tetris.fieldHeight;j++){
                f1[i][j]=f0[i][j];
            }
        }
        return f1;
    }

    //Creates the initial population of genomes, each with random genes TO DO: Lindalee
    private void initPopulation(){}
    //Evaluates the next individual in the population. If there is none, evolves the population TO DO: Lindalee
    private void evalIndividual(){}
    //Creates the new population using the best individuals from the last TO DO: Lindalee
    private void getNextGen(){}
    //Returns a child from 2 individuals TO DO: Lindalee
    private void mate(){}

    private void getBestMove(){ //Returns an array of possible moves TODO: Sam
        /*int [][]oldField=copyField(Tetris.field);
        int curPiece=Tetris.curPiece;
        int curPieceRotation=Tetris.curPieceRotation;
        int [] move=new int [3]; //rotation,translation,rating

        for (int i=0;i<PentominoDatabase.data[Tetris.curPiece].length;i++) { //for each possible rotation
            for (int k=0;k<i;k++) Tetris.rotatePiece(true);
            int[][] ptp = PentominoDatabase.data[Tetris.curPiece][i];
            for (int t = -5; t <= 5; t++) {
                if(t<0){
                    for (int m = 0; m < -1*t; m++) {
                        Tetris.movePiece(true);
                    }
                } else if(t>0){
                    Tetris.movePiece(false);
                }
            }
        }*/
    }
    private void makeMove() { //Makes the next move based on the genome TODO: Sam

    }
    private void genBag() { // TODO: Sam

    }
    private void nextShape() { //TODO: Sam

    }

    //Genome Parameter Values
    private void getCumHeight() {} //TO DO: Lindalee
    private void getHoles(){} //TO DO: Lindalee
    private void getHolesArray(){} //TO DO: Lindalee
    private void getRoughness(){} //TO DO: Lindalee
    private void getRelHeight() { //TODO: Sam

    }
    private void getHeight() { //TODO: Sam

    }
    private void getRowsCleared() { //TODO: Sam

    }



}
