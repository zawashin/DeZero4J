package dezero4j.step.step06;

import dezero4j.step.Step;

public class Step02 extends Step {

    public void calc() {
        double data = 10;
        Variable x = new Variable(data);
        Square f = new Square();
        Variable y = f.forward(x);
        System.out.println(y.getData());
    }

    public static void main(String[] args) {
        new dezero4j.step.step02.Step02().calc();
    }
}