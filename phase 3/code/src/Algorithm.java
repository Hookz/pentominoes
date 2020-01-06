public class Algorithm {
    //TODO create the algorithm that actually solves the problem, make a pentomino and parcel option

    //create a container (as ints) for which the problem can be solved, not for the UI (this is done separately)
    //TODO save items as follows: typeId + uniqueId, this would allow you to seperate all of the elements.
    int[][][] container = new int[Wrapper.CONTAINER_WIDTH][Wrapper.CONTAINER_HEIGHT][Wrapper.CONTAINER_DEPTH];

    public void startAlgorithm(){
        System.out.println("Starting algorithm");
        //TODO setup algorithm
        runAlgorithm();
    }

    public void runAlgorithm(){
        //TODO run algorithm

    }

    public void updateUI(){
        System.out.println("The algorithm is done, the UI will be updated");
        //TODO update UI
    }
}
