//This file is for testing only
public class test {

    static int[][][] input = new int[33][5][8];

    public static void giveInput(){

        for(int x=0; x<input.length; x++){
            for(int y=0; y<input[x].length; y++){
                for(int z=0; z<input[x][y].length; z++){
                    input[x][y][z] = (int) (Math.random()*4);
                }
            }
        }

        FX3D.updateUI(input);
    }
}
