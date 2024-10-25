package dezero4jv1.step.step42;

import java.util.Random;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class LinearRegression {

    public int iterMax;
    public double learningRate;
    Variable x0;
    Variable y0;

    Variable a;
    Variable b;
    Function mes = new MeanSquaredError();
    double xMean, yMean;
    double varianceX, varianceY, covariance;
    double sd1, sd2, r;

    public void setIterMax(int iterMax) {
        this.iterMax = iterMax;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public Variable getA() {
        return a;
    }

    public Variable getB() {
        return b;
    }

    public LinearRegression(double[] xs, double[] ys) {
        double[][] xt = new double[xs.length][1];
        double[][] yt = new double[ys.length][1];
        for(int i = 0; i < xs.length; i++) {
            xt[i][0] = xs[i];
            yt[i][0] = ys[i];
        }

        x0 = new Variable(xt);
        y0 = new Variable(yt);

        a = new Variable(0);
        b = new Variable(0);
    }

    public static double[][] generateSample(double a, double b, int n) {
        Random random = new Random(System.currentTimeMillis());
        double[][] xy = new double[2][n];
        for (int i = 0; i < n; i++) {
            xy[0][i] = (double) i/(n - 1);
            xy[1][i] = b + a * xy[0][i] + random.nextDouble();
        }
        return xy;
    }

    public void calc() {
        for (int i = 0 ; i < iterMax; i++) {
            Variable y = (x0.dot(a)).plus(b);
            //System.out.println("Predict = " + y.transpose());
            Variable loss = mes.forward(y, y0)[0];
            System.out.println("Loss = " + loss);
            a.clearGrad();
            b.clearGrad();
            loss.backward(false, true);
            Variable dw = a.grad;
            Variable db = b.grad;
            a.minusAssign(dw.times(learningRate));
            b.minusAssign(db.times(learningRate));
        }

        double[] x = x0.getDataAsArray();
        double[] y = y0.getGradAsArray();
        if(x.length != y.length) {
            throw new RuntimeException(TensorUtils.ERROR_LENGTH);
        }
        int length = x.length;
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        double sumYY = 0;

        for (int i = 0; i < length; i++) {
            sumX += x[i];
            sumY += y[i];
        }
        xMean = sumX / length;
        yMean = sumY / length;
        for (int i = 0; i < length; i++) {
            double dx = x[i] - xMean;
            double dy = y[i] - yMean;
            sumXX += dx * dx;
            sumYY += dy * dy;
            sumXY += dx * dy;
        }

        // 分散
        varianceX = sumXX / length;
        varianceY = sumYY / length;
        covariance = sumXY / length;

        // 標準偏差
        sd1 = Math.sqrt(varianceX);
        sd2 = Math.sqrt(varianceY);
        // 相関係数
        r = covariance / (sd1 * sd2);
    }

    public double getR() {
        return r;
    }

    public static void main(String[] args) {
        double[][] xy = generateSample(2.0, 5.0, 101);
        LinearRegression lr = new LinearRegression(xy[0], xy[1]);
        lr.setIterMax(100);
        lr.setLearningRate(0.1);

        lr.calc();
        System.out.println(lr.getA() + "\t" + lr.getB());
        System.out.println(lr.getR());
    }

}
