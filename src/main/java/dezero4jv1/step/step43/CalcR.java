package dezero4jv1.step.step43;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class CalcR {

    static double sqr(double x) {

        return x * x;

    }


    public static void main(String[] args) {

        //
        //                      データの設定
        //
        double[][] data = {{1, 5},
                {2, 4},
                {3, 2},
                {5, 1}};
        int n = data.length;
        //
        //                      和を求めて平均を計算する
        //
        double sum1 = 0.0, sum2 = 0.0;

        for (int i = 0; i < n; i++) {
            sum1 += data[i][0];
            sum2 += data[i][1];
            System.out.println("data[" + i + "][0] = " + data[i][0]
                    + "   data[" + i + "][1] = " + data[i][1]);
        }

        double mean1 = sum1 / n, mean2 = sum2 / n;
        System.out.println("Mean1 = " + mean1 + "   Mean2 = " + mean2);

        //
        //                      ２乗和、積和を求めて分散、共分散を計算する
        //
        double sum11 = 0.0, sum12 = 0.0, sum22 = 0.0;

        for (int i = 0; i < n; i++) {
            sum11 += sqr(data[i][0] - mean1);
            sum22 += sqr(data[i][1] - mean2);
            sum12 += (data[i][0] - mean1) * (data[i][1] - mean2);
        }

        double variance1 = sum11 / n, variance2 = sum22 / n;
        double cov = sum12 / n;

        //
        //                              相関係数の計算
        //
        double sd1 = Math.sqrt(variance1), sd2 = Math.sqrt(variance2);
        double r = cov / (sd1 * sd2);

        System.out.println("Variance1 = " + variance1 + "   Variance2 = " + variance2);
        System.out.println("Covariance = " + cov);
        System.out.println("SD1 = " + sd1 + "   SD2 = " + sd2);
        System.out.println("r = " + r);
    }

}
