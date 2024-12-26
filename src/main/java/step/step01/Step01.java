package step.step01;

import step.Step;

public class Step01 extends Step {

    public static void main(String[] args) {
        Step step = new Step01();
        step.calc();
    }

    public void calc() {
        double data = 1.0;
        Variable x = new Variable(data);
        System.out.println(x.getData());

        x.setData(2.0);
        System.out.println(x.getData());
    }
}