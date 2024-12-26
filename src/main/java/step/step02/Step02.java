package step.step02;

import step.Step;

public class Step02 extends Step {

    public static void main(String[] args) {
        new Step02().calc();
    }

    public void calc() {
        double data = 10;
        Variable x = new Variable(data);
        Square f = new Square();
        Variable y = f.forward(x);
        System.out.println(y.getData());
    }
}