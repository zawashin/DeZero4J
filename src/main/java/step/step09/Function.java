package step.step09;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable input;
    protected Variable output;

    public Variable forward(Variable input) {
        this.input = input;
        Tensor x = input.getData();
        Tensor y = forward(x);
        output = new Variable(y);
        output.setCreator(this);
        return output;
    }

    protected abstract Tensor forward(Tensor x);

    protected abstract Tensor backward(Tensor gy);

    public Variable getInput() {
        return input;
    }

    public Variable getOutput() {
        return output;
    }
}
