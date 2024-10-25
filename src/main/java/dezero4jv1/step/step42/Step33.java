package dezero4jv1.step.step42;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step33 {

    public static void main(String[] args) {
        Variable[] xs = new Variable[1];
        xs[0] = new Variable(new double[]{2, 3});
        //xs[0] = new Variable(new double[][]{{2, 3},{3,2}});
        Variable x = xs[0];
        Fx fx = new Fx();
        int maxIteration = 10;

        for (int i = 0; i < maxIteration; i++) {
            Variable y = fx.forward(xs)[0];
            x.clearGrad();
            y.backward(true, true);
            Variable gxs = x.getGrad();
            x.clearGrad();
            gxs.backward(false, true);
            Variable gxs2 = x.getGrad();

            System.out.println(x);
            System.out.println(gxs);
            System.out.print(gxs2 + "\n");
            Variable dx = gxs.div(gxs2);
            xs[0].minusAssign(dx);
        }
    }
}
