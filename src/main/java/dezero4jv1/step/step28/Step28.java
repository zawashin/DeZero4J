package dezero4jv1.step.step28;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step28 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[2];
        xs[0] = new Variable(new double[]{0});
        xs[1] = new Variable(new double[]{2});
        Rosenbrock rosenbrock = new Rosenbrock();
        double learningRate = 0.001;
        int maxIteration = 50000;
        Variable z = null;

        for (int i = 0; i < maxIteration; i++) {

            z = rosenbrock.forward(xs);
            for (Variable x : xs) {
                x.cleaGrad();
            }
            z.backward();
            for (Variable x : xs) {
                //xs[j].getData()[0] = xs[j].getData()[0] - learningRate * xs[j].getGrad()[0];
                x.getData()[0] -= learningRate * x.getGrad()[0];
            }
            /*
            System.out.print(xs[0].getData()[0] + "\t");
            System.out.print(xs[1].getData()[0] + "\t");
            System.out.println(z.getData()[0]);

             */
        }
        System.out.print(xs[0].getData()[0] + "\t");
        System.out.print(xs[1].getData()[0] + "\t");
        System.out.println(z.getData()[0]);
    }
}
