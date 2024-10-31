package dezero4j.step.step37;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step36 extends Step {

    @Override
    public void calc() {
        Variable x = new Variable(2.0);
        Variable y = x.pow(2);
        y.backward(false, true);
        Variable gx = x.grad;
        x.clearGrad();
        Variable z = gx.pow(3).plus(y);
        z.backward(false, true);
        System.out.println(x.data);
        System.out.println(y.data);
        System.out.println(gx.data);
        System.out.println(x.grad);
    }

    public static void main(String[] args) {
        new Step36().calc();
    }
}