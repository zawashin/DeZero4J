package dezero4jv1.step.step44;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Test {
    public static void main(String[] args) {
        {
            System.out.println("*** Rosenbrock ***");
            Variable x = new Variable(new double[]{0});
            Variable y = new Variable(new double[]{2});
            System.out.println(x);
            System.out.println(y);
            Rosenbrock rosenbrock = new Rosenbrock();
            Variable z = rosenbrock.calcLoss(x, y);
            z.backward(false, true);
            System.out.println(x.getGrad());
            System.out.println(y.getGrad());
        }
        {
            System.out.println("*** Goldstein ***");
            Variable x = new Variable(new double[]{1});
            Variable y = new Variable(new double[]{1});
            Goldstein goldstein = new Goldstein();
            Variable z = goldstein.calcLoss(x, y);
            z.backward(false, true);
            System.out.println(x.getGrad());
            System.out.println(y.getGrad());
        }
        {
            System.out.println("*** Step 33 ***");
            Variable[] xs = new Variable[1];
            xs[0] = new Variable(new double[]{2, 3});
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
        {
            System.out.println("*** Step 33-2 ***");
            Variable[] xs = new Variable[1];
            xs[0] = new Variable(new double[][]{{2, 3}, {3, 5}});
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
                System.out.println(gxs2);
                Variable dx = gxs.div(gxs2);
                xs[0].minusAssign(dx);
            }
        }
        {
            System.out.println("*** Step 34 ***");
            int numX = 100;
            double[] xArray = new double[numX + 1];
            double x0 = -7;
            double dx = Math.abs(2.0 * x0) / numX;
            for (int i = 0; i < numX; i++) {
                xArray[i] = x0 + dx * i;
            }
            xArray[numX] = -x0;
            Variable x = new Variable(xArray);
            Variable y = x.sin();
            int numDiff = 2; // 微分の回数
            Variable[] gxs = new Variable[numDiff + 1];
            gxs[0] = y;
            y.backward(true, true);
            for (int i = 1; i <= numDiff; i++) {
                gxs[i] = x.getGrad();
                x.clearGrad();
                gxs[i].backward(false, true);
            }
            for (int i = 0; i <= numX; i += 10) {
                System.out.print(xArray[i] + "\t" + y.data.values[i] + "\t");
                System.out.println(gxs[1].data.values[i] + "\t" + gxs[2].data.values[i]);
            }

        }
        {
            System.out.println("*** Step 35 ***");
            Variable x = new Variable(1);
            System.out.println(x);
            Variable y = x.tanh();
            System.out.println(y);
            x.clearGrad();
            y.backward(false, true);
            Variable gxs = x.grad;
            System.out.println(gxs);
            x.clearGrad();
            // retaiGrad = trueだと計算が不正確
            gxs.backward(false, true);
            Variable gxs2 = x.grad;
            System.out.println(gxs2);
        }
        {
            System.out.println("*** Step 36 ***");
            Variable x = new Variable(2.0);
            Variable y = x.pow(2);
            y.backward(false, true);
            Variable gxs = x.grad;
            x.clearGrad();
            Variable z = gxs.pow(3).plus(y);
            z.backward(false, true);
            System.out.println(x.data);
            System.out.println(y.data);
            System.out.println(gxs.data);
            System.out.println(x.grad);

        }
        {
            System.out.println("*** Step 37 ***");
            Variable x = new Variable(new double[][]{{1, 2, 3}, {4, 5, 6}});
            System.out.println(x);
            Variable y = x.sin();
            System.out.println(y);
            y.backward(false, true);
            System.out.println(x.grad);
            Variable y2 = x.cos();
            System.out.println(y2);
            Variable c = new Variable(new double[][]{{10, 20, 30}, {40, 50, 60}});
            System.out.println(c);
            Variable t = x.plus(c);
            System.out.println(t);
            y = t.sum();
            System.out.println(y);
        }
    }
}

