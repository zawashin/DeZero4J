package dezero4j.step.step07;

import dezero4j.step.Step;

public class Step03 extends Step {

    public static void main(String[] args) {
        new Step03().calc();
    }

    public void calc() {
        Function A = new Square();
        Function B = new Exp();
        Function C = new Square();

        Variable x = new Variable(0.5);
        Variable a = A.forward(x);
        Variable b = B.forward(a);
        Variable y = C.forward(b);
        System.out.println(y.getData());
    }
}