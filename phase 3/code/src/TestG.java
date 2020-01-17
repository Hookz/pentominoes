public class TestG {

    public static void main(String[] args) {
        int[][][] container = new int[Wrapper.CONTAINER_WIDTH*2][Wrapper.CONTAINER_HEIGHT*2][Wrapper.CONTAINER_DEPTH*2];

        initContainer(container);

    }
    public static void initContainer(int [][][] container){
        for(int i = 0 ; i< container.length ; i++){
            for(int j = 0 ;j<container[i].length ; j++ ){
                for(int k=0;k<container[i][j].length;k++){
                    container[i][j][k] = 0;
                }
            }
        }

        for(int i = 0 ; i< container.length ; i++){
            for(int j = 0 ;j<container[i].length ; j++ ){
                for(int k=0;k<container[i][j].length;k++){
                    System.out.print(container[i][j][k]);
                }
            }
        }

    }
}
