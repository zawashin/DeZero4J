package dezero4j.step.step11;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable[] inputs;
    protected Variable[] outputs;

    public Variable[] forward(Variable[] inputs) {
        this.inputs = inputs;
        Tensor[] xs = new Tensor[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            xs[i] = inputs[i].getData();
        }

        Tensor[] ys = forward(xs);
        outputs = new Variable[ys.length];
        for (int i = 0; i < ys.length; i++) {
            outputs[i] = new Variable(ys[i]);
            outputs[i].setCreator(this);
        }
        return outputs;
    }
    public abstract Tensor[] forward(Tensor[] xs);

    public abstract Tensor[] backward(Tensor[] gys);

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable getInput(int n) {
        return inputs[n];
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public Variable getOutput(int n) {
        return outputs[n];
    }
}
