package dezero4jv1.step.step41;


/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Gx2 extends LostFunction {

    @Override
    public Variable[] forward(Variable... xs) {
        Variable[] lost = new Variable[1];
        //int length = xs[0].getLength();
        int[] shape = xs[0].getShape();
        //    z = 12* x ** 2  - 4
        lost[0] = xs[0].pow(2).times(param(12, shape)).minus(param(4, shape));
        return lost;
    }
}
