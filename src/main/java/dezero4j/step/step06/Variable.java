package dezero4j.step.step06;

public class Variable {
    protected double data;
    protected double grad;

    public Variable(double data) {
        this.data = data;
        this.grad = 0.0;
    }
}

