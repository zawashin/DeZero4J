package dezero4j.step.step02;

public class Square extends Function {

    @Override
    public Variable forward(Variable input) {
        double x = input.getData();
        double y = x * x;
        return new Variable(y);
    }
}