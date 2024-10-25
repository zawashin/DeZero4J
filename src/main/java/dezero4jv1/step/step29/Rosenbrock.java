package dezero4jv1.step.step29;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Rosenbrock extends MultivariateFunction {
    public Variable forward(Variable... xs) {
        int length = xs[0].getData().length;
        // z = 100 * (x0 - x0**2)**2 + (x0 - 1)**2
        Variable z = constant(length, 100).times(xs[1].minus(xs[0].pow(2)).pow(2)).plus((xs[0].minus(constant(length, 1)).pow(2)));
        //Variable z1 = constant(length, 100).times(xs[1].minus(xs[0].pow(2)).pow(2));
        //Variable z2 = xs[0].minus(constant(length, 1)).pow(2);
        //z = z1.plus(z2);
        //System.out.println(z1.getData()[0]);
        //System.out.println(z2.getData()[0]);
        return z;
    }

    public static void main(String[] args) {
        Variable x = new Variable(new double[]{0});
        Variable y = new Variable(new double[]{2});
        Rosenbrock rosenbrock = new Rosenbrock();
        Variable z = rosenbrock.forward(x, y);
        z.backward();
        System.out.println(x.getGrad()[0]);
        System.out.println(y.getGrad()[0]);
    }
}
