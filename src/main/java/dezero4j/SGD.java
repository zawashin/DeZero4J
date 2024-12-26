package dezero4j;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class SGD extends Optimizer {

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
