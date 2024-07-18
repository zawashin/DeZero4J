package dezero4j.step.step07;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable input;
    protected Variable output;

    public Variable forward(Variable input) {
        double x = input.getData();
        double y = forward(x);
        Variable output = new Variable(y);
        output.setCreator(this);
        this.input = input;
        this.output = output;
        return output;
    }

    protected abstract double forward(double x);

    protected abstract double backward(double gy);

    public Variable getInput() {
        return this.input;
    }

    public Variable getOutput() {
        return this.output;
    }
}
