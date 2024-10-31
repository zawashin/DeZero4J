package dezero4j.step.step37;

import dezero4j.step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step28 extends Step {

    public static void main(String[] args) {
        new Step28().calc();
    }

    @Override
    public void calc() {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[]{0});
        xs[1] = new Variable(new double[]{2});
        Rosenbrock rosenbrock = new Rosenbrock();
        double learningRate = 0.001;
        int maxIteration = 1000;
        Variable z = null;

        for (int i = 0; i < maxIteration; i++) {

            z = rosenbrock.calc(xs);
            for (Variable x : xs) {
                x.cleaGrad();
            }
            z.backward();
            for (Variable x : xs) {
                x.minusAssign(new Variable(x.getGrad().times(learningRate)));
            }
            System.out.print(xs[0] + "\t");
            System.out.print(xs[1] + "\t");
            System.out.println(z);
        }
        System.out.print(xs[0].getData() + "\t");
        System.out.print(xs[1].getData() + "\t");
        System.out.println(z);
    }

}
