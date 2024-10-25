package dezero4jv1.step.step36;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Fx extends LostFunction {

    public Variable[] forward(Variable... xs) {
        Variable[] lost = new Variable[1];
        int length = xs[0].getLength();
        //    z = x ** 4  - 2  * x ** 2
        lost[0] = (xs[0].pow(4)).minus(xs[0].pow(2).times(param(length, 2)));
        return lost;
    }
}
