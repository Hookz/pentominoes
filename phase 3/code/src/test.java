//This file is for testing only
public class test {
    public static void giveInput(){
        int[][][] input = new int[5][8][33];

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
