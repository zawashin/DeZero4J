package step.step38;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Fx extends MultivariateFunction {
    public Variable calc(Variable... xs) {
        //    z = x ** 4  - 2  * x ** 2
        shape = xs[0].getShape();
        return (xs[0].pow(4)).subtract(xs[0].pow(2).multiply(c(2)));
    }
}
