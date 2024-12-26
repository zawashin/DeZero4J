package step.step44;

import java.io.Serializable;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Layer implements Serializable {
    protected int numInputs;
    protected int numOutputs;
    protected int numParams;
    protected Parameter[] params;
    protected boolean initialized = false;
    protected Function function;

    public Layer(int numOutputs) {
        this.numOutputs = numOutputs;
    }

    public Layer(int numInputs, int numOutputs) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
    }

    public abstract Variable[] forward(Variable... inputs);

    public void update(double learningRate) {
        for (Variable param : params) {
            param.subtractAssign(param.getGrad().multiply(learningRate));
        }
    }

    public void clearGrads() {
        for (Variable param : params) {
            param.clearGrad();
        }
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
