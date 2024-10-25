package dezero4jv1.step.step41;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Matyas extends LostFunction {

    public Variable[] forward(Variable... xs) {
        Variable[] lost = new Variable[1];
        int length = xs[0].getLength();
        lost[0] = xs[0].pow(2).plus(xs[1].pow(2)).times(0.26).minus(xs[0].times(xs[1]).times(0.48));
        //    z = 0.26 * (x ** 2 + y ** 2) - 0.48 * x * y
        return lost;
    }

    public static void main(String[] args) {
        Variable x = new Variable(1.0);
        Variable y = new Variable(1.0);
        Matyas matyas = new Matyas();
        Variable z = matyas.calc(x, y);
        z.backward(true, true);
        System.out.println(x.getGrad().getData().values[0]);
        System.out.println(y.getGrad().getData().values[0]);
    }

}
