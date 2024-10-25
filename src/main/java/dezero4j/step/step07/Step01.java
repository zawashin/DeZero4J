package dezero4j.step.step07;

import dezero4j.step.step04.Variable;

public class Step01 {

    public static void main(String[] args) {
        double data = 1.0;
        Variable x = new Variable(data);
        System.out.println(x.getData());

        x.setData(2.0);
        System.out.println(x.getData());
    }
}