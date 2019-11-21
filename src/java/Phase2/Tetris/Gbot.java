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

    //Creates the initial population of genomes, each with random genes TODO: Lindalee
    private void initPopulation(){}
    //Evaluates the next individual in the population. If there is none, evolves the population TODO: Lindalee
    private void evalIndividual(){}
    //Creates the new population using the best individuals from the last TODO: Lindalee
    private void getNextGen(){}
    //Returns a child from 2 individuals TODO: Lindalee
    private void mate(){}
    //Returns an array of possible moves TODO: Sam
    private void getBestMove(){}
    //Makes the next move based on the genome TODO: Sam
    private void makeMove(){} //TODO: Sam
    private void genBag(){} // TODO: Sam
    private void nextShape(){} //TODO: Sam

    //Genome Parameter Values
    private void getCumHeight() {\\;)} //TODO: Lindalee
    private void getHoles(){} //TODO: Lindalee
    private void getHolesArray(){} //TODO: Lindalee
    private void getRoughness(){} //TODO: Lindalee
    private void getRelHeight(){} //TODO: Sam
    private void getHeight(){} //TODO: Sam
    private void getRowsCleared(){} //TODO: Sam



}
