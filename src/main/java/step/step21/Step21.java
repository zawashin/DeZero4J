package step.step21;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step21 extends Step {

    public static void main(String[] args) {
        new Step21().calc();
    }

    @Override
    public void calc() {
        Variable a = new Variable(new double[]{3.0});
        Variable b = new Variable(new double[]{2.0});
        Variable c = new Variable(new double[]{1.0});

        // y = a * b + c
        Variable y = (a.multiply(b)).add(1);
        y.backward();

        System.out.println(y.getData());
        System.out.println(a.getGrad());
        System.out.println(b.getGrad());
    }
}
