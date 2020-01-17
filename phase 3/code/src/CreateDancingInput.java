import java.util.ArrayList;

public class CreateDancingInput {
    int[][][][] shapes;
    String type; //Parcels or Pentominoes
    ArrayList<Integer[][][]> placements = new ArrayList<Integer[][][]>();

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
            //For every depth layer
            for(int z=0; z < depth; z++){
                //For every y
                for(int y=0; y < height; y++){
                    //For every x
                    for(int x=0; x < width; x++){
                        //Check if it fits
                        if(fits(x, y, z)){
                            //Get shape inside container
                            Integer[][][] shapeInContainer = new Integer[depth][height][width];

                            //Place shape in container
                            for(int zContainer=0; z<depth; z++){
                                for(int yContainer=0; y < height; y++) {
                                    for (int xContainer = 0; x < width; x++) {
                                        //Check if the shape would be here
                                        System.out.println(x>= xContainer);
                                        System.out.println(x<=(xContainer+shape[0][0].length));
                                        System.out.println(y>= yContainer);
                                        System.out.println(yContainer+shape[0].length);
                                        System.out.println(z>= zContainer);
                                        System.out.println(z<=(zContainer+shape.length));
                                        if(x>= xContainer && x<=(xContainer+shape[0][0].length) && y>= yContainer && y<=(yContainer+shape[0].length) && z>= zContainer && z<=(zContainer+shape.length)){
//                                            int xShape = xContainer+shape[0][0].length - x;
//                                            int yShape = yContainer+shape[0].length - y;
//                                            int zShape = zContainer+shape.length - z;
//
//                                            int xIndex = xShape-1;
//                                            int yIndex = yShape-1;
//                                            int zIndex = zShape-1;

                                            if(shape[z][y][x] == 1){
                                                shapeInContainer[zContainer][yContainer][xContainer] = 1;
                                            } else {
                                                shapeInContainer[zContainer][yContainer][xContainer] = 0;
                                            }

                                        } else {
                                            shapeInContainer[zContainer][yContainer][xContainer] = 0;
                                        }
                                    }
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

    public boolean fits(int startX, int startY, int startZ){
        //check X
        if(startX+width > width){
            return false;
        }

        //check Y
        if(startY+height > height){
            return false;
        }

        //check z
        if(startZ+depth > depth){
            return false;
        }

        return true;
    }

}
