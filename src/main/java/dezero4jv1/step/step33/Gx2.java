package dezero4jv1.step.step33;


/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Gx2 extends LostFunction {
    public Variable[] forward(Variable... xs) {
        Variable[] lost = new Variable[1];
        int length = xs[0].getLength();
        //    z = 12* x ** 2  - 4
        lost[0] = xs[0].pow(2).multiply(param(length, 12)).minus(param(length, 4));
        return lost;
    }
}
