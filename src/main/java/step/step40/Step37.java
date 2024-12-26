package step.step40;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step37 extends Step {

    public static void main(String[] args) {
        new Step37().calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        Variable c = new Variable(new double[][]{{10, 20, 30}, {40, 50, 60}});
        System.out.println(c);
        Variable t = x.add(c);
        System.out.println(t);
        t.backward(false, true);
        System.out.println(x.grad);
        System.out.println(c.grad);
    }
}
