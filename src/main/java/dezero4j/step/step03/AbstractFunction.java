package dezero4j.step.step03;

public abstract class AbstractFunction {
    public abstract Variable forward(Variable input);

    protected abstract double forward(double x);
}

