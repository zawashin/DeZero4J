package dezero4j.step.step02;

public abstract class AbstractFunction {
    public abstract  double forward(double x);

    public Variable forward(Variable input) {
        Variable output = forward(input);
        return output;
    }
}