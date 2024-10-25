package dezero4jv1.step.step37;

import java.util.Arrays;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable[] inputs;
    protected Variable[] outputs;
    protected int generation;
    protected int numInputs;
    protected int numOutputs;

    public Variable[] getInputs() {
        return inputs;
    }

    public Variable[] getOutputs() {
        return outputs;
    }

    public int getGeneration() {
        return generation;
    }

    public Variable param(int length, double data) {
        return new Variable(data, length);
    }

    public abstract Tensor[] forward(Tensor... xs);

    public Variable[] forward(Variable... inputs) {
        double[][] xsArray = new double[inputs.length][];
        Tensor[] xs = new Tensor[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            //xsArray[i] = inputs[i].getData();
            xs[i] = inputs[i].getData();
        }
        if (Config.getInstance().getParam().get("enable_backprop")) {
            generation = Arrays.stream(inputs).mapToInt(Variable::getGeneration).max().orElse(0);

            //double[][] ysArray = forward(xsArray);
            Tensor[] ys = forward(xs);
            Variable[] outputs = new Variable[ys.length];
            for (int i = 0; i < ys.length; i++) {
                //outputs[i] = new Variable(ysArray[i]);
                outputs[i] = new Variable(ys[i]);
                outputs[i].setCreator(this);
            }

            this.inputs = inputs;
            this.outputs = outputs;
        }
        return outputs;
    }

    public abstract Variable[] backward(Variable... gys);

}
