package dezero4j.step.step37_weakreference;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class NoGrad {
    public static UsingConfig no_grad() {
        return new UsingConfig("enableBackprop", false);
    }
}