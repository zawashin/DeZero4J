package dezero4jv1.step.step27;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Matyas extends MultivariateFunction {
    public Variable forward(Variable... xs) {
        //    z = 0.26 * (x ** 2 + y ** 2) - 0.48 * x * y
        return xs[0].square().plus(xs[1].square()).times(0.26).minus(xs[0].times(xs[1]).times(0.48));
    }

    public static void main(String[] args) {
        Variable x = new Variable(1);
        Variable y = new Variable(1);
        Matyas matyas = new Matyas();
        Variable z = matyas.forward(x, y);
        z.backward();
        System.out.println(x.getGrad()[0]);
        System.out.println(y.getGrad()[0]);
    }
}
