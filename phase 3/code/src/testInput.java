public class testInput {
    public static void main(String[] args){
        int amountOfShapes = 1;
        int[][][][] input = new int[amountOfShapes][Wrapper.CONTAINER_WIDTH/Wrapper.cellSize][Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize][Wrapper.CONTAINER_DEPTH/Wrapper.cellSize];

        int[][][] testShape = {{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}}};
        input[0] = testShape;

        CreateDancingInput createDancingInput = new CreateDancingInput("Parcels", input);
        createDancingInput.createPlacements();
        createDancingInput.threeDToOneD();

    }
}
