package dezero4j.step.step28;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step13 extends Step {

    public static void main(String[] args) {
        new Step13().calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(new double[]{2.0});
        Variable y = new Variable(new double[]{3.0});

        Function square = new Square();
        Function square2 = new Square();
        Function plus = new Plus();
        Variable z = plus.forward(square.forward(x)[0], square2.forward(y)[0])[0];

        z.backward();
        System.out.println(z.getData());
        System.out.println(x.getGrad());
        System.out.println(y.getGrad());
    }
}
