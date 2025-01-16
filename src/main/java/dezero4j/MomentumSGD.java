package dezero4j;

import tensor4j.TensorUtils;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MomentumSGD extends Optimizer {

    @Serial
    private static final long serialVersionUID = -6570516936042873958L;

    private double learningRate = 0.01;  // 学習率
    private double momentum = 0.9;      // モーメント係数
    private Map<Parameter, Parameter> velocities; // パラメータごとの速度を保持

    public MomentumSGD(Model model, double learningRate, double momentum) {
        super(model);
        this.learningRate = learningRate;
        this.momentum = momentum;
        this.velocities = new HashMap<>();

        // 各パラメータの速度を初期化（ゼロベクトル）
        for (Parameter param : model.params) {
            velocities.put(param, new Parameter(TensorUtils.create(param.getShape()))); // 同じ形状のゼロを設定
        }
    }

    @Override
    public void update() {
        target.update(learningRate);
        for (Parameter param : target.params) {
            if (param.grad == null) continue; // 勾配がない場合はスキップ

            // 速度を更新: v = momentum * v - learningRate * grad
            Parameter velocity = (Parameter) velocities.get(param).multiply(momentum);
            //velocity.multiplyAssign(momentum);  // v = momentum * v
            velocity.subtractAssign(param.grad.multiply(learningRate)); // v -= learningRate * grad

            // パラメータを更新: param += v
            param.addAssign(velocity);
        }
    }
}
