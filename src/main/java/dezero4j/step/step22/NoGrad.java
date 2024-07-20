package dezero4j.step.step22;

import dlfs3.step.step22.Config;

/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class NoGrad {
    public static UsingConfig no_grad() {
        return new UsingConfig(() -> Config.enableBackprop, false);
    }
}
