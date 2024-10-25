package dezero4jv1.step.step43;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Fx extends LossFunction {

    public Variable[] forward(Variable... xs) {
        Variable[] loss = new Variable[1];
        //int length = xs[0].getLength();
        //    z = x ** 4  - 2  * x ** 2
        loss[0] = (xs[0].pow(4)).minus(xs[0].pow(2).times(parameter(2, xs[0].getShape())));
        return loss;
    }

}
