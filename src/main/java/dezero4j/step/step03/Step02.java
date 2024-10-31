package dezero4j.step.step03;

import dezero4j.step.Step;
import dezero4j.step.step02.Square;
import dezero4j.step.step02.Variable;

public class Step02 extends Step {

    public static void main(String[] args) {
        new dezero4j.step.step02.Step02().calc();
    }

    public void calc() {
        double data = 10;
        dezero4j.step.step02.Variable x = new dezero4j.step.step02.Variable(data);
        dezero4j.step.step02.Square f = new Square();
        Variable y = f.forward(x);
        System.out.println(y.getData());
    }
}