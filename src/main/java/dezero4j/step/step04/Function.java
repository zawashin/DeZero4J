package dezero4j.step.step04;

abstract class Function {
    protected Variable input;
    protected Variable output;

    public Variable forward(Variable input) {
        double x = input.getData();
        double y = forward(x);
        output = new Variable(y);
        this.input = input;
        return output;
    }

    protected abstract double forward(double x);
}

