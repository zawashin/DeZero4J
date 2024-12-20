package dezero4j.step.step41;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step40 extends Step {
    @Override
    public void calc() {
        System.out.println("Multiply");
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[1] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println("2x2");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        Variable y = xs[0].multiply(xs[1]);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
        xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[1] = new Variable(3);
        System.out.println("2x0");
        System.out.println(xs[0]);
        System.out.println(xs[1]);

        y = xs[0].multiply(xs[1]);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
        xs = new Variable[2];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 7}});
        xs[1] = new Variable(new double[]{1, 2, 4});
        System.out.println("2x1");
        System.out.println(xs[0]);
        System.out.println(xs[1]);

        y = xs[0].multiply(xs[1]);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
        xs = new Variable[2];
        System.out.println("Divide");
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 8}});
        xs[1] = new Variable(3);
        y = xs[0].divide(xs[1]);
        y.backward(false, true);
        System.out.println("x:[2nd]/[0th]");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        System.out.println(y);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 7}});
        xs[1] = new Variable(new double[]{1, 2, 4});
        y = xs[0].divide(xs[1]);
        y.backward(false, true);
        System.out.println("x:[2nd]/[1st]");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        System.out.println(y);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);

        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 8}});
        xs[1] = new Variable(new double[][]{{1, 2, 4}, {1, 2, 4}});
        y = xs[0].divide(xs[1]);
        y.backward(false, true);
        System.out.println("x");
        System.out.println("x:[2nd]/[2nd]");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);
    }

    public static void main(String[] args) {
        new Step40().calc();
    }
}
