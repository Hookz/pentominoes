package Phase3;
public class Pentominoe{
    public static void main(String[] args) {
        //int[][][][] array= {{{{1,1,1}},{{0,1,0}},{{0,1,0}}},{{{1,1,1},{0,1,0},{0,1,0}}}};
        //int[][][] z={{{1,1,1}},{{0,1,0}},{{0,1,0}}};
        int[][][] x = {{{1,1,1},{0,1,0},{0,1,0}}};
        int[][][][] arr = allRotations(x);
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                for(int k=0;k<arr[i][j].length;k++){
                    for(int l=0;l<arr[i][j][k].length;l++){
                        System.out.print(arr[i][j][k][l]);
                    }
                    System.out.println();
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }

}
     
    public static int[][][][] allRotations(int[][][] mat){
        int[][][][] result = new int[8][][][];
        int[][] arr2= mat[0];
        int[][][]arr={arr2};
        //first four rotations
        for(int i=0;i<4;i++){
            arr2=rotateMatrix(arr[0]);
            arr[0]=arr2;
            int[][][] temp= {arr2};
            result[i]=temp;
        }
        //second four rotations
        return result;
    }
    

    static int[][] rotateMatrix(int[][] mat) {
        int N = mat.length;
        int[][] s = new int[mat.length][];
        // Consider all squares one by one
        for (int x = 0; x < N / 2; x++) {
            // Consider elements in group of 4 in  
            // current square 
            for (int y = x; y < N-x-1; y++) {
                // store current cell in temp variable 
                int temp = mat[x][y]; 
       
                // move values from right to top 
                mat[x][y] = mat[y][N-1-x]; 
       
                // move values from bottom to right 
                mat[y][N-1-x] = mat[N-1-x][N-1-y]; 
       
                // move values from left to bottom 
                mat[N-1-x][N-1-y] = mat[N-1-y][x]; 
       
                // assign temp to left 
                mat[N-1-y][x] = temp; 
            } 
            
        } 
        s=mat;
        return s;
    }

}