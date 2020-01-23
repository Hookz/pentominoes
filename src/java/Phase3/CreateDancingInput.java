package Phase3;
import java.util.ArrayList;
public class CreateDancingInput {
    boolean[][][][][] shapes;
    String type; //Parcels or Pentominoes

    ArrayList<boolean[][][]> placements = new ArrayList<>();
    ArrayList<Integer> rowTypes = new ArrayList<>();
    boolean[][] inputMatrix;
    float[] rowValueHelper;

    //Keeps track of the order in which the input will be given with the index being the order of the booklet
    int[] order;

    int width = (Wrapper.CONTAINER_DEPTH/Wrapper.cellSize);
    int height = (Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize);
    int depth = (Wrapper.CONTAINER_WIDTH/Wrapper.cellSize);

    public CreateDancingInput(String type) {
        this.type = type;
    }

    /**
     * Method retrieving the input selected in the Wrapper class and generating input
     */
    public void selectInput(){
        if(type.equals("Parcels")){
            boolean[][][][] A = ShapesAndRotations.getA();
            boolean[][][][] B = ShapesAndRotations.getB();
            boolean[][][][] C = ShapesAndRotations.getC();

            if(Wrapper.problemType.equals("B")){
                //Go greedy and use the order: A -> C -> B
                shapes = new boolean[][][][][]{A, C, B};
                order = new int[]{0, 2, 1};
                rowValueHelper = new float[]{Wrapper.inputDetails[0].value, Wrapper.inputDetails[2].value, Wrapper.inputDetails[1].value};
            } else {
                shapes = new boolean[][][][][]{A, B, C};
                order = new int[]{0, 1, 2};
                rowValueHelper = new float[]{Wrapper.inputDetails[0].value, Wrapper.inputDetails[1].value, Wrapper.inputDetails[2].value};
            }

        } else if (type.equals("Pentominoes")){
            boolean[][][][] L = ShapesAndRotations.getL();
            boolean[][][][] P = ShapesAndRotations.getP();
            boolean[][][][] T = ShapesAndRotations.getT();

            if(Wrapper.problemType.equals("D")){
                //Go greedy and use order: T -> P -> L
                shapes = new boolean[][][][][]{T, P, L};
                order = new int[]{2, 1, 0};
                rowValueHelper = new float[]{Wrapper.inputDetails[2].value, Wrapper.inputDetails[1].value, Wrapper.inputDetails[0].value};
            } else {
                shapes = new boolean[][][][][]{L, P, T};
                order = new int[]{0, 1, 2};
                rowValueHelper = new float[]{Wrapper.inputDetails[0].value, Wrapper.inputDetails[1].value, Wrapper.inputDetails[2].value};
            }
        }
    }

    public void createPlacements(){
        //For every type of shape
        int shapeNumber = 0;
        int typeNumber = 0;
        for(boolean[][][][] typeOfShape : shapes){
            //For every shape
            for(boolean[][][] shape : typeOfShape){
                int shapeWidth = shape[0][0].length;
                int shapeHeight = shape[0].length;
                int shapeDepth = shape.length;

                //For every depth layer
                for(int zPlacementStart=0; zPlacementStart < depth; zPlacementStart++){
                    //For every y
                    for(int yPlacementStart=0; yPlacementStart < height; yPlacementStart++){
                        //For every x
                        for(int xPlacementStart=0; xPlacementStart < width; xPlacementStart++){
                            //Check if it fits
                            if(fits(xPlacementStart, yPlacementStart, zPlacementStart, shape)){
                                int shapeX = 0;
                                int shapeY = 0;
                                int shapeZ = 0;

                                //Get shape inside container
                                boolean[][][] shapeInContainer = new boolean[depth][height][width];

                                //Place shape in container
                                for(int zContainer=0; zContainer < depth; zContainer++){
                                    //System.out.println("zContainer:" + zContainer);

                                    //If this is the layer that the shape needs to be placed in
                                    if(zContainer>=zPlacementStart && zContainer<zPlacementStart+shapeDepth){
                                        //System.out.println("Z: " + z);
                                        shapeY = 0;


                                        for(int yContainer=0; yContainer < height; yContainer++) {
                                            //System.out.println("yContainer:" + yContainer);
                                            shapeX = 0;

                                            //If this is the height that the shape needs to be placed on
                                            if(yContainer>=yPlacementStart && yContainer<yPlacementStart+shapeHeight){
                                                for (int xContainer = 0; xContainer < width; xContainer++) {
                                                    //System.out.println("xContainer:" + xContainer);
                                                    //If this is the x that the shape needs to be placed on
                                                    if(xContainer>=xPlacementStart && xContainer<xPlacementStart+shapeWidth){
//                                                    System.out.println("Z shape:" + shapeZ);
//                                                    System.out.println("Y shape:" + shapeY);
//                                                    System.out.println("X shape:" + shapeX);
                                                        if(shape[shapeZ][shapeY][shapeX]){
                                                            shapeInContainer[zContainer][yContainer][xContainer] = true;
                                                        }

                                                        ++shapeX;
                                                    }
                                                }
                                                ++shapeY;
                                            }

                                        }
                                        ++shapeZ;
                                    }

                                }

                                //check the type
                                int rowType =  order[typeNumber]+1;
                                float rowValue = rowValueHelper[order[typeNumber]];

                                //Save it
                                placements.add(shapeInContainer);
                                rowTypes.add(rowType);
                            }
                        }
                    }
                }
            }
            typeNumber++;
        }
    }

    /**
     * Method converting a three-dimensional input to a one dimensional array and creating the 2D inputMatrix
     */
    public void threeDToOneD(){
        //The array that contains all the 1D arrays
        boolean[][] result = new boolean[placements.size()][width*height*depth];

        //For every placement
        int placementNumber = 0;
        for(boolean[][][] placement : placements){
            boolean[] oneD = new boolean[width*height*depth];
            boolean[][] twoD = new boolean[height*depth][width];

            //Go from 3D to 2D by removing depth
            for(int z=0; z<depth; z++){
                boolean[][] layer = placement[z];

                //Stitch the rows to the end of the 2D array
                for(int y=0; y<height; y++){

                    //Stitch the row together
                    for(int x=0; x<width; x++){
                        twoD[z*height+y][x] = placement[z][y][x];
                    }
                }
            }

            //Go from 2D to 1D by removing height
            for(int y=0; y<height*depth; y++){
                for(int x=0; x<width; x++){
                    oneD[y*width+x] = twoD[y][x];
                }
            }

            //Add this shape to the array of 1D shapes
            result[placementNumber] = oneD;

            placementNumber++;
        }

        inputMatrix = result;
    }

    /**
     * Method checking if a shape fits at starting coordinates
     * @param startX: starting X-dimension (width)
     * @param startY: starting Y-dimension (height)
     * @param startZ: starting Z-dimension (depth)
     * @param shape: shape to be fitted
     * @return true if a shape can be fitted, false otherwise
     */
    public boolean fits(int startX, int startY, int startZ, boolean[][][] shape){
        int shapeWidth = shape[0][0].length;
        int shapeHeight = shape[0].length;
        int shapeDepth = shape.length;

        //check X
        if(startX+shapeWidth > width){
            return false;
        }

        //check Y
        if(startY+shapeHeight > height){
            return false;
        }

        //check z
        return startZ + shapeDepth <= depth;
    }

}
