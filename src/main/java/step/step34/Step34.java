package step.step34;

import step.Step;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class Step34 extends Step {

    public static void main(String[] args) {
        new Step34().calc();
    }

    @Override
    public void calc() {
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
        for (int i = 0; i <= numX; i++) {
            System.out.print(xArray[i] + "\t" + y.data.getValues()[i] + "\t");
            System.out.println(gx[1].getData().getValues()[i] + "\t" + gx[2].getData().getValues()[i]);
        }
    }

}
