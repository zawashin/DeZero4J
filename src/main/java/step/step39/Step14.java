package step.step39;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step14 extends Step {

    public static void main(String[] args) {
        new Step14().calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(new double[]{3.0});
        Function add = new Add();
        Function plus2 = new Add();
        Variable y = add.forward(x, x)[0];
        y.backward();
        System.out.println(x.getGrad());
        x.cleaGrad();
        y = plus2.forward(add.forward(x, x)[0], x)[0];
        y.backward();
        System.out.println(x.getGrad());

        x = new Variable(new double[]{3.0});
        y = x.add(x);
        y.backward();
        System.out.println(x.getGrad());
        x.cleaGrad();
        y = x.add(x).add(x);
        y.backward();
        System.out.println(x.getGrad());
    }

}
