package dezero4j.step.step37_weakreference;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step37 extends Step {

    @Override
    public void calc() {
        Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println(x);
        Variable y = x.sin();
        System.out.println(y);
        y.backward(false, true);
        System.out.println(y);
        System.out.println(x.grad);
        Variable y2 = x.cos();
        System.out.println(y2);
        Variable c = new Variable(new double[][]{{10, 20, 30}, {40, 50, 60}});
        System.out.println(c);
        Variable t = x.plus(c);
        System.out.println(t);
    }

    public static void main(String[] args) {
        new Step37().calc();
    }
}
