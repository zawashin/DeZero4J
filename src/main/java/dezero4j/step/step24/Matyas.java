package dezero4j.step.step24;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Matyas {

    public Variable forward(Variable... xs) {
        //    z = 0.26 * (x ** 2 + y ** 2) - 0.48 * x * y
        // $f(x, y) = 0.26 \cdot (x^2 + y^2) - 0.48 \cdot x \cdot y$

        return xs[0].square().plus(xs[1].square()).times(0.26).minus(xs[0].times(xs[1]).times(0.48));
    }

    public static void main(String[] args) {
        Variable x = new Variable(1);
        Variable y = new Variable(1);
        Matyas matyas = new Matyas();
        Variable z = matyas.forward(x, y);
        System.out.println(z);
        z.backward();
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }
}