package dezero4j.step.step14;

import dezero4j.Step;

public class Step09 extends Step {

    @Override
    public void calc() {
        Variable x = new Variable(0.5);
        Variable y = new Square().forward(new Exp().forward(new Square().forward(x)))[0];
        y.backward();
        System.out.println(y);
        System.out.println(x.getGrad());
        // 改めて作らないと微分値が不適切になる
        x = new Variable(0.5);
        y = x.square().exp().square();
        System.out.println(y);
        y.backward();
        System.out.println(x.getGrad());
    }

    public static void main(String[] args) {
         new Step09().calc();
    }
}