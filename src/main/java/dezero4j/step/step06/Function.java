package dezero4j.step.step06;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class Function {
    protected Variable input;

    public abstract Variable forward(Variable input);

    protected abstract double forward(double x);

    protected abstract double backward(double gy);
}
