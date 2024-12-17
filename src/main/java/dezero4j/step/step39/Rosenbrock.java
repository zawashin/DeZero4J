package dezero4j.step.step39;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Rosenbrock extends MultivariateFunction {

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{0, 2});
        Variable y = new Variable(new double[]{2, 1});
        Variable z = new Rosenbrock().calc(x, y);
        z.backward();
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }

    public Variable calc(Variable... xs) {
        shape = xs[0].getShape();
        // z = 100 * (x0 - x0**2)**2 + (x0 - 1)**2
        Variable z = c(100).multiply(xs[1].subtract(xs[0].pow(2)).pow(2)).add((xs[0].subtract(c(1)).pow(2)));
        return z;
    }
}
