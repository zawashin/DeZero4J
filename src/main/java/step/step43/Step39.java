package step.step43;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step39 extends Step {
    public static void main(String[] args) {
        new Step39().calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        Variable y = x.sum();
        System.out.println(y);
        y = x.sum(0);
        System.out.println(y);
        y = x.sum(1);
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        y = x.sum();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
    }
}
