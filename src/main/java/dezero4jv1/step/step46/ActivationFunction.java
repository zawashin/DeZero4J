package dezero4jv1.step.step46;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public abstract class ActivationFunction extends Function {
    public static ActivationFunction createFunction(ActivateType type) {
        return switch (type) {
            case ActivateType.SIGMOID -> new Sigmoid();
            case ActivateType.RELU -> new ReLU();
            case ActivateType.LEAKY_RELU -> new LeakyReLU();
            case ActivateType.SOFTMAX -> new Softmax();
            case ActivateType.LOG_SOFTMAX -> new LogSoftmax();
        };
    }
}
