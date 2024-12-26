package dezero4j.step.step47;

import java.util.Arrays;

public class Softmax0 {
    private final int axis;

    // コンストラクタ: デフォルトはaxis=1
    public Softmax0() {
        this.axis = 1;
    }

    public Softmax0(int axis) {
        this.axis = axis;
    }

    // ソフトマックス関数 (forward)
    public double[][] forward(double[][] input) {
        int rows = input.length;
        int cols = input[0].length;
        double[][] output = new double[rows][cols];

        if (axis == 0) { // 列方向
            for (int j = 0; j < cols; j++) {
                // 各列ごとに最大値を計算
                double max = Double.NEGATIVE_INFINITY;
                for (int i = 0; i < rows; i++) {
                    max = Math.max(max, input[i][j]);
                }

                // 最大値を引いてexp計算
                double sumExp = 0.0;
                for (int i = 0; i < rows; i++) {
                    output[i][j] = Math.exp(input[i][j] - max);
                    sumExp += output[i][j];
                }

                // 正規化
                for (int i = 0; i < rows; i++) {
                    output[i][j] /= sumExp;
                }
            }
        } else { // デフォルト（行方向、axis=1）
            for (int i = 0; i < rows; i++) {
                double max = Arrays.stream(input[i]).max().orElse(0.0); // 安定性のために最大値を引く
                for (int j = 0; j < cols; j++) {
                    output[i][j] = Math.exp(input[i][j] - max);
                }
                double sumExp = Arrays.stream(output[i]).sum();
                for (int j = 0; j < cols; j++) {
                    output[i][j] /= sumExp;
                }
            }
        }

        return output;
    }

    // ソフトマックスの勾配計算 (backward)
    public double[][] backward(double[][] dout, double[][] output) {
        int rows = output.length;
        int cols = output[0].length;
        double[][] dx = new double[rows][cols];

        if (axis == 0) { // 列方向
            for (int j = 0; j < cols; j++) {
                for (int i = 0; i < rows; i++) {
                    double sum = 0.0;
                    for (int k = 0; k < rows; k++) {
                        if (i == k) {
                            sum += dout[k][j] * output[k][j] * (1 - output[k][j]);
                        } else {
                            sum += dout[k][j] * (-output[k][j] * output[i][j]);
                        }
                    }
                    dx[i][j] = sum;
                }
            }
        } else { // 行方向（デフォルト）
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    double sum = 0.0;
                    for (int k = 0; k < cols; k++) {
                        if (j == k) {
                            sum += dout[i][k] * output[i][j] * (1 - output[i][j]);
                        } else {
                            sum += dout[i][k] * (-output[i][j] * output[i][k]);
                        }
                    }
                    dx[i][j] = sum;
                }
            }
        }

        return dx;
    }


    public static void main(String[] args) {
        // 入力データの例
        double[][] input = {
                {1.0, 2.0, 3.0},
                {3.0, 1.0, 0.0}
        };

        // Softmaxインスタンスの生成
        Softmax0 softmax = new Softmax0(1); // 列方向

        // Forward計算
        double[][] output = softmax.forward(input);

        // 出力表示
        System.out.println("Softmax Output:");
        for (double[] row : output) {
            System.out.println(Arrays.toString(row));
        }

        // ダミーの勾配 (dout)
        double[][] dout = {
                {0.1, 0.2, 0.3},
                {0.3, 0.2, 0.1}
        };

        // Backward計算
        double[][] dx = softmax.backward(dout, output);

        // 勾配の表示
        System.out.println("Backward Output:");
        for (double[] row : dx) {
            System.out.println(Arrays.toString(row));
        }
    }
}
