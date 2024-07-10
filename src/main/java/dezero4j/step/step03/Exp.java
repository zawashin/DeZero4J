package dezero4j.step.step03;

public class Exp extends Function {
    @Override
    public Variable forward(Variable input) {
        double x = input.getData();
        double y = Math.exp(x);
        return new Variable(y);
    }
}

