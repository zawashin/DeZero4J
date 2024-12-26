package step.step23;

import step.Step;

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
        System.out.println(xs[0].add(xs[1]));
        System.out.println(xs[0].subtract(xs[1]));
        System.out.println(xs[0].rminus(xs[1]));
        System.out.println(xs[0].multiply(xs[1]));
    }
}
