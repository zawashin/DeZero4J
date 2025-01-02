package dezero4j;

import java.io.Serial;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class SGD extends Optimizer {

    @Serial
    private static final long serialVersionUID = -6570516936042873958L;
    private double learningRate = 0.01;

    public SGD(Model model, double learningRate) {
        super(model);
        this.learningRate = learningRate;
    }

    @Override
    public void update() {
        target.update(learningRate);
        for (Parameter param : target.params) {
            param.subtractAssign((param.grad).multiply(learningRate));
        }
    }

}
