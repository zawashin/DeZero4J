package dezero4jv1.step.step37;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Test {
    public static void main(String[] args) {
        {
            System.out.println("*** Rosenbrock ***");
            Variable x = new Variable(new double[]{0});
            Variable y = new Variable(new double[]{2});
            Rosenbrock rosenbrock = new Rosenbrock();
            Variable z = rosenbrock.calc(x, y);
            z.backward(false, true);
            System.out.println(x.getGrad());
            System.out.println(y.getGrad());
        }
        {
            System.out.println("*** Goldstein ***");
            Variable x = new Variable(new double[]{1});
            Variable y = new Variable(new double[]{1});
            Goldstein goldstein = new Goldstein();
            Variable z = goldstein.calc(x, y);
            z.backward(false, true);
            System.out.println(x.getGradAsArray()[0]);
            System.out.println(y.getGradAsArray()[0]);
        }
        {
            System.out.println("*** Step 33 ***");
            Variable[] xs = new Variable[1];
            xs[0] = new Variable(new double[]{2, 3});
            Variable x = xs[0];
            Fx fx = new Fx();
            int maxIteration = 10;

            for (int i = 0; i < maxIteration; i++) {
                Variable y = fx.calc(xs);
                x.clearGrad();
                y.backward(true, true);
                Variable gx = x.getGrad();
                x.clearGrad();
                gx.backward(false, true);
                Variable gx2 = x.getGrad();

                System.out.println(x);
                System.out.println(gx);
                System.out.print(gx2 + "\n");
                /*
                for (int j = 0; j < x.getData().length; j++) {
                    xs[0].getData().values[j] -= gx.getData().values[j] / gx2.getData().values[j];
                }

                 */
                Variable dx = gx.div(gx2);
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
                Variable y = fx.calc(xs);
                x.clearGrad();
                y.backward(true, true);
                Variable gx = x.getGrad();
                x.clearGrad();
                gx.backward(false, true);
                Variable gx2 = x.getGrad();

                System.out.println(x);
                System.out.println(gx);
                System.out.println(gx2 + "\n");
                Variable dx = gx.div(gx2);
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
            Variable[] gx = new Variable[numDiff + 1];
            gx[0] = y;
            y.backward(true, true);
            for (int i = 1; i <= numDiff; i++) {
                gx[i] = x.getGrad();
                x.clearGrad();
                gx[i].backward(false, true);
            }
            for (int i = 0; i <= numX; i += 10) {
                System.out.print(xArray[i] + "\t" + y.data.values[i] + "\t");
                System.out.println(gx[1].data.values[i] + "\t" + gx[2].data.values[i]);
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
            Variable gx = x.grad;
            System.out.println(gx);
            x.clearGrad();
            // retaiGrad = trueだと計算が不正確
            gx.backward(false, true);
            Variable gx2 = x.grad;
            System.out.println(gx2);

        }
        {
            System.out.println("*** Step 36 ***");
            Variable x = new Variable(2.0);
            Variable y = x.pow(2);
            y.backward(false, true);
            Variable gx = x.grad;
            x.clearGrad();
            Variable z = gx.pow(3).plus(y);
            z.backward(false, true);
            System.out.println(x.data);
            System.out.println(y.data);
            System.out.println(gx.data);
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
            //y = t.sum();
            y.backward(false, true);
            //System.out.println(y.grad);
            //System.out.println(t.grad);
            //System.out.println(c.grad);

        }
    }
}

