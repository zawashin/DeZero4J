package step.step43;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step41 extends Step {
    public static void main(String[] args) {
        new Step41().calc();
    }

    @Override
    public void calc() {
        System.out.println("Dot");
        Variable[] xs = new Variable[2];
        Variable y;
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[1] = new Variable(new double[][]{{1, 2}, {5, 6}, {9, 10}});
        System.out.println("2x2");
        System.out.println(xs[0]);
        System.out.println(xs[1]);
        y = xs[0].matmul(xs[1]);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(xs[0].grad);
        System.out.println(xs[1].grad);

        xs = new Variable[3];
        xs[0] = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        xs[1] = new Variable(new double[][]{{1, 2}, {5, 6}, {9, 10}});
        xs[2] = new Variable(new double[][]{{1, 2, 1}, {5, 6, 1}});
        System.out.println("2x2");
        //System.out.println(xs[0]);
        //System.out.println(xs[1]);
        y = xs[0].matmul(xs[1]).matmul(xs[2]);
        System.out.println(y);
        y.backward(true, true);
        System.out.println(xs[0].grad);
        //System.out.println(xs[1].grad);
        System.out.println(xs[2].grad);
        xs[0] = new Variable(new double[][]{{4, 5, 6}});
        xs[0] = new Variable(new double[]{4, 5, 6});
        xs[1] = new Variable(new double[][]{{1, 2}, {5, 6}, {9, 10}});
        xs[2] = new Variable(new double[][]{{1, 2, 1}, {5, 6, 1}});
        System.out.println("1x2");
        //System.out.println(xs[0]);
        //System.out.println(xs[1]);
        y = xs[0].matmul(xs[1]).matmul(xs[2]);
        System.out.println(y);
        y.backward(true, true);
        System.out.println(xs[0].grad);
        //System.out.println(xs[1].grad);
        System.out.println(xs[2].grad);
    }
}
