package dezero4j.step.step33;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step33 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        xs[0] = new Variable(new double[]{2, 3});
        Variable x = xs[0];
        Fx fx = new Fx();
        int maxIteration = 1000;

        for (int i = 0; i < maxIteration; i++) {
            Variable y = fx.calc(xs);
            x.clearGrad();
            y.backward(true, true);
            Variable gx = x.getGrad();
            x.clearGrad();
            gx.backward(false, true);
            Variable gx2 = x.getGrad();

            System.out.print(x);
            System.out.print(gx + "  ");
            System.out.print(gx2 + "\n");
            xs[0].getData().minusAssign(gx.getData().div(gx2.getData()));
            /*
            for (int j = 0; j < x.getData().length; j++) {
                xs[0].getData()[j] -= gx.getData()[j] / gx2.getData()[j];
            }

             */

        }
    }
}
