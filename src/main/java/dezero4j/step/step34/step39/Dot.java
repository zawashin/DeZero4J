package dezero4j.step.step34.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dot extends Function {

    public Dot() {
        numInputs = 2;
        numOutputs = 2;
    }


    @Override
    public Tensor[] forward(Tensor... xs) {
        double[] x0 = xs[0].values;
        int length = xs[0].length;
        double[] values = new double[length];
        return new Tensor[0];
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable x = inputs[0];
        return gxs;
    }
}
