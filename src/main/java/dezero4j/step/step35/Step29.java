package dezero4j.step.step35;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step29 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        xs[0] = new Variable(new double[]{2, 3});
        Fx fx = new Fx();
        Gx2 df2 = new Gx2();
        int maxIteration = 10;

        for (int i = 0; i < maxIteration; i++) {

            Variable y = fx.calc(xs);
            for (Variable x : xs) {
                x.cleaGrad();
            }
            y.backward();
            //Variable gx1 = new Variable(xs[0].getGrad());
            Variable gx1 = xs[0].getGrad();
            Variable dfx2 = df2.calc(xs[0]);

            System.out.print(xs[0] + "  " + gx1 + "  " + dfx2 + "\n");
            xs[0].getData().subtractAssign(gx1.getData().divide(dfx2.getData()));
        }
    }
}
