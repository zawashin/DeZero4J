package dezero4j.step.step23;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step23 extends Step {
    public static void main(String[] args) {
        new Step23().calc();
    }

    @Override
    public void calc() {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(1.0);
        xs[1] = new Variable(2.0);
        System.out.println(xs[0].plus(xs[1]));
        System.out.println(xs[0].minus(xs[1]));
        System.out.println(xs[0].rminus(xs[1]));
        System.out.println(xs[0].times(xs[1]));
    }
}
