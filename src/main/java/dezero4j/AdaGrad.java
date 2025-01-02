package dezero4j;

import java.io.Serial;

public class AdaGrad extends Optimizer {

    @Serial
    private static final long serialVersionUID = 5799648050718408485L;
    private final double[] gradSquared;  // 勾配の二乗の累積
    private final double epsilon = 1e-8;  // 数値安定性のための小さな定数
    private double learningRate = 0.01;

    public AdaGrad(Model model, double learningRate) {
        super(model);
        this.learningRate = learningRate;
        this.gradSquared = new double[model.params.size()];
    }

    @Override
    public void update() {
        int idx = 0;
        for (Parameter param : target.params) {
            //double grad = param.grad.data[0];
            double grad = param.grad.getValues()[0];
            // 勾配の二乗の累積
            gradSquared[idx] += grad * grad;
            // 学習率の調整
            double adjustedGrad = learningRate * grad / (Math.sqrt(gradSquared[idx]) + epsilon);
            // パラメータの更新
            //param.subtractAssign(adjustedGrad);
            idx++;
        }
    }

}
