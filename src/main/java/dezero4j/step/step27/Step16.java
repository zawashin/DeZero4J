package dezero4j.step.step27;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step16 extends Step {

    @Override
    public void calc() {
        Variable x = new Variable(new double[]{2.0});
        Function square = new Square();
        Variable a = square.forward(x)[0];
        Function plus = new Plus();
        Variable y = plus.forward(a.square(), a.square())[0];
        y.backward();
        System.out.println(y.getData());
        System.out.println(x.getGrad());
    }

    public static void main(String[] args) {
        new Step16().calc();
    }
}
