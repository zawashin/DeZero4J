package dezero4jv1.step.step46;


/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Gx2 extends LossFunction {

    @Override
    public Variable[] forward(Variable... xs) {
        Variable[] loss = new Variable[1];
        //int length = xs[0].getLength();
        int[] shape = xs[0].getShape();
        //    z = 12* x ** 2  - 4
        loss[0] = xs[0].pow(2).times(parameter(12, shape)).minus(parameter(4, shape));
        return loss;
    }
}
