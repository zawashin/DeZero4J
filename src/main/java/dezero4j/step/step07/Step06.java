package dezero4j.step.step07;

import dezero4j.Step;

public class Step06 extends Step {
    @Override
    public void calc() {
        Function A = new Square();
        Function B = new Exp();
        Function C = new Square();

        Variable x = new Variable(0.5);
        Variable a = A.forward(x);
        Variable b = B.forward(a);
        Variable y = C.forward(b);

        y.grad = 1.0;
        b.grad = C.backward(y.grad);
        System.out.println(b.grad);
        a.grad = B.backward(b.grad);
        System.out.println(a.grad);
    }

    public static void main(String[] args) {
        new Step06().calc();
    }

}
