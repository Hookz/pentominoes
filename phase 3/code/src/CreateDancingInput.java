import java.util.ArrayList;

public class CreateDancingInput {
    boolean[][][][][] shapes;
    String type; //Parcels or Pentominoes

    ArrayList<int[][][]> placements = new ArrayList<>();
    float[] rowValues;
    int[] rowType;

    int width = (Wrapper.CONTAINER_DEPTH/Wrapper.cellSize);
    int height = (Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize);
    int depth = (Wrapper.CONTAINER_WIDTH/Wrapper.cellSize);

    public CreateDancingInput(String type) {
        this.type = type;
    }

    public void selectInput(){
        if(type.equals("Parcels")){

            boolean[][][][] A = ShapesAndRotations.getABoolean();
            boolean[][][][] B = ShapesAndRotations.getBBoolean();
            boolean[][][][] C = ShapesAndRotations.getCBoolean();

            shapes = new boolean[][][][][]{A, B, C};
            rowValues = new float[A.length+B.length+C.length];
            rowType = new int[A.length+B.length+C.length];

            for(int i=0; i<rowValues.length; i++){
                if(i<A.length){
                    rowValues[i] = Wrapper.inputDetails[0].value;
                } else if(i<B.length){
                    rowValues[i] = Wrapper.inputDetails[1].value;
                } else if(i<C.length){
                    rowValues[i] = Wrapper.inputDetails[2].value;
                }
            }

            for(int i=0; i<rowType.length; i++){
                if(i<A.length){
                    rowType[i] = 1;
                } else if(i<B.length){
                    rowType[i] = 2;
                } else if(i<C.length){
                    rowType[i] = 3;
                }
            }

        } else if (type.equals("Pentominoes")){

            boolean[][][][] L = ShapesAndRotations.getLBoolean();
            boolean[][][][] P = ShapesAndRotations.getPBoolean();
            boolean[][][][] T = ShapesAndRotations.getTBoolean();

            shapes = new boolean[][][][][]{L, P, T};
            rowValues = new float[L.length+P.length+T.length];
            rowType = new int[A.length+B.length+C.length];

            for(int i=0; i<rowValues.length; i++){
                if(i<L.length){
                    rowValues[i] = Wrapper.inputDetails[0].value;
                } else if(i<P.length){
                    rowValues[i] = Wrapper.inputDetails[1].value;
                } else if(i<T.length){
                    rowValues[i] = Wrapper.inputDetails[2].value;
                }
            }

            for(int i=0; i<rowType.length; i++){
                if(i<L.length){
                    rowType[i] = 1;
                } else if(i<P.length){
                    rowType[i] = 2;
                } else if(i<T.length){
                    rowType[i] = 3;
                }
            }
        }
    }

    public void createPlacements(){
        //For every type of shape
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
                                int[][][] shapeInContainer = new int[depth][height][width];

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
                                                            shapeInContainer[zContainer][yContainer][xContainer] = 1;
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

                                //Save it
                                placements.add(shapeInContainer);
                            }
                        }
                    }
                }
            }
        }
    }

    public void threeDToOneD(){
        //The array that contains all the 1D arrays
        int[][] result = new int[placements.size()][width*height*depth];

        //For every placement
        int placementNumber = 0;
        for(int[][][] placement : placements){
            int[] oneD = new int[width*height*depth];
            int[][] twoD = new int[height*depth][width];

            //Go from 3D to 2D by removing depth
            for(int z=0; z<depth; z++){
                int[][] layer = placement[z];

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
    }

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
        if(startZ+shapeDepth > depth){
            return false;
        }

        return true;
    }

}
