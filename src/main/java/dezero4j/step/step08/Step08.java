package dezero4j.step.step08;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step08 extends Step {

    @Override
    public void calc() {
        Variable x;
        Function A = new Square();
        Function B = new Exp();
        Function C = new Square();

        x = new Variable(0.5);
        Variable a = A.forward(x);
        Variable b = B.forward(a);
        Variable y = C.forward(b);

        y.setGrad(1.0);
        y.backward();
        System.out.println(x.getGrad());
    }

    public static void main(String[] args) {
        new Step08().calc();
    }

}
