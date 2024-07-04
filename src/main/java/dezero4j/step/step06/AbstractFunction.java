package dezero4j.step.step06;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
abstract class AbstractFunction {
    Variable input;

    public abstract Variable forward(Variable input);

    public abstract double forward(double x);

    public abstract double backward(double gy);
}
