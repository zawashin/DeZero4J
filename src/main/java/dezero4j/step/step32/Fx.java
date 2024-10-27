package dezero4j.step.step32;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Fx extends MultivariateFunction {
    public Variable calc(Variable... xs) {
        //    z = x ** 4  - 2  * x ** 2
        shape = xs[0].getShape();
        return (xs[0].pow(4)).minus(xs[0].pow(2).times(c(2)));
    }
}
