package dezero4j.step.step07;

import dezero4j.step.step02.Square;
import dezero4j.step.step02.Variable;

public class Step02 {
    public static void main(String[] args) {
        double data = 10;
        Variable x = new Variable(data);
        Square f = new Square();
        Variable y = f.forward(x);
        System.out.println(y.getData());
    }
}