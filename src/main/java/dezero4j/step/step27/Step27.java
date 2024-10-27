package dezero4j.step.step27;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step27 extends Step {

    @Override
    public void calc() {
        Variable x = new Variable(new double[]{Math.PI / 6.0, Math.PI / 2.0});

        // y = a * b + c
        Variable y = x.sin();
        System.out.println(y.getData());
        System.out.println(y);
        y.backward();

        System.out.println(x.getGrad());
        y = x.cos();
        x.cleaGrad();
        y.backward();
        System.out.println(y.getData());
        System.out.println(x.getGrad());
    }

    public static void main(String[] args) {
        new Step27().calc();
    }
}
