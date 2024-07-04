package dezero4j.step.step03;

public class Exp extends AbstractFunction {
    @Override
    public Variable forward(Variable input) {
        double x = input.getData();
        double y = forward(x);
        return new Variable(y);
    }

    @Override
    protected double forward(double x) {
        return Math.exp(x);
    }
}

