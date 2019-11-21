package Phase2.Tetris;

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
    private Archive[] archive =new Archive[];
    private double mutationRate;
    private double mutationStep;

    //Creates the initial population of genomes, each with random genes
    private void initPopulation(){}
    //Evaluates the next individual in the population. If there is none, evolves the population
    private void evalIndividual(){}
    //Creates the new population using the best individuals from the last
    private void getNextGen(){}
    //Returns a child from 2 individuals
    private void mate(){}
    //Returns an array of possible moves
    private void getBestMove(){}
    //Makes the next move based on the genome
    private void makeMove(){}
    private void genBag(){}
    private void nextShape(){}

    //Genome Parameter Values
    private void getCumHeight() {\\;)}
    private void getHoles(){}
    private void getHolesArray(){}
    private void getRoughness(){}
    private void getRelHeight(){}
    private void getHeight(){}
    private void getRowsCleared(){}



}
