package dezero4jv1.step.step37;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Reshape extends Function {
    int[] oldShape;
    int[] newShape;

    public Reshape() {
        numInputs = 1;
        numOutputs = 1;
    }

    public Reshape(int[] newShape) {
        numInputs = 1;
        numOutputs = 1;
        this.newShape = newShape.clone();
    }

    public void setShape(int[] newShape) {
        this.newShape = newShape.clone();
    }

    @Override
    public Tensor[] forward(Tensor... xs) {
        return new Tensor[0];
    }

    @Override
    public Variable[] backward(Variable... gys) {
        return new Variable[0];
    }
}
