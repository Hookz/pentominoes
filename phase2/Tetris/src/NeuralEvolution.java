public class NeuralEvolution {
    public static int neuronsN=10;


}

class Neuron{
    private double[]weights;
    private double bias;
    private double value;

    public Neuron(double[]w,double b){
        weights=new double[Tetris.fieldHeight*Tetris.fieldWidth];
        System.arraycopy(w, 0, weights, 0, w.length);
        bias=b;
    }

    private int[] flattenField(){
        int[]ff=new int[Tetris.fieldHeight*Tetris.fieldWidth];
        for(int i=0;i<Tetris.field.length;i++)
            for(int j=0;j<Tetris.field[0].length;j++)
                ff[i+j]=Tetris.field[i][j];
        return ff;
    }

    private double sigmoid(double z){
        return 1/(1+Math.exp(-z));
    }

    public double evaluate(){
        int[]ff=flattenField();
        value=0;
        for(int i=0;i<ff.length;i++){
            value+=weights[i]*ff[i];
        }
        value+=bias;
        return sigmoid(value);
    }
}

class Output{
    private double[]weights;
    private double[]neurons;
    private double bias;
    private double value;

    public Output(double[]w,double b, double[]n){
        weights=new double[NeuralEvolution.neuronsN];
        neurons=new double[NeuralEvolution.neuronsN];
        System.arraycopy(w, 0, weights, 0, w.length);
        System.arraycopy(n, 0, neurons, 0, n.length);
        bias=b;
    }

    private double sigmoid(double z){
        return 1/(1+Math.exp(-z));
    }

    public double evaluate(){
        value=0;
        for(int i=0;i<neurons.length;i++){
            value+=weights[i]*neurons[i];
        }
        value+=bias;
        return sigmoid(value);
    }
}