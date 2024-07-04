package dezero4j.step.step02;

public class Square extends AbstractFunction {
    public double forward(double x) {
        return x * x;
    }

    public Variable forward(Variable input) {
        double[] x = input.getData();
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = x[i] * x[i];
        }
        Variable output = new Variable(y);
        return output;
    }
}