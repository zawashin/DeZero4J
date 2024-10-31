package dezero4j.step.step34;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step21 extends Step {

    public static void main(String[] args) {
        new Step21().calc();
    }

    @Override
    public void calc() {
        Variable a = new Variable(new double[]{3.0, 4, 3});
        Variable b = new Variable(new double[]{2.0, 3, 4});

        // y = a * b + c
        Variable y = (a.times(b)).plus(1);
        y.backward();

        System.out.println(y.getData());
        System.out.println(a.getGrad());
        System.out.println(b.getGrad());
    }
}
