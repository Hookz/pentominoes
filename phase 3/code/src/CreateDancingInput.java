import java.util.ArrayList;

public class CreateDancingInput {
    int[][][][] shapes;
    String type; //Parcels or Pentominoes
    ArrayList<int[][][]> placements = new ArrayList<>();
    ArrayList<Integer> placementsOneD;

    int width = (Wrapper.CONTAINER_DEPTH/Wrapper.cellSize);
    int height = (Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize);
    int depth = (Wrapper.CONTAINER_WIDTH/Wrapper.cellSize);

    public CreateDancingInput(String type, int[][][][] shapes) {
        this.type = type;
        this.shapes = shapes;
    }

    public void createPlacements(){
        //For every shape
        for(int[][][] shape : shapes){
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
                                                    System.out.println("Z shape:" + shapeZ);
                                                    System.out.println("Y shape:" + shapeY);
                                                    System.out.println("X shape:" + shapeX);
                                                    if(shape[shapeZ][shapeY][shapeX] == 1){
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

    public void threeDToOneD(){
        //TODO
        int[] oneD = new int[1];
        int[][] twoD = new int[1][1];

    }

    public boolean fits(int startX, int startY, int startZ, int[][][] shape){
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
