package step.step41;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step38 extends Step {
    public static void main(String[] args) {
        new Step38().calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        Variable y = x.reshape(6);
        System.out.println(y);
        y.backward(false, true);
        System.out.println(x.grad);
        x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        y = x.transpose();

        System.out.println(y);
    }
}
