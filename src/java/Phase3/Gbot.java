package Phase3;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Genetic algorithm bot (Gbot) similar to the one from Phase2 but adapted to solving 3D problems
 */
public class Gbot {
    private static int populationSize = 50;
    private static int tournamentSize = 5;
    private static int parNo = 8;
    private static double[][] genomes = new double[populationSize][parNo];
    private static double[][] top10 = new double[10][parNo];
    private static int currentGenome = 0;
    private static int generation = 0;
    private static double mutationRate = 0.1;
    private static double mutationStep = 0.01;
    private static double mutationRate2 = 0.05;
    private static double mutationStep2 = 3;
    private static int writerIterator = 0;
    private static BufferedWriter writer;
    private static File file;
    private static ArrayList<Integer> scores = new ArrayList<Integer>();
    public static int curHeight;
    public static int contX = (int) Math.round(Wrapper.ACTUAL_CONTAINER_WIDTH * 2);
    public static int contY = (int) Math.round(Wrapper.ACTUAL_CONTAINER_HEIGHT * 2);
    public static int contZ = (int) Math.round(Wrapper.ACTUAL_CONTAINER_DEPTH * 2);
    public static int[][][] lastSolid;
    public static int[] lastCoord;
    public static int maxScore = 0;
    public static int boardScore = 0;
    public static boolean con = true;
    public static boolean general = false;
    public static int[] leftToPlace = new int[3];
    public static int[] placed = new int[3];
    public static boolean slow = false;
    public static double[] gen1 = new double[8];

    /**
     * Array storing all pentominoes alongside all of their rotations
     */
    public static int[][][][][] pentominoes = new int[][][][][]{ //{ T , P , L }
            //L
            {
                    {{{1}, {1}, {1}, {1}}, {{1}, {0}, {0}, {0}}},
                    {{{1, 1, 1, 1}}, {{1, 0, 0, 0}}},
                    {{{1}, {1}, {1}, {1}}, {{0}, {0}, {0}, {1}}},
                    {{{1, 1, 1, 1}}, {{0, 0, 0, 1}}},
                    {{{1}, {0}, {0}, {0}}, {{1}, {1}, {1}, {1}}},
                    {{{1, 0, 0, 0}}, {{1, 1, 1, 1}}},
                    {{{0}, {0}, {0}, {1}}, {{1}, {1}, {1}, {1}}},
                    {{{0, 0, 0, 1}}, {{1, 1, 1, 1}}},
                    {{{1, 1}, {1, 0}, {1, 0}, {1, 0}}},
                    {{{1, 0, 0, 0}, {1, 1, 1, 1}}},
                    {{{0, 1}, {0, 1}, {0, 1}, {1, 1}}},
                    {{{1, 1, 1, 1}, {0, 0, 0, 1}}},
                    {{{1, 1}, {0, 1}, {0, 1}, {0, 1}}},
                    {{{1, 1, 1, 1}, {1, 0, 0, 0}}},
                    {{{1, 0}, {1, 0}, {1, 0}, {1, 1}}},
                    {{{0, 0, 0, 1}, {1, 1, 1, 1}}},
                    {{{1}, {0}}, {{1}, {0}}, {{1}, {0}}, {{1}, {1}}},
                    {{{1, 0}}, {{1, 0}}, {{1, 0}}, {{1, 1}}},
                    {{{0}, {1}}, {{0}, {1}}, {{0}, {1}}, {{1}, {1}}},
                    {{{0, 1}}, {{0, 1}}, {{0, 1}}, {{1, 1}}},
                    {{{1}, {1}}, {{1}, {0}}, {{1}, {0}}, {{1}, {0}}},
                    {{{1, 1}}, {{1, 0}}, {{1, 0}}, {{1, 0}}},
                    {{{1}, {1}}, {{0}, {1}}, {{0}, {1}}, {{0}, {1}}},
                    {{{1, 1}}, {{0, 1}}, {{0, 1}}, {{0, 1}}}
            },
            //P
            {
                    {{{1, 1}, {1, 1}, {1, 0}}},
                    {{{1, 1, 0}, {1, 1, 1}}},
                    {{{0, 1}, {1, 1}, {1, 1}}},
                    {{{1, 1, 1}, {0, 1, 1}}},
                    {{{1, 1}, {1, 1}, {0, 1}}},
                    {{{1, 1, 1}, {1, 1, 0}}},
                    {{{1, 0}, {1, 1}, {1, 1}}},
                    {{{0, 1, 1}, {1, 1, 1}}},
                    {{{1}, {1}, {0}}, {{1}, {1}, {1}}},
                    {{{1, 1, 0}}, {{1, 1, 1}}},
                    {{{0}, {1}, {1}}, {{1}, {1}, {1}}},
                    {{{0, 1, 1}}, {{1, 1, 1}}},
                    {{{1}, {1}, {1}}, {{1}, {1}, {0}}},
                    {{{1, 1, 1}}, {{1, 1, 0}}},
                    {{{1}, {1}, {1}}, {{0}, {1}, {1}}},
                    {{{1, 1, 1}}, {{0, 1, 1}}},
                    {{{1}, {0}}, {{1}, {1}}, {{1}, {1}}},
                    {{{1, 0}}, {{1, 1}}, {{1, 1}}},
                    {{{0}, {1}}, {{1}, {1}}, {{1}, {1}}},
                    {{{0, 1}}, {{1, 1}}, {{1, 1}}},
                    {{{1}, {1}}, {{1}, {1}}, {{0}, {1}}},
                    {{{1, 1}}, {{1, 1}}, {{0, 1}}},
                    {{{1}, {1}}, {{1}, {1}}, {{1}, {0}}},
                    {{{1, 1}}, {{1, 1}}, {{1, 0}}}
            },
            //T
            {
                    {{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}}},
                    {{{1, 0, 0}, {1, 1, 1}, {1, 0, 0}}},
                    {{{0, 1, 0}, {0, 1, 0}, {1, 1, 1}}},
                    {{{0, 0, 1}, {1, 1, 1}, {0, 0, 1}}},
                    {{{1}, {0}, {0}}, {{1}, {1}, {1}}, {{1}, {0}, {0}}},
                    {{{1, 0, 0}}, {{1, 1, 1}}, {{1, 0, 0}}},
                    {{{0}, {0}, {1}}, {{1}, {1}, {1}}, {{0}, {0}, {1}}},
                    {{{0, 0, 1}}, {{1, 1, 1}}, {{0, 0, 1}}},
                    {{{0}, {1}, {0}}, {{0}, {1}, {0}}, {{1}, {1}, {1}}},
                    {{{0, 1, 0}}, {{0, 1, 0}}, {{1, 1, 1}}},
                    {{{1}, {1}, {1}}, {{0}, {1}, {0}}, {{0}, {1}, {0}}},
                    {{{1, 1, 1}}, {{0, 1, 0}}, {{0, 1, 0}}}
            },
    };

    /**
     * Array storing all parcels alongside all of their rotations
     */
    public static int[][][][][] parcels = new int[][][][][]{ //{ 1x1.5x2 , 1x1x2 , 1.5x1.5x1.5 }
            //1x1x2
            {
                    {{{1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}}},
                    {{{1, 1}, {1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}, {1, 1}}},
                    {{{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}, {{1, 1}, {1, 1}}}
            },
            //1x1.5x2
            {
                    {{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}}},
                    {{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}}},
                    {{{1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}}},
                    {{{1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}}, {{1, 1, 1, 1}, {1, 1, 1, 1}}},
                    {{{1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}}},
                    {{{1, 1}, {1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}, {1, 1}}, {{1, 1}, {1, 1}, {1, 1}, {1, 1}}}
            },
            //1.5x1.5x1.5
            {
                    {{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}}
            }
    };

    public static int[] aShapeVal = {3, 4, 5};
    public static int[][][][][] aShape = parcels;

    public static void assignValue(int solidIndex, int value) {
        aShapeVal[solidIndex] = value;
    }


    public static boolean removeLastSolid(int[][][] field) {
        if (lastSolid != null) {
            for (int i = 0; i < lastSolid.length; i++) {
                for (int j = 0; j < lastSolid[i].length; j++) {
                    for (int k = 0; k < lastSolid[i][j].length; k++) {
                        if (lastSolid[i][j][k] != 0) {
                            field[lastCoord[0] + i][lastCoord[1] + j][lastCoord[2] + k] = 0;
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method adding a solid to the container (field) at the specified coordinates
     * @param solid: solid to be added
     * @param coord: coordinates to add the solid at
     * @param color: color of the parcel that is being added
     * @param field: field (container) that the object is being added to
     */
    public static void addSolid(int[][][] solid, int[] coord, int color, int[][][] field) { //add the solid to the container at the specified coordinates
        if (!checkCollision(solid, coord)) {
            for (int i = 0; i < solid.length; i++) {
                for (int j = 0; j < solid[i].length; j++) {
                    for (int k = 0; k < solid[i][j].length; k++) {
                        if (solid[i][j][k] != 0) {
                            field[coord[0] + i][coord[1] + j][coord[2] + k] = color;
                        }
                    }
                }
            }
        }
    }

    /**
     * Method checking if a collusion occurs when attempting to add a solid at coordinates
     * @param solid: Solid to be added
     * @param coord: coordinates to add the solid at
     * @return true if a collision occurs, false otherwise
     */
    public static boolean checkCollision(int[][][] solid, int[] coord) {
        if (coord[0] < 0 || coord[1] < 0 || coord[2] < 0) {
            return true;
        } else {
            try {
                for (int i = 0; i < solid.length; i++) { // loop over x position of pentomino
                    for (int j = 0; j < solid[i].length; j++) { // loop over y position of pentomino
                        for (int k = 0; k < solid[i][j].length; k++) {
                            if (solid[i][j][k] == 1) {
                                if (FX3D.UIInput[coord[0] + i][coord[1] + j][coord[2] + k] != 0) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    public static boolean dropPiece(int[][][] solid, int[] coord, int color, boolean tentative) {
        int[] coord1 = coord.clone();
        int h = 33 - curHeight;
        int z = 0;
        if (h - 4 >= 0) z = h - 4;
        coord1[0] += z;
        if (checkCollision(solid, coord1)) return false;
        while (!checkCollision(solid, coord1)) {
            coord1[0]++;
        }
        coord1[0]--;
        if (tentative) addSolid(solid, coord1, color, FX3D.tmpUIInput);
        else addSolid(solid, coord1, color, FX3D.UIInput);

        lastSolid = solid;
        lastCoord = coord1;
        return true;
    }

    public static int[][][] copyField(int[][][] f0) {
        int x = Wrapper.CONTAINER_WIDTH / Wrapper.cellSize;
        int y = Wrapper.CONTAINER_HEIGHT / Wrapper.cellSize;
        int z = Wrapper.CONTAINER_DEPTH / Wrapper.cellSize;
        int[][][] f1 = new int[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    f1[i][j][k] = f0[i][j][k];
                }
            }
        }
        return f1;
    }

    public static Integer[] arrayCopy(Integer[] old) {
        Integer[] n = new Integer[old.length];
        for (int i = 0; i < old.length; i++) {
            n[i] = old[i];
        }
        return n;
    }

    public static int[] arrayCopy(int[] old) {
        int[] n = new int[old.length];
        for (int i = 0; i < old.length; i++) {
            n[i] = old[i];
        }
        return n;
    }

    public static double[] arrayCopy(double[] old) {
        double[] n = new double[old.length];
        for (int i = 0; i < old.length; i++) {
            n[i] = old[i];
        }
        return n;
    }

    /**
     * Creates the initial population of genomes, each with random genes
     */
    public static void initPopulation() {
        for (int i = 0; i < populationSize; i++) {
            double[] gen = {Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5, Math.random() * 0.5, Math.random() - 0.5, Math.random() - 0.5, 0};
            genomes[i] = gen;
            if (i < top10.length) top10[i] = gen.clone();
        }
        //genomes[0]=new double[]{3.6974782858162163, 0.020477199087742037, -0.23918114942987156, -0.11603246371208442, -1.6252129706895644, -0.6811390204242889, 1.044093597983097, 4160.0};
    }

    public static void setup() {
        for (int i = 0; i < 3; i++) {
            placed[i]=0;
        }
        if (Wrapper.problemType.equals("General")) {
            general = true;
            for (int i = 0; i < 3; i++) {
                aShapeVal[i] = (int) Wrapper.inputDetails[i].value;
                leftToPlace[i] = Wrapper.inputDetails[i].amount;
            }
        } else {
            general = false;
        }

        if (Wrapper.inputType.equals("Parcels")) {
            aShape = parcels;
            gen1 = new double[]{0.18096287888426765, 0.00618439891207434, -0.31849350106234864, -0.10701358860879029, 0.1586015231404712, 0.238364609241272, 0.1505493689152868, 231.0}; //best individual
            if (general) gen1 = new double[]{3.64414098285567, -1.2286989694623847, -2.287482242582422, -3.0061077506949183, 0.5379527402479262, -1.4939799418391704, -5.017954274133195, 192.0}; //best individual
            //3.64414098285567, -1.2286989694623847, -2.287482242582422, -3.0061077506949183, 0.5379527402479262, -1.4939799418391704, -5.017954274133195, 192.0 //for general
        } else {
            //TODO train for pentomino general
            gen1 = new double[]{0.25889549831304826, -0.44049829919118355, -0.8359454055376402, -0.012412378382019704, 0.2414328743542116, 0.3378703975584336, -0.46182521987384706, 1192.0}; //best individual
            if (general) gen1 = new double[]{0.25889549831304826, -0.44049829919118355, -0.8307141709975095, -0.011123246697450843, 0.2414328743542116, 0.3378703975584336, -0.46182521987384706, 792.0};
            aShape = pentominoes;
        }
    }

    /**
     * Plays the game by making the next move (makePlay)
     */
    public static void Play() {
        setup();
        boardScore=0;
        //Tetris.training = false;

        FX3D.clearInput(FX3D.tmpUIInput);
        FX3D.clearInput(FX3D.UIInput);
        FX3D.clearInput(Wrapper.UIInput);
//        for(int i=0;i<aShapeVal.length;i++){
//            assignValue(i,1+((int)(Math.random()*100)));
//        }
        //System.out.println(Arrays.toString(aShapeVal));
        genomes[0] = gen1;
        currentGenome = 0;
        makePlay(true);
    }

    /**
     * Trains the neural network
     */
    public static void train() {
        setup();
        boardScore=0;
        FX3D.clearInput(FX3D.tmpUIInput);
        FX3D.clearInput(FX3D.UIInput);
        FX3D.clearInput(Wrapper.UIInput);
        /*LocalDate myObj = LocalDate.now();
        file = new File("src/resources/topIndividuals"+myObj.toString()+".txt");*/
        initPopulation();
        double[] gen1 = {0.25889549831304826, -0.44049829919118355, -0.8359454055376402, -0.012412378382019704, 0.2414328743542116, 0.3378703975584336, -0.46182521987384706, 1192.0}; //best individual
        genomes[0]=gen1;
        while (true) {
            boardScore=0;
            for (int i = 0; i < aShapeVal.length; i++) {
                assignValue(i, 1 + ((int) (Math.random() * 100)));
            }
            System.out.println(Arrays.toString(aShapeVal));
            evalPopulation();
            getNextGen();
        }
    }

    /**
     * Updates the saved top 10 individuals
     */
    public static void updateTop10() {
//        for(int i=0;i<populationSize;i++)
//            System.out.println(genomes[i][7]);
//        System.out.println();
        for (int i = 0; i < top10.length; i++) {
            if (genomes[0][7] > top10[i][7]) {
                top10[top10.length - 1] = genomes[0].clone();
                break;
            }
        }
        java.util.Arrays.sort(top10, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(b[7], a[7]);
            }
        });
    }

    /**
     * Evaluates the next individual in the population. If there is none, evolves the population
     */
    private static void evalPopulation() {
        setup();
        for (int i = 0; i < populationSize; i++) {
            System.out.print("-");
            FX3D.clearInput(FX3D.tmpUIInput);
            FX3D.clearInput(FX3D.UIInput);
            currentGenome = i;
            boardScore=0;
            aShape = pentominoes;
            //aShapeVal = new int[]{3, 4, 5};
            makePlay(false);
        }
        /*for(int i=0;i<populationSize;i++)
            System.out.println(genomes[i][7]);
        System.out.println();*/
    }

    /**
     * Creates the new population using the best individuals from the last
     */
    private static void getNextGen() {
        generation++;
        geneSort(genomes);
        updateTop10();

        /*try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(Arrays.toString(genomes[0]) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        for (int i = 0; i < 5; i++)
            System.out.println(Arrays.toString(genomes[i]));
        /*for(int i=0;i<genomes.length;i++){
            System.out.println(Arrays.toString(genomes[i]));
        }
        System.out.println();*/
        int k = 0;
        double[][] tempGene = new double[populationSize][parNo];
        int[] parents = new int[2];
        int elites = (int) (genomes.length / 50.0);
        for (int i = 0; i < elites; i++) {
            tempGene[i] = genomes[i];
        }
        for (int i = elites; i < populationSize; i++) {
            parents = tournamentSelection(tournamentSize);
            tempGene[i] = mate(parents[0], parents[1]);
        }
        genomes = tempGene;
    }

    /**
     * Performs the tournament selection for selection of parents
     *
     * @param tSize: size of population to undergo the selection
     * @return best parents from the tournament selection
     */
    public static int[] tournamentSelection(int tSize) {
        int[] parents = {0, 0};
        double bestScore = 0;
        int bestInd = 0;
        int a = 0;
        for (int i = 0; i < tSize; i++) {
            a = (int) (Math.random() * populationSize);
            if (genomes[a][7] > bestScore) {
                bestScore = genomes[a][7];
                bestInd = a;
            }
        }
        parents[0] = bestInd;
        bestScore = 0;
        bestInd = 0;
        for (int i = 0; i < tSize; i++) {
            a = (int) (Math.random() * populationSize);
            if (genomes[a][7] > bestScore) {
                bestScore = genomes[a][7];
                bestInd = a;
            }
        }
        parents[1] = bestInd;
        //System.out.println(Arrays.toString(parents));
        return parents;
    }

    /**
     * Gets a new child based on two parents (mom and dad)
     *
     * @param mom: female parent
     * @param dad: male parent
     * @return a child of two individuals
     */
    private static double[] mate(int mom, int dad) {
        int crossover = (int) Math.random() * 6;
        double[] child = new double[parNo];
        for (int i = 0; i < crossover; i++) {
            child[i] = genomes[dad][i];
        }
        for (int i = crossover; i < child.length - 1; i++) {
            child[i] = genomes[mom][i];
        }
        //child[7] = 0;

        for (int i = 0; i < child.length - 1; i++) {
            if (Math.random() < mutationRate) {
                double mut = Math.random() * mutationStep * 2.0;
                child[i] = child[i] + mut - mutationStep;
            }
            if (Math.random() < mutationRate2) {
                double mut = Math.random() * mutationStep2 * 2.0;
                child[i] = child[i] + mut - mutationStep2;
            }
            if (Math.random() < mutationRate) {
                child[i] = (genomes[mom][i] + genomes[dad][i]) / 2;
            }
        }
        return child;
    }

    /**
     * Gets a best move
     *
     * @return
     */
    private static int[] getBestMove() {
        FX3D.tmpUIInput = copyField(FX3D.UIInput);
        double[] algorithm = new double[6];
        int[] move = new int[4]; //rotation,translation,rating
        curHeight = getHeight();
        double bestRating = -10000;
        int[] bestMove = new int[]{-1, -1, -1, -1}; //rotation,translation,rating
        for (int a = 0; a < aShape.length; a++) {
            outerloop:
            for (int b = 0; b < aShape[a].length; b++) {
                for (int i = 0; i < Wrapper.ACTUAL_CONTAINER_HEIGHT * 2; i++) {
                    for (int j = 0; j < Wrapper.ACTUAL_CONTAINER_DEPTH * 2; j++) {
                        if (dropPiece(aShape[a][b], new int[]{0, i, j}, 1, true)) {
                            if (general && leftToPlace[a] == 0) break outerloop;
                            double rating = 0;
                            rating += aShapeVal[a] * genomes[currentGenome][0];
                            rating += getHeight(curHeight) * genomes[currentGenome][1];
                            rating += getCompactness() * genomes[currentGenome][2];
                            rating += getHoles() * genomes[currentGenome][3];
                            move[0] = a;
                            move[1] = b;
                            move[2] = i;
                            move[3] = j;
                            if (rating > bestRating) {
                                bestRating = rating;
                                bestMove[0] = move[0];
                                bestMove[1] = move[1];
                                bestMove[2] = move[2];
                                bestMove[3] = move[3];
                            }
                            removeLastSolid(FX3D.tmpUIInput);
                        }
                    }
                }
            }
        }
        FX3D.tmpUIInput = copyField(FX3D.UIInput);
        if (bestMove[0] != -1){
            if (general) leftToPlace[bestMove[0]]--;
            placed[bestMove[0]]++;
            boardScore += aShapeVal[bestMove[0]];
        }

        return bestMove;
    }

    /**
     * Makes next move based on the genome
     *
     * @param play: true if supposed to make next move, false otherwise
     */
    public static void makePlay(boolean play) {
        if (play) {
            if (slow) {
                Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        boolean con = true;
                        if (con) {
                            int[] bm = getBestMove();
                            if (bm[0] != -1) {
                                curHeight = getHeight();
                                con = dropPiece(aShape[bm[0]][bm[1]], new int[]{0, bm[2], bm[3]}, bm[0] + 1, false);
                                if (!con){
                                    boardScore -= aShapeVal[bm[0]];

                                    System.out.println("---------- Statistics ----------");
                                    System.out.println("- Amount of parcels of type A placed: " + placed[0]);
                                    System.out.println("- Amount of parcels of type B placed: " + placed[1]);
                                    System.out.println("- Amount of parcels of type C placed: " + placed[2]);
                                }
                                //System.out.println(Arrays.toString(bm));
                            }
                        }
                        Wrapper.score = boardScore;
                        Wrapper.UIInput = FX3D.tmpUIInput;
                        System.out.println(getHoles());
                        FX3D.updateUI();
                    }
                }));
                fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
                fiveSecondsWonder.play();
            } else {
                boolean con = true;
                while (con) {
//                if(!con){
//                    FX3D.clearInput(FX3D.tmpUIInput);
//                    FX3D.clearInput(FX3D.UIInput);
//                }
                    int[] bm = getBestMove();
                    if (bm[0] == -1) break;
                    curHeight = getHeight();
                    con = dropPiece(aShape[bm[0]][bm[1]], new int[]{0, bm[2], bm[3]}, bm[0] + 1, false);
                    if (!con) boardScore -= aShapeVal[bm[0]];
                    //System.out.println(Arrays.toString(bm));
                }
                Wrapper.score = boardScore;
                Wrapper.UIInput = FX3D.UIInput;
                System.out.println("---------- Statistics ----------");
                System.out.println("- Amount of parcels of type A placed: " + placed[0]);
                System.out.println("- Amount of parcels of type B placed: " + placed[1]);
                System.out.println("- Amount of parcels of type C placed: " + placed[2]);
                FX3D.updateUI();
            }
        } else {
            for (int i = 0; i < 3; i++) {
                placed[i]=0;
            }
            boolean con = true;
            while (con) {
//                if(!con){
//                    FX3D.clearInput(FX3D.tmpUIInput);
//                    FX3D.clearInput(FX3D.UIInput);
//                }
                int[] bm = getBestMove();
                if (bm[0] == -1) break;
                curHeight = getHeight();
                con = dropPiece(aShape[bm[0]][bm[1]], new int[]{0, bm[2], bm[3]}, bm[0] + 1, false);
                if (!con) boardScore -= aShapeVal[bm[0]];
                //System.out.println(Arrays.toString(bm));
            }
            Wrapper.score = boardScore;
            Wrapper.UIInput = FX3D.UIInput;
            /*System.out.println("---------- Statistics ----------");
            System.out.println("- Amount of parcels of type A placed: " + placed[0]);
            System.out.println("- Amount of parcels of type B placed: " + placed[1]);
            System.out.println("- Amount of parcels of type C placed: " + placed[2]);*/
            //FX3D.updateUI();
            genomes[currentGenome][7] = boardScore;
        }
    }

    /**
     * Calculates the cumulative height
     *
     * @return cumulative height
     */
    private static double getCumHeight() {
        int cumHeight = 0;
        /*int [] l = new int [Tetris.fieldWidth];
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
        }*/
        return cumHeight;
    }

    /**
     * Gets number of holes in the game field
     *
     * @return number of holes in game field
     */
    private static double getHoles() {
        int x = (int) Math.round(Wrapper.ACTUAL_CONTAINER_WIDTH * 2);
        int y = (int) Math.round(Wrapper.ACTUAL_CONTAINER_HEIGHT * 2);
        int z = (int) Math.round(Wrapper.ACTUAL_CONTAINER_DEPTH * 2);

        int holes = 0;
        int [][] l = new int [y][z];
        for (int i = 0; i < l.length; i++)
            for (int j = 0; j < l[i].length; j++) l[i][j]=x;

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < z; j++) {
                for (int k = 0; k < x; k++) {
                    if (FX3D.tmpUIInput[k][i][j] != 0) {
                        l[i][j] = k;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < y; i++) {
            for (int j = 0; j < z; j++) {
                for (int k = l[i][j]; k < x; k++) {
                    if(FX3D.tmpUIInput[k][i][j]==0) holes++;
                }
            }
        }
        return holes;
    }

    /**
     * Gets roughness
     *
     * @return roughness
     */
    private static double getRoughness() {
        int roughness = 0;/*
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
        }*/
        return roughness;
    }

    /**
     * Gets relative height
     *
     * @return relative height
     */
    private static double getRelHeight() {
        int relheight = 0;/*
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
        relheight=(int)(max-min);*/
        return relheight;
    }

    /**
     * Gets height
     *
     * @return height
     */
    private static int getHeight() {
        int x = (int) Math.round(Wrapper.ACTUAL_CONTAINER_WIDTH * 2);
        int y = (int) Math.round(Wrapper.ACTUAL_CONTAINER_HEIGHT * 2);
        int z = (int) Math.round(Wrapper.ACTUAL_CONTAINER_DEPTH * 2);
        int height = 0;
        outerloop:
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if (FX3D.tmpUIInput[i][j][k] != 0) {
                        height = x - i;
                        break outerloop;
                    }
                }
            }
        }
        return height;
    }

    private static int getHeight(int start) {
        start = 33 - start;
        if (start - 4 > 0) start -= 4;
        else start = 0;
        int x = (int) Math.round(Wrapper.ACTUAL_CONTAINER_WIDTH * 2);
        int y = (int) Math.round(Wrapper.ACTUAL_CONTAINER_HEIGHT * 2);
        int z = (int) Math.round(Wrapper.ACTUAL_CONTAINER_DEPTH * 2);
        int height = 0;
        outerloop:
        for (int i = start; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if (FX3D.tmpUIInput[i][j][k] != 0) {
                        height = x - i;
                        break outerloop;
                    }
                }
            }
        }
        return height;
    }

    private static double getCompactness() {
        double compactness = 0;
        double cumDist = 0;
        int voxN = 0;
        for (int i = 0; i < contX; i++) {
            for (int j = 0; j < contY; j++) {
                for (int k = 0; k < contZ; k++) {
                    if (FX3D.tmpUIInput[i][j][k] != 0) {
                        cumDist += Math.sqrt((i - contX) * (i - contX) + (j - contY) * (j - contY) + (k - contZ) * (k - contZ));
                        voxN++;
                    }
                }
            }
        }
        compactness = cumDist / voxN;
        return compactness;
    }

    /*
    Based on https://core.ac.uk/download/pdf/82384325.pdf Section 2: Concepts and definitions

    private static double getCompactness() {
        int x = (int) Math.round(Wrapper.ACTUAL_CONTAINER_WIDTH * 2);
        int y = (int) Math.round(Wrapper.ACTUAL_CONTAINER_HEIGHT * 2);
        int z = (int) Math.round(Wrapper.ACTUAL_CONTAINER_DEPTH * 2);
        int enclosingSurfaceArea = 0;
        int volume = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if (FX3D.UIInput[i][j][k] != 0) {
                        if(i-1>=0 && FX3D.UIInput[i-1][j][k] == 0){
                                enclosingSurfaceArea++;
                            }
                        if(j-1>=0 && FX3D.UIInput[i][j-1][k] == 0){
                                enclosingSurfaceArea++;
                            }
                        if(k-1>=0 && FX3D.UIInput[i-1][j][k] == 0){
                                enclosingSurfaceArea++;
                            }
                        if(i+1<x && FX3D.UIInput[i-1][j][k] == 0){
                                enclosingSurfaceArea++;
                            }
                        if(j+1<y && FX3D.UIInput[i-1][j][k] == 0){
                                enclosingSurfaceArea++;
                            }
                        if(k+1<x && FX3D.UIInput[i-1][j][k] == 0){
                                enclosingSurfaceArea++;
                            }
                        volume++;
                    }
                }
            }
        }
        return Math.pow(enclosingSurfaceArea,3)/Math.pow(volume,2);
    }*/

    /**
     * Sorts the array of genes
     *
     * @param gen: sorted array of genes
     */
    public static void geneSort(double[][] gen) {
        java.util.Arrays.sort(gen, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(b[7], a[7]);
            }
        });
    }
}
