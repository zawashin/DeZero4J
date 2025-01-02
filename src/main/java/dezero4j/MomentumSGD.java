package dezero4j;

import java.io.Serial;

public class MomentumSGD extends Optimizer {

    @Serial
    private static final long serialVersionUID = 6378280131961899280L;
    private final double[] velocity;  // 勾配の速度
    private double learningRate = 0.01;
    private double momentum = 0.9;  // モメンタム係数

    public MomentumSGD(Model model, double learningRate, double momentum) {
        super(model);
        this.learningRate = learningRate;
        this.momentum = momentum;
        // 速度の初期化
        velocity = new double[model.params.size()];
    }

    @Override
    public void update() {
        // パラメータごとにモメンタムを適用して更新
        int idx = 0;
        for (Parameter param : target.params) {
            // 勾配の速度を更新
            //velocity[idx] = momentum * velocity[idx] - learningRate * param.grad.data[0];
            velocity[idx] = momentum * velocity[idx] - learningRate * param.grad.getValues()[0];
            // パラメータを更新
            //param.subtractAssign(velocity[idx]);
            idx++;
        }
    }

}
