package dezero4j;

import java.io.Serial;

public class AdaDelta extends Optimizer {

    @Serial
    private static final long serialVersionUID = 5050070461901986605L;
    private double rho = 0.95;  // 移動平均の減衰率
    private final double epsilon = 1e-8;  // 数値安定性のための小さな定数
    private final double[] accumGradSquared;  // 勾配の二乗の移動平均
    private final double[] accumUpdateSquared;  // 更新の二乗の移動平均

    public AdaDelta(Model model, double rho) {
        super(model);
        this.rho = rho;
        this.accumGradSquared = new double[model.params.size()];
        this.accumUpdateSquared = new double[model.params.size()];
    }

    @Override
    public void update() {
        int idx = 0;
        for (Parameter param : target.params) {
            //double grad = param.grad.data[0];
            double grad = param.grad.getValues()[0];

            // 勾配の二乗の移動平均
            accumGradSquared[idx] = rho * accumGradSquared[idx] + (1 - rho) * grad * grad;

            // 更新の二乗の移動平均
            double delta = Math.sqrt(accumUpdateSquared[idx] + epsilon) / Math.sqrt(accumGradSquared[idx] + epsilon) * grad;
            accumUpdateSquared[idx] = rho * accumUpdateSquared[idx] + (1 - rho) * delta * delta;

            // パラメータの更新
            //param.subtractAssign(delta);
            idx++;
        }
    }

}
