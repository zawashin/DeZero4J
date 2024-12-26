package step.step11;

import step.Step;

public class Step11 extends Step {

    public static void main(String[] args) {

    }

    @Override
    public void calc() {
        Variable x0 = new Variable(new double[]{2});
        Variable x1 = new Variable(new double[]{3});
        Variable[] xs = new Variable[]{x0, x1};

        Function f = new Add();
        Variable[] ys = f.forward(xs);
        Variable y = ys[0];

        System.out.println(y);
        y = x0.add(x1);
        System.out.println(y);
    }
}