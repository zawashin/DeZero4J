package dezero4jv1;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Log extends Function {

    public Log() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.log(xs[0]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable x = inputs[0];
        gxs[0] = gys[0].div(x);
        gxs[0].setShape(inputs[0].getShape());

        return gxs;
    }

}
