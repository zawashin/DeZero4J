package dezero4jv1.step.step29;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Fx extends MultivariateFunction {
    public Variable forward(Variable... xs) {
        //    z = x ** 4  - 2  * x ** 2
        int length = xs[0].getLength();
        /*
        double[] ys = new double[length];
        for(int i = 0; i < length; i++) {
            ys[i] = Math.pow(xs[0].getData()[i], 4) - 2.0 * Math.pow(xs[0].getData()[i], 2.0);
        }

        return new Variable(ys);
         */
        return (xs[0].pow(4)).minus(xs[0].pow(2).times(constant(length, 2)));
    }
}
