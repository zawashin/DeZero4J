package dezero4j.step.step04;

public abstract class Function {

    public Variable forward(Variable input) {
        double x = input.getData();
        double y = forward(x);
        return new Variable(y);
    }

    protected abstract double forward(double x);
}

