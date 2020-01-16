//This file is for testing only
public class testUI {

    static int[][][] input = new int[Wrapper.CONTAINER_WIDTH/Wrapper.cellSize][Wrapper.CONTAINER_HEIGHT/Wrapper.cellSize][Wrapper.CONTAINER_DEPTH/Wrapper.cellSize];

    public static void giveInput(){

        for(int x=0; x<input.length; x++){
            for(int y=0; y<input[x].length; y++){
                for(int z=0; z<input[x][y].length; z++){
                    input[x][y][z] = (int) (Math.random()*4);
                }
            }
        }

        Wrapper.UIInput = input;
        FX3D.updateUI();
    }
}
