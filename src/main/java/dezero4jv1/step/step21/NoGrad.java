package dezero4jv1.step.step21;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class NoGrad {
    public static UsingConfig no_grad() {
        return new UsingConfig(() -> Config.enableBackprop, false);
    }
}
