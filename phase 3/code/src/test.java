//This file is for testing only
public class test {

    static int[][][] input = new int[Wrapper.CONTAINER_WIDTH/FX3D.cellSize][Wrapper.CONTAINER_HEIGHT/FX3D.cellSize][Wrapper.CONTAINER_DEPTH/FX3D.cellSize];

    public static void giveInput(){

        for(int x=0; x<input.length; x++){
            for(int y=0; y<input[x].length; y++){
                for(int z=0; z<input[x][y].length; z++){
                    input[x][y][z] = (int) (Math.random()*4);
                }
            }
        }

        FX3D.updateUI();

        Wrapper.UIInput = input;
    }
}
