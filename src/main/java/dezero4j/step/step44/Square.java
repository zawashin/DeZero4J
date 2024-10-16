package dezero4j.step.step44;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Square extends Function {

    public Square() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.square(xs[0]);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        Variable x = inputs[0];
        gxs[0] = x.times(parameter(2, x.getShape())).times(gys[0]);
        gxs[0].setShape(inputs[0].getShape());
        return gxs;
    }

}
