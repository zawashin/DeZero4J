package dezero4j.step.step03;

public class Exp extends Function {
    @Override
    public Variable forward(Variable input) {
        double x = input.getData();
        return new Variable(Math.exp(x));
    }
}

