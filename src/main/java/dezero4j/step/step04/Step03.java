package dezero4j.step.step04;

import dezero4j.Step;

public class Step03 extends Step {

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

    public static void main(String[] args) {
        new Step03().calc();
    }
}