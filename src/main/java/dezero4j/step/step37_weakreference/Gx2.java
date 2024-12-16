package dezero4j.step.step37_weakreference;


/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Gx2 extends MultivariateFunction {

    public Variable calc(Variable... xs) {
        //    z = 12* x ** 2  - 4
        shape = xs[0].getShape();
        return xs[0].square().multiply(c(12)).subtract(c(4));
    }
}
