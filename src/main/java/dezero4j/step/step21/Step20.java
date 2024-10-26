package dezero4j.step.step21;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step20 extends Step {

    @Override
    public void calc() {
        Variable a = new Variable(new double[]{3.0});
        Variable b = new Variable(new double[]{2.0});
        Variable c = new Variable(new double[]{1.0});

        // y = a * b + c
        Variable y = (a.times(b)).plus(c);
        y.backward();

        System.out.println(y.getData());
        System.out.println(a.getGrad());
        System.out.println(b.getGrad());
    }

    public static void main(String[] args) {
        new Step20().calc();
    }
}
