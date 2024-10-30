package dezero4j.step.step33_2;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step14 extends Step {

    @Override
    public void calc() {
        Variable x = new Variable(new double[]{3.0});
        Function plus = new Plus();
        Function plus2 = new Plus();
        Variable y = plus.forward(x, x)[0];
        y.backward();
        System.out.println(x.getGrad());
        x.cleaGrad();
        y = plus2.forward(plus.forward(x, x)[0], x)[0];
        y.backward();
        System.out.println(x.getGrad());

        x = new Variable(new double[]{3.0});
        y = x.plus(x);
        y.backward();
        System.out.println(x.getGrad());
        x.cleaGrad();
        y = x.plus(x).plus(x);
        y.backward();
        System.out.println(x.getGrad());
    }

    public static void main(String[] args) {
        new Step14().calc();
    }

}
