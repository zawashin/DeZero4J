package dezero4j.step.step32;


/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Gx2 extends MultivariateFunction {

    public Variable calc(Variable... xs) {
        //    z = 12* x ** 2  - 4
        shape = xs[0].getShape();
        return xs[0].square().multiply(c(12)).subtract(c(4));
        /*
        int length = xs[0].getLength();
        double[] dx = new double[length];
        for(int i = 0; i < length; i++) {
            double x = xs[0].getData()[i];
            dx[i] = 12.0 * x * x - 4.0;
        }
        return new Variable(dx);

         */
    }
}
