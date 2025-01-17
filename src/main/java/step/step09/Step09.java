package step.step09;

import step.Step;

public class Step09 extends Step {

    public static void main(String[] args) {
        new Step09().calc();
    }

    @Override
    public void calc() {
        Variable x = new Variable(0.5);
        Variable y = new Square().forward(new Exp().forward(new Square().forward(x)));
        y.backward();
        System.out.println(y);
        System.out.println(x.getGrad());
        y = x.square().exp().square();
        System.out.println(y);
        y.backward();
        System.out.println(x.getGrad());
    }
}