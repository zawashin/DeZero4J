package dezero4j.step.step45;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Matyas extends MultivariateFunction {

    public static void main(String[] args) {
        Variable x = new Variable(1);
        Variable y = new Variable(1);
        Matyas matyas = new Matyas();
        Variable z = matyas.calc(x, y);
        System.out.println(z);
        z.backward();
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }

    @Override
    public Variable calc(Variable... xs) {
        //    z = 0.26 * (x ** 2 + y ** 2) - 0.48 * x * y
        // $f(x, y) = 0.26 \cdot (x^2 + y^2) - 0.48 \cdot x \cdot y$

        return xs[0].square().add(xs[1].square()).multiply(0.26).subtract(xs[0].multiply(xs[1]).multiply(0.48));
    }
}
