package step.step40;

import tensor4j.Tensor;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step33 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        xs[0] = new Variable(new double[]{2, 3});
        Variable x = xs[0];
        Fx fx = new Fx();
        int maxIteration = 10;

        for (int i = 0; i < maxIteration; i++) {
            Variable y = fx.calc(xs);
            x.clearGrad();
            y.backward(true, true);
            Variable gx = x.getGrad().clone();
            x.clearGrad();
            gx.backward(false, true);
            Variable gx2 = x.getGrad();

            System.out.print(x);
            System.out.print(gx + "  ");
            System.out.print(gx2 + "\n");
            Tensor dx = gx.getData().divide(gx2.getData());

            //xs[0].getData().minusAssign(gx.getData().divide(gx2.getData()));
            xs[0].minusAssign(gx.divide(gx2));
            System.out.print(x);

        }
    }
}
