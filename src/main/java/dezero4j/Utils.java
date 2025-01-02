package dezero4j;

import tensor4j.Tensor;

import java.io.Serial;
import java.io.Serializable;

public class Utils implements Serializable {

    @Serial
    private static final long serialVersionUID = 7786635886227153145L;

    // ソフトマックス関数 (forward)
    public static Tensor[] softmax(Tensor... xs) {
        int shape0, shape1;
        int[][] indices;
        switch (xs[0].getRank()) {
            case 0:
                shape0 = 1;
                shape1 = 1;
                break;
            case 1:
                shape0 = 1;
                shape1 = xs[0].getShape(0);
                break;
            case 2:
                shape0 = xs[0].getShape(0);
                shape1 = xs[0].getShape(1);
                break;
            default:
                throw new IllegalArgumentException("Rank " + xs[0].getRank() + " is not supported.");
        }
        double[] values = new double[shape0 * shape1];
        indices = new int[shape0][shape1];
        for (int i = 0; i < shape0; i++) {
            for (int j = 0; j < shape1; j++) {
                indices[i][j] = i * shape1 + j;
            }
        }

        for (int i = 0; i < shape0; i++) {
            double max = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < shape1; j++) {
                if (xs[0].getValues()[indices[i][j]] > max) {
                    max = xs[0].getValues()[indices[i][j]];
                }
            }
            for (int j = 0; j < shape1; j++) {
                values[indices[i][j]] = Math.exp(xs[0].getValues()[indices[i][j]] - max);
            }
            double sumExp = 0.0;
            for (int j = 0; j < shape1; j++) {
                int index = i * shape1 + j;
                values[index] = Math.exp(xs[0].getValues()[index] - max);
                sumExp += values[index];
            }
            for (int j = 0; j < shape1; j++) {
                values[i * shape1 + j] /= sumExp;
            }
        }

        return new Tensor[]{new Tensor(values, xs[0].getShape())};
    }

    /*
    Softmax Output:
    [0.09003057317038046, 0.24472847105479764, 0.6652409557748218]
    [0.8437947344813395, 0.11419519938459449, 0.04201006613406605]
    Backward Output:
    [-0.014181709360981211, -0.014077035746963, 0.02825874510794424]
    [0.016725305314204297, -0.00915599600654278, -0.00756930930766152]
     */
    public static void main(String[] args) {
        // 入力データの例
        double[][] input = {
                {1.0, 2.0, 3.0},
                {3.0, 1.0, 0.0}
        };

        Tensor x = new Tensor(new Tensor(input));
        // Softmaxインスタンスの生成
        Tensor y = Utils.softmax(x)[0];
        System.out.println("Softmax Output:" + y);
    }
}
