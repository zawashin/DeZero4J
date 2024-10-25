package dezero4jv1.step.step12;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable[] inputs;
    protected Variable[] outputs;

    public Variable[] forward(Variable... inputs) {
        double[][] xs = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            xs[i] = inputs[i].getData();
        }

        double[][] ys = forward(xs);
        Variable[] outputs = new Variable[ys.length];
        for (int i = 0; i < ys.length; i++) {
            outputs[i] = new Variable(ys[i]);
            outputs[i].setCreator(this);
        }

        this.inputs = inputs;
        this.outputs = outputs;
        return outputs;
    }

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public abstract double[][] forward(double[][] xs);

    public abstract double[][] backward(double[][] gys);
}
