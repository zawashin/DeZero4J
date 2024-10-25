package dezero4jv1.step.step45;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Dot extends Function {

    public Dot() {
        numInputs = 2;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] ys = new Tensor[numOutputs];
        ys[0] = TensorOperator.dot(xs);
        return ys;
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Variable[] gxs = new Variable[numInputs];
        gxs[0] = gys[0].dot(inputs[1].transpose());
        gxs[1] = (inputs[0].transpose()).dot(gys[0]);
        return gxs;
    }
}
