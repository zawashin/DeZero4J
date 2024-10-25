package dezero4jv1.step.step37;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Transpose extends Function {

    public Transpose() {
        numInputs = 1;
        numOutputs = 1;
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        Tensor[] tensors = new Tensor[1];
        return new Tensor[0];
    }

    @Override
    public Variable[] backward(Variable... gys) {
        Tensor[] tensors = new Tensor[1];
        //gx[0].setShape(inputs[0].getShape());

        return new Variable[0];
    }
}
